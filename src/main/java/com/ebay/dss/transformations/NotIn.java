package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import org.apache.flink.api.common.functions.FilterFunction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author tianhu
 */
public class NotIn implements FilterFunction<EventItem>{
    private Set<Object> fiters;
    private String fieldName;
    private Object recordField;

    public NotIn(String field,Object ... filters) {
        this.fieldName =field;
        this.fiters = new HashSet(filters.length);
        for (Object filter : filters)
            this.fiters.add(filter);
    }
    public boolean filter(EventItem eventItem) throws Exception {

        try {
            Class<?> eventclass = eventItem.getClass();
            Field field = eventclass.getDeclaredField(this.fieldName);
            field.setAccessible(true);
            this.recordField = field.get(eventItem);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (this.fiters.contains(recordField)) {
            return false;
        } else {
            return true;
        }
    }

}
