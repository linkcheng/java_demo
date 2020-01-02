package cn.xyf.flowcount.kafka.consumer;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class AbstractConsumer {

    private static String BOOTSTRAP_SERVERS = "192.168.0.44:9092";
    public static String SCHEMA_REGISTRY_URL = "http://192.168.0.44:8081";

    public Properties getProps(String group_id) {
        Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group_id);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // earliest, none
        return props;
    }

    public Properties getAvroProps(String group_id) {
        Properties props = getProps(group_id);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_REGISTRY_URL);
        return props;
    }
}
