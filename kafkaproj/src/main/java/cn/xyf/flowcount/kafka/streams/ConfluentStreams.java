package cn.xyf.flowcount.kafka.streams;

import cn.xinyongfei.bi.streams.CountRecord;
import cn.xyf.flowcount.kafka.CountTimestampExtractor;
import cn.xyf.flowcount.kafka.MenuCount;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
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
    private static final String outputTopic = "streams.count2222";
    private static final String APPLICATION_ID_CONFIG = "streams-count-avro2222";

    public static void main(String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        countMenuStream(builder);

        final Topology topology = builder.build();
        final Properties props = getProps();
        final KafkaStreams streams = new KafkaStreams(topology, props);

        startStreamWithClose(streams);
    }

    public static Schema makeSchema() {
        return MenuCount.getClassSchema();
    }

    public static Serde<GenericRecord> makeSerde(boolean isKey) {
        Map<String, String> serdeConfig = Collections.singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        Serde<GenericRecord> genericAvroSerde = new GenericAvroSerde();
        genericAvroSerde.configure(serdeConfig, isKey);

        return genericAvroSerde;
    }

    public static KStream<Windowed<String>, Long> startStream(KStream<GenericRecord, GenericRecord> stream) {
        Serde<GenericRecord> genericAvroSerde = makeSerde(false);

        return stream.filter((key, value) -> "c".equals(value.get("op").toString()))
                .mapValues(value -> (GenericRecord)value.get("after"))
                .selectKey((key, value) -> value.get("name").toString())
                .groupByKey(Grouped.with(Serdes.String(), genericAvroSerde))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(3L)))
                .count()
                .toStream()
                ;
    }

    /**
     * 构建 DAG 逻辑过程
     * kafka.version=2.3
     * @param builder
     */
    public void process(final StreamsBuilder builder) {
        Serde<GenericRecord> keyGenericAvroSerde = makeSerde(true);
        Serde<GenericRecord> valueGenericAvroSerde = makeSerde(false);
        Duration window = Duration.ofSeconds(30);

        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);

        KStream<Windowed<String>, Long> newStream = stream
                .map((k, v) -> new KeyValue<>(v.get("op").toString(), (GenericRecord)v.get("after")))
                .groupByKey(Grouped.with(Serdes.String(), valueGenericAvroSerde))
                .windowedBy(TimeWindows.of(window).advanceBy(window).grace(Duration.ofSeconds(10L)))
                .count(Materialized.with(Serdes.String(), Serdes.Long()))
                .suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream()
        ;

        newStream.mapValues(
                (k ,v) -> {
                    GenericRecord record = new GenericData.Record(CountRecord.getClassSchema());
                    record.put("op",  k.key());
                    record.put("start", k.window().start());
                    record.put("count",  v);
                    return record;
                })
                .to(outputTopic, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), keyGenericAvroSerde));
    }

    /**
     *
     /opt/confluent/bin/kafka-console-consumer \
     --bootstrap-server 192.168.0.044:9092 \
     --topic streams.count \
     --from-beginning \
     --property print.key=true \
     --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer \
     --property key.deserializer=org.apache.kafka.streams.kstream.TimeWindowedDeserializer \
     --property key.deserializer.default.windowed.key.serde.inner=org.apache.kafka.common.serialization.Serdes\$StringSerde

     ……
     */
    public static void countMenuStream0(final StreamsBuilder builder) {
        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);
        KStream<Windowed<String>, Long> newStream = startStream(stream);

        newStream.to(outputTopic, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), Serdes.Long()));
    }

    /**
     *
     /opt/confluent/bin/kafka-avro-console-consumer \
     --bootstrap-server 192.168.0.044:9092 \
     --topic streams.count2222 \
     --from-beginning \
     --property print.key=true \
     --property key.deserializer=org.apache.kafka.streams.kstream.TimeWindowedDeserializer \
     --property key.deserializer.default.windowed.key.serde.inner=org.apache.kafka.common.serialization.Serdes\$StringSerde

     ……
     */
    public static void countMenuStream(final StreamsBuilder builder) {
        Serde<GenericRecord> genericAvroSerde = makeSerde(false);

        KStream<GenericRecord, GenericRecord> stream = builder.stream(inputTopic);

        KStream<Windowed<String>, Long> newStream = startStream(stream);

        newStream.mapValues((k ,v) -> {
                    GenericRecord record = new GenericData.Record(makeSchema());
                    record.put("start", k.window().start());
                    record.put("name",  k.key());
                    record.put("count",  v);
                    return record;
                })
                .to(outputTopic, Produced.with(WindowedSerdes.timeWindowedSerdeFrom(String.class), genericAvroSerde));
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
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, GenericAvroSerde.class.getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class.getName());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        // 时间抽取
//        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, CountTimestampExtractor.class);
        return props;
    }

}
