package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tianhu
 */
public class RoundTimestamp implements MapFunction<EventItem, EventItem>
{

    private static final Logger log = LoggerFactory.getLogger(RoundTimestamp.class);
    private int divisor;
    private int diff;
    private int granularity;

    public RoundTimestamp(int granularity) {
        this.granularity = granularity;
        switch (granularity) {
            case Calendar.SECOND:
                log.info("round to seconds");
                divisor = 1000;
                diff = 0;
                break;
            case Calendar.MINUTE:
                log.info("round to minutes");
                divisor = 60000;
                diff = 0;
                break;
            case Calendar.HOUR:
                log.info("round to hours");
                divisor = 3600000;
                diff = 0;
                break;
            case Calendar.DATE:
                log.info("round to days");
                divisor = 86400000;
                diff = 7 * 3600000; //eBay default timezone MST -07:00
                break;
            default:
                throw new IllegalArgumentException("Not accepted granularity.");
        }
    }

    @Override
    public EventItem map(EventItem eventItem) throws Exception {
        long timestamp = eventItem.getCreatedDT();
        long rounded = round(timestamp);
        if (granularity == Calendar.DATE) {
            //   it's not working for same cases:
            rounded = DateUtils.truncate(new Date(timestamp), Calendar.DATE).getTime();
        }
        eventItem.setTs(rounded);
        return eventItem;
    }

    long round(long timestamp) {
        return  ((timestamp/divisor) * divisor + diff);
    }

}

