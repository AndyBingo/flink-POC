package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import com.ebay.dss.model.SalesOutItem;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author tianhu
 */
public class paseToSalesItem implements MapFunction<EventItem, SalesOutItem> {
    @Override
    public SalesOutItem map(EventItem eventItem) throws Exception {
        return new SalesOutItem(eventItem);
    }
}
