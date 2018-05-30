package com.ebay.dss.transformations;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.ebay.dss.model.EventItem;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author tianhu
 */
public class CoreVertical  extends RichMapFunction<EventItem, EventItem> {
    private String documentId;
    private CouchbaseCluster cluster;
    private String na_documentId;
    private Bucket bucket;
    private Set<String> na;
    private final Map<String, Object> config;

    public CoreVertical(Map<String, Object> config, String... cntrys) {
        this.config = config;
        if (cntrys.length == 0) {
        } else {
            this.na = new HashSet(cntrys.length);
            for (String cntry : cntrys)
                this.na.add(cntry);
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.documentId = (String) config.get("standard_vertical");
        this.na_documentId = ((String) config.get("standard_vertical"));
        this.cluster = CouchbaseCluster.create((String) config.get("clusterName"));
        cluster.authenticate((String) config.get("userName"), (String) config.get("password"));
        this.bucket = cluster.openBucket((String) config.get("bucketName"));

    }

    @Override
    public void close() throws Exception {
        super.close();
//        this.cluster.disconnect();
    }

    @Override
    public EventItem map(EventItem eventItem) throws Exception {
        if (this.na != null && this.na.contains(eventItem.getSlr_rollup()))
            eventItem.setVertical(
                    bucket.mapGet(
                            na_documentId,
                            makeKey(eventItem.getListingSiteID(), eventItem.getCategoryOne()),
                            String.class));
        else {
            eventItem.setVertical(
                    bucket.mapGet(
                            documentId,
                            makeKey(eventItem.getListingSiteID(), eventItem.getCategoryOne()),
                            String.class));
        }
        return eventItem;
    }

    private static String makeKey(String site, String categoryId) {
        StringBuilder sb = new StringBuilder(site);
        return sb.append("_").append(categoryId).toString();
    }
}
