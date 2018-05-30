package com.ebay.dss;

import com.ebay.dss.model.EventItem;
import com.ebay.dss.model.TransFields;
import com.ebay.dss.transformations.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer010;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;

/**
 * @author tianhu
 */
public class readLocalKafka {
    static String consumerNm = "dealstest";
    static String consumerId = "13bf78e6-bc4f-4544-91a0-d68c2f76948d";
    static String consumerTopic = "transaction.sales";

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(2000);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties props = new Properties();

        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerNm);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerId);

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        // Rheos event deserializer that decodes messages stored in Kafka to RheosEvent
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.ebay.rheos.schema.avro.RheosEventDeserializer");


        DataStream<String> stream = env.addSource(new FlinkKafkaConsumer010<String>(consumerTopic, new SimpleStringSchema(), props));
        //decode message from rheos
        DataStream<EventItem> eventItemDataStream = stream.map(new parser());

        eventItemDataStream.print();


        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

