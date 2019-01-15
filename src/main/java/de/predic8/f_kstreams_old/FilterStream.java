package de.predic8.f_kstreams_old;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.*;

public class FilterStream {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.serializer", JsonPOJOSerializer.class.getName());
        props.put("value.deserializer", JsonPOJODeserializer.class.getName());

        Map<String, Object> serdeProps = new HashMap<>();
        serdeProps.put("JsonPOJOClass", Messung.class);

        final Serializer<Messung> serializer = new JsonPOJOSerializer<>();
        serializer.configure(serdeProps, false);

        final Deserializer<Messung> deserializer = new JsonPOJODeserializer<>();
        deserializer.configure(serdeProps, false);

        final Serde<Messung> serde = Serdes.serdeFrom(serializer, deserializer);

        StreamsConfig config = new StreamsConfig(props);

        StreamsBuilder builder = new StreamsBuilder();

        //  Before 2.0.0: builder.stream(Serdes.String(), serde, "produktion")

//        builder.stream("produktion")
//                .filter( (k,v) -> v.type.equals("Biogas"))
//                .to("produktion2");
//
//        KafkaStreams streams = new KafkaStreams( builder, config);
//        streams.start();
    }
}
