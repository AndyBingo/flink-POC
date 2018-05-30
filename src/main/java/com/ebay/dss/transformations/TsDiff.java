package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import org.apache.flink.api.common.functions.FilterFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tianhu
 */
public class TsDiff implements FilterFunction<EventItem> {
    private long diff;
    public TsDiff(long diff) {
        this.diff = diff;
    }

    @Override
    public boolean filter(EventItem eventItem) throws Exception {
        Long transDt = eventItem.getCreatedDT();
        Long now = System.currentTimeMillis();
        if (now-transDt > this.diff)
            return false;
        else {
            return true;
        }
    }
}
