package com.ebay.dss.transformations;

import com.datastax.driver.core.*;
import com.ebay.dss.model.EventItem;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tianhu
 */
public class SellerType extends RichMapFunction<EventItem, EventItem> {


    private static final Logger log = LoggerFactory.getLogger(SellerType.class);

    private Map<String, Object> config;
    private transient PreparedStatement selectSeller;
    private static Session session;
    private static Object sessionLock = new Object();
    private final static String FIND_SELLER = "select seller_type_cd, user_dsgntn_id from nrt_slr_type where user_id = ?";
    private LoadingCache<Long, Integer[]> sellers;

    public SellerType(Map<String, Object> config) {
        this.config = config;
    }

    @Override
    public EventItem map(EventItem eventItem) throws Exception {
        String sellerCountry = eventItem.getSlr_rollup();
        Long sellerId = Long.valueOf(eventItem.getSellerId());

        String type = "c";//c2c

        try {
            Integer[] result = sellers.get(sellerId);
            if (isEU(sellerCountry)) {
                Integer userDsgnId = result[1];
                switch (userDsgnId) {
                    case 2:
                        type = "b";
                        break;
                    default:
                        type = "c";
                        break;
                }
            } else {
                Integer slrTypeId = result[0];
                switch (slrTypeId) {
                    case 11:
                    case 12:
                    case 13:
                    case 15:
                        type = "b";
                        break;
                    default:
                        type = "c";
                        break;
                }
            }
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
        }
        eventItem.setSlr_type(type);
        return eventItem;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        synchronized (sessionLock) {
            if (session == null) {
                Cluster.Builder builder = Cluster.builder().addContactPoints(
                        ((String) config.get("cassandra.hosts")).split(","));

                if (config.get("cassandra.username") != null && !"".equals(config.get("cassandra.username"))
                        && config.get("cassandra.password") != null && !"".equals(config.get("cassandra.password"))) {
                    builder = builder.withCredentials((String) config.get("cassandra.username"),
                            (String) config.get("cassandra.password"));
                }
                Cluster cluster = builder.build();
                session = cluster.connect((String) config.get("cassandra.keyspace"));
            }
        }

        this.selectSeller = session.prepare(FIND_SELLER);


        sellers = CacheBuilder.newBuilder()
                .maximumSize((Integer) config.get("cache.seller.size"))
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build(
                        new CacheLoader<Long, Integer[]>() {
                            public Integer[] load(Long id) {
                                Row row = null;
                                try {
                                    BoundStatement statement = selectSeller.bind(id);
                                    ResultSet resultSet = session.execute(statement);
                                    row = resultSet.one();
                                } catch (Exception e) {
                                    log.error("Unable to get seller for {}", id);
                                }
                                Integer[] result = {-99, 0};
                                if (row != null) {
                                    result[0] = new Long(row.getLong(0)).intValue();
                                    result[1] = new Long(row.getLong(1)).intValue();
                                } else {
                                    log.error("Seller {} not found.", id);
                                }
                                return result;
                            }
                        });
    }


    boolean isEU(String country) {
        return "UK".equals(country) || "DE".equals(country) || "FR".equals(country) || "IT".equals(country) || "ES".equals(country);
    }
}
