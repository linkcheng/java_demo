package cn.xyf.flowcount.kafka.streams;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class ConfluentStreams extends AbstractStreams {
    private static final String schemaRegistryUrl = "http://192.168.0.44:8081";
    private static final String inputTopic = "common.common.user_menu";
    private static final String outputTopic = "streams.count";
    private static final String APPLICATION_ID_CONFIG = "streams-count-avro1111";

    public static void main(String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        countMenuStream(builder);

        final Topology topology = builder.build();
        final Properties props = getProps();
        final KafkaStreams streams = new KafkaStreams(topology, props);

        startStreamWithClose(streams);
    }

    public static Serde<GenericRecord> makeSerde() {
        Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        Serde<GenericRecord> genericAvroSerde = new GenericAvroSerde();
        genericAvroSerde.configure(serdeConfig, false);

        return genericAvroSerde;
    }

    public static void countMenuStream(final StreamsBuilder builder) {
        Serde<GenericRecord> genericAvroSerde = makeSerde();

        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);

        stream.filter((key, value) -> "c".equals(value.get("op").toString()))
                .mapValues(value -> (GenericRecord)value.get("after"))
                .selectKey((key, value) -> value.get("name").toString())
                .groupByKey(Grouped.with(Serdes.String(), genericAvroSerde))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(3L)))
                .count()
                .toStream()
                .to(outputTopic, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.Long()))
        ;
    }

    public static void peekMenuStream(final StreamsBuilder builder) {
        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);
        stream.filter((key, value) -> "c".equals(value.get("op").toString()))
                .mapValues(value -> (GenericRecord)value.get("after"))
                .peek((key, value) -> log.info("key=" + key +
                        ", value=" + value.toString() +
                        ", parent_id=" + value.get("parent_id") +
                        ", parent_id class=" + value.get("parent_id").getClass().getName()
                ))
        ;

    }

    public static void mapMenuStream(final StreamsBuilder builder) {
        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);

        stream.filter((key, value) -> "c".equals(value.get("op").toString()))
                .mapValues(value -> (GenericRecord)value.get("after"))
                .map((key, value) -> new KeyValue<>(key.get("id").toString(), value.get("name").toString()))
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.String()))
        ;
    }

    public static Properties getProps() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID_CONFIG);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBOOTSTRAP_SERVERS_CONFIG());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        return props;
    }

}
