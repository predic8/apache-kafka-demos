package de.predic8.f_kstreams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.WindowedDeserializer;
import org.apache.kafka.streams.kstream.internals.WindowedSerializer;
import org.apache.kafka.streams.processor.StreamPartitioner;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class GroupByKeyStream {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.serializer", JsonPOJOSerializer.class.getName());
        props.put("value.deserializer", JsonPOJODeserializer.class.getName());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Messung.class);

        final Serializer<Messung> serializer = new JsonPOJOSerializer<>();
        serializer.configure(serdeProps, false);

        final Deserializer<Messung> deserializer = new JsonPOJODeserializer<>();
        deserializer.configure(serdeProps, false);

        final Serde<Messung> pojoSerde = Serdes.serdeFrom(serializer, deserializer);

        Serde<Windowed<String>> windowedSerde = Serdes.serdeFrom(new WindowedSerializer<>(new StringSerializer()), new WindowedDeserializer<>(new StringDeserializer()));

        KStreamBuilder builder = new KStreamBuilder();
        KStream<String, Messung> stream = builder.stream(Serdes.String(), pojoSerde, "produktion");

        KTable<Windowed<String>, Double> table = stream
                .groupByKey(Serdes.String(), pojoSerde)
                .aggregate( () -> new Double(0),
                            (k,v,agg) -> agg + v.kw
                            ,TimeWindows.of(10000),
                        Serdes.Double(), "store");

        table.toStream().to(windowedSerde, Serdes.Double(), "produktion3");


        KStream<Windowed<String>, Double> aggregiert = builder.stream(windowedSerde, Serdes.Double(), "produktion3");
        aggregiert.map((k, v) -> {
            String time = new SimpleDateFormat("HH:mm:ss").format(k.window().start());
            System.out.printf("%s key: %s value: %s\n", time, k.key(), v);

            return new KeyValue(k, v);
        });

        KafkaStreams streams = new KafkaStreams(builder, new StreamsConfig(props));
        streams.start();
    }
}