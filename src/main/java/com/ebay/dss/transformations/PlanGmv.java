package com.ebay.dss.transformations;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.ebay.dss.model.EventItem;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;

import java.io.Serializable;
import java.util.Map;

/**
 * @author tianhu
 */
public class PlanGmv extends RichMapFunction<EventItem, EventItem> {
    private String documentId;
    private Bucket bucket;
    private CouchbaseCluster cluster;
    Map<String, Object> config;
    public PlanGmv(Map<String, Object> config) {
        this.config = config;

    }

    @Override
    public EventItem map(EventItem eventItem) throws Exception {
        eventItem.setPlan_gmv(
                Double.valueOf(bucket.mapGet(documentId,eventItem.getCurrencyID(),String.class))
                        *
                        Double.valueOf(eventItem.getGmvLstgAmt()));
        return eventItem;
    }
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        this.documentId=(String)config.get("plan_rate");
        this.cluster = CouchbaseCluster.create((String)config.get("clusterName"));
        cluster.authenticate((String)config.get("userName"), (String)config.get("password"));
        this.bucket = cluster.openBucket((String)config.get("bucketName"));

    }

    @Override
    public void close() throws Exception {
        super.close();
//        this.cluster.disconnect();
    }
}
