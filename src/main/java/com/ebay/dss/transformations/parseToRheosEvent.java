package com.ebay.dss.transformations;

import com.ebay.dss.model.EventItem;
import com.ebay.dss.model.SalesOutItem;
import io.ebay.rheos.kafka.client.StreamConnectorConfig;
import io.ebay.rheos.schema.avro.GenericRecordDomainDataDecoder;
import io.ebay.rheos.schema.avro.SchemaRegistryAwareAvroSerializerHelper;
import io.ebay.rheos.schema.event.RheosEvent;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichMapFunction;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tianhu
 */
public class parseToRheosEvent extends RichMapFunction<EventItem, RheosEvent> {
    @Override
    public RheosEvent map(EventItem eventItem) throws Exception {
        SalesOutItem salesOutItem= new SalesOutItem(eventItem);
        salesOutItem.setVertical("GC");
        salesOutItem.setSlr_rollup("DE");
        salesOutItem.setSlr_type("C2C");
//        System.out.println(salesOutItem.toString());
        Map<String, Object> config = new HashMap<>();
        config.put(StreamConnectorConfig.RHEOS_SERVICES_URLS, "https://rheos-services.qa.ebay.com");
        String schemaName = "sales.test";
        SchemaRegistryAwareAvroSerializerHelper<GenericRecord> serializerHelper = new SchemaRegistryAwareAvroSerializerHelper<>(config, GenericRecord.class);
        Schema schema = serializerHelper.getSchema(schemaName);

        // Rheos event extends GenericRecord so that it can be used as GenericRecord
        RheosEvent rheosEvent = new RheosEvent(schema);

        // Populate Rheos header fields. These fields are used by Rheos
//        rheosEvent.setEventId("index-key for future indexing on Rheos. This is optional");
        rheosEvent.setEventCreateTimestamp(System.currentTimeMillis());
        rheosEvent.setEventSentTimestamp(System.currentTimeMillis());
        rheosEvent.setProducerId("transaction.sales.test");
        rheosEvent.setEventId("eventId");
        rheosEvent.setSchemaId(serializerHelper.getSchemaId(schemaName));

        // Populate user's fields. The way to populate is schema dependent.

        Class<?> itemclass=salesOutItem.getClass();
        Field[] fields=itemclass.getDeclaredFields();
        for (Field field:fields){
            try {
                field.setAccessible(true);
                if(!field.getName().equals("serialVersionUID")) {
                    rheosEvent.put(field.getName(), field.get(salesOutItem));
//                    System.out.println(rheosEvent.get(field.getName()));
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

/*      GenericRecordDomainDataDecoder decoder = new GenericRecordDomainDataDecoder(config);
        Field rec = rheosEvent.getClass().getDeclaredField("record");
        rec.setAccessible(true);
        GenericRecord payload = (GenericRecord) rec.get(rheosEvent);
        System.err.println(payload.toString());*/


        return rheosEvent;
    }
}
