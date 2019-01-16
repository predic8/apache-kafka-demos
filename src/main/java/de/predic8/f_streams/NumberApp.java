package de.predic8.f_streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Printed;

import java.util.Properties;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;

public class NumberApp {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put( APPLICATION_ID_CONFIG, "numbers");
        props.put( BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        StreamsBuilder builder = new StreamsBuilder();

        builder.stream("numbers",
                Consumed.with(
                        Serdes.Integer(),
                        Serdes.Double()))
                .print(Printed.toSysOut());

        new KafkaStreams( builder.build(), props).start();

    }
}
