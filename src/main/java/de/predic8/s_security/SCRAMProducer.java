package de.predic8.s_security;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import javax.json.*;
import java.util.*;
import java.util.concurrent.*;

import static java.lang.Math.*;
import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class SCRAMProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        props.put("sasl.mechanism","SCRAM-SHA-256");
        props.put("security.protocol","SASL_PLAINTEXT");

        try(Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer())) {
            producer.send(new ProducerRecord<>("produktion", "1", "And the message is...")).get();
            System.out.println("fertig ");
        }



    }
}
