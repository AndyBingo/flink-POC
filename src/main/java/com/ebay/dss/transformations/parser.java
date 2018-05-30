package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import com.ebay.dss.model.TransactionFields;
import org.apache.flink.api.common.functions.MapFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author tianhu
 */
public class parser implements MapFunction<String, EventItem> {
    private static final Logger log = LoggerFactory.getLogger(parser.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//use HH not hh!!!!
    private final DecimalFormat df = new DecimalFormat("##.00");


    public EventItem map(String event) throws Exception {
        Map<String, Object> pairs = new HashMap<String, Object>();
        if (event != null && !"".equals(event.trim())) {
            int index = event.indexOf("totalAmount");
            event = event.substring(index);
            StringTokenizer st = new StringTokenizer(event, "|");
            while (st.hasMoreElements()) {
                String token = st.nextToken();
                int i = token.indexOf("=");
                if (i < 1) {
                    continue;
                }
                String k = token.substring(0, i);
                String v = token.substring(i + 1);
                //addition parsing
                if (TransactionFields.TIMESTAMP.equals(k)) {
                    try {
                        pairs.put(k, sdf.parse(v).getTime());
                    } catch (ParseException e) {
                        log.error("Unable to parse: createDT=" + v);
                    }
                } else if (TransactionFields.GMV.equals(k)) {
                    try {
                        pairs.put(k, df.parse(v).doubleValue());//parse may return a long if no decimal
                    } catch (ParseException e) {
                        log.error("Unable to gmv: gmv=" + v);
                    }
                } else if (TransactionFields.QUANTITY.equals(k)) {
                    try {
                        pairs.put(k, Integer.valueOf(v));
                    } catch (NumberFormatException e) {
                        log.error("Unable to quantity: quantity=" + v);
                    }
                }//take it as it is...
                else {
                    pairs.put(k, v);
                }

            }
        }
        EventItem eventItem = new EventItem(pairs);
//        System.out.println("parse: "+eventItem.toString());
        return eventItem;
    }
}
