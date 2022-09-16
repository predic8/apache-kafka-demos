package de.predic8.s_security;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import java.util.*;

import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SCRAMConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        props.put("sasl.mechanism","SCRAM-SHA-256");
        props.put("security.protocol","SASL_SSL");

        props.put(GROUP_ID_CONFIG, "arbeiter");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props, new StringDeserializer(), new StringDeserializer()) ) {
            consumer.subscribe( singletonList("produktion"));

            while (true){
                for (ConsumerRecord<String, String> rec : consumer.poll(ofSeconds(1)))
                    System.out.printf("key= %s, value= %s\n", rec.key(), rec.value());
            }
        }

    }
}
