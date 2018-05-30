package com.ebay.dss;

import com.ebay.dss.model.EventItem;
import com.ebay.dss.model.TransFields;

import com.ebay.dss.transformations.*;
import io.ebay.rheos.schema.avro.RheosEventDeserializer;
import io.ebay.rheos.schema.event.RheosEvent;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.commons.collections.map.HashedMap;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer010;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer08;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer09;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * @author tianhu
 */
public class BizPerf {
    static final String SITE_FILTER = "app.filter.site";
    static final String CB_ROLLUP = "couchbase.item";
        static String consumerNm =      "transactiontest";
    static String consumerId = "dafe5c18-96a7-4f2e-b500-75fea33084f0";
    static String consumerTopic = "tora.in.rtdf.transaction.new";
//    static String consumerNm = "dealstest";
//    static String consumerId = "13bf78e6-bc4f-4544-91a0-d68c2f76948d";
//    static String consumerTopic = "tora.out.transactiontest.fakedata";
    static String producerNm = "transaction.sales.test";
    static String producerId = "1556da2f-8ad4-4413-b655-1d753b26164e";
    static String producerTopic = "tora.out.transactiontest.sales.test";


    static final String[] NA = {"US", "CA"};

    public static void main(String[] args) {
        Map config = new HashedMap();
        try {
            Yaml yaml = new Yaml();
            URL url = BizPerf.class.getClassLoader().getResource("BizPerf.local.yaml");
            if (url != null) {
                //也可以将值转换为Map

                Map map = (Map) yaml.load(new FileInputStream(url.getFile()));
                //通过map我们取值就可以了.
                config.putAll(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(10000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerNm);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerId);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "rheos-kafka-proxy-1.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-2.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-3.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-1.phx02.dev.ebayc3.com:9092,rheos-kafka-proxy-2.phx02.dev.ebayc3.com:9092,rheos-kafka-proxy-3.phx02.dev.ebayc3.com:9092");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        // Rheos event deserializer that decodes messages stored in Kafka to RheosEvent
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.ebay.rheos.schema.avro.RheosEventDeserializer");
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "rheos-kafka-proxy-1.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-2.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-3.lvs02.dev.ebayc3.com:9092,rheos-kafka-proxy-1.phx02.dev.ebayc3.com:9092,rheos-kafka-proxy-2.phx02.dev.ebayc3.com:9092,rheos-kafka-proxy-3.phx02.dev.ebayc3.com:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, producerId);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.ebay.rheos.schema.avro.RheosEventSerializer");

        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer010<String>(consumerTopic, new SimpleStringSchema(), props));
        //decode message from rheos
        DataStream<EventItem> eventItemDataStream = stream.map(new parser());
        //
        DataStream<EventItem> siteFilterDataStream = eventItemDataStream.filter(new NotIn(TransFields.TRANS_SITE, config.getOrDefault(SITE_FILTER, "0,2,100")));

        DataStream<EventItem> caty1FilterDataStream = siteFilterDataStream.filter(new NotIn(TransFields.CATY_1, "172036"));

        DataStream<EventItem> tsDiffDataStream = caty1FilterDataStream.filter(new TsDiff(172800000));

        DataStream<EventItem> roundTimestampDataStream = tsDiffDataStream.map(new RoundTimestamp(Calendar.HOUR));

        DataStream<EventItem> countryRollupDataStream = roundTimestampDataStream.map(new CountryRollup((Map<String, Object>) config.get(CB_ROLLUP)));
        DataStream<EventItem> planGmvDataStream = countryRollupDataStream.map(new PlanGmv((Map<String, Object>) config.get(CB_ROLLUP)));
        DataStream<EventItem> coreVerticalDataStream = planGmvDataStream.map(new CoreVertical((Map<String, Object>) config.get(CB_ROLLUP), NA));

//        DataStream<EventItem> sellerTypeDataStream = coreVerticalDataStream.map(new SellerType(config));

        DataStream<RheosEvent> salesOutItemDataStream = coreVerticalDataStream.map(new parseToRheosEvent());

        FlinkKafkaProducer08<RheosEvent> myProducer = new FlinkKafkaProducer08<RheosEvent>(
                producerTopic,                  // target topic
                new KeyedSerializationSchema<RheosEvent>() {
                    @Override
                    public byte[] serializeKey(RheosEvent rheosEvent) {
//                        return rheosEvent.toBytes();
                        return ByteBuffer.allocate(4).putInt(new Random().nextInt(Integer.MAX_VALUE)).array();
                    }

                    @Override
                    public byte[] serializeValue(RheosEvent rheosEvent) {
                        EncoderFactory encoderFactory = EncoderFactory.get();
                        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                            BinaryEncoder encoder = encoderFactory.directBinaryEncoder(out, null);
                            DatumWriter<GenericRecord> writer = getWriter(rheosEvent);
                            writer.write(rheosEvent, encoder);
                            encoder.flush();
                            System.err.println("one to Rheos: " + rheosEvent.toString());
                            return out.toByteArray();
                        } catch (Exception e) {
                            throw new SerializationException("Unable to serialize common message", e);
                        }
                    }

                    @Override
                    public String getTargetTopic(RheosEvent rheosEvent) {
                        return producerTopic;
                    }

                    private DatumWriter<GenericRecord> getWriter(RheosEvent rheosEvent) {
                        return new GenericDatumWriter<>(rheosEvent.getSchema());
                    }
                }, properties);   // serialization schema

// the following is necessary for at-least-once delivery guarantee
        myProducer.setLogFailuresOnly(true);   // "false" by default
        myProducer.setFlushOnCheckpoint(false);  // "false" by default

        salesOutItemDataStream.addSink(myProducer);



        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
