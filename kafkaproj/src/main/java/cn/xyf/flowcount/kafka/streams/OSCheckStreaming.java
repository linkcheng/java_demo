package cn.xyf.flowcount.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Pattern;


public class OSCheckStreaming extends AbstractStreams{
    private static final String inputTopic = "streams-plaintext-input";
    private static final String outputTopic = "streams-wordcount-output";
    private static final String APPLICATION_ID_CONFIG = "streams-wordcount";

    /**
     *
     bin/kafka-console-consumer.sh --bootstrap-server 192.168.0.44:9092 \
         --topic streams-wordcount-output \
         --from-beginning \
         --formatter kafka.tools.DefaultMessageFormatter \
         --property print.key=true \
         --property print.value=true \
         --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \
         --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer
     * @param args
     */

    public static void main(String[] args) {
        final StreamsBuilder builder = new StreamsBuilder();
        createWordCountStream(builder);

        final Properties props = getProps();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATION_ID_CONFIG);

        final Topology topology = builder.build();
        final KafkaStreams streams = new KafkaStreams(topology, props);

        startStreamWithClose(streams);

    }

    /**
     * Define the processing topology for Word Count.
     *
     * @param builder StreamsBuilder to use
     */
    public static void createWordCountStream(final StreamsBuilder builder) {
        // Construct a `KStream` from the input topic "streams-plaintext-input", where message values
        // represent lines of text (for the sake of this example, we ignore whatever may be stored
        // in the message keys).  The default key and value serdes will be used.
        final KStream<String, String> textLines = builder.stream(inputTopic);

        final Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

        final KTable<String, Long> wordCounts = textLines
                .flatMapValues(value -> Arrays.asList(pattern.split(value)))
                .groupBy((keyIgnored, word) -> word)
                .count()
                ;

        // Write the `KTable<String, Long>` to the output topic.
        wordCounts.toStream().to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));
    }
}
