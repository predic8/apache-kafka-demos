package de.predic8.s_security;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import javax.json.*;
import java.util.*;

import static java.lang.Math.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SCRAMClientWithoutTLS {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        try(Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer())) {

            int i = 0;
            long t1 = System.currentTimeMillis();

            for (; i < 10; i++) {

                String key = String.valueOf(round(random() * 1000));

                JsonObject json = Json.createObjectBuilder()
                        .add("windrad", key)
                        .add("kw", Double.valueOf(round(random() * 10000000L)).intValue() / 1000.0)
                        .build();

                producer.send(new ProducerRecord<>("produktion", key, json.toString()));
            }
            System.out.println("fertig " + i + " Nachrichten in " + (System.currentTimeMillis() - t1 + " ms"));
        }

    }
}
