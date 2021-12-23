package de.predic8.e_replication;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Properties;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class WindenergieProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        try(Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer())) {

            for (int i = 1; i <= 10; i++) {

                String key = String.valueOf(round(random() * 1000));

                JsonObject json = Json.createObjectBuilder()
                        .add("windrad", 6)
                        .add("kw/h", Double.valueOf(round(random() * 10000000L)).intValue() / 1000.0)
                        .build();

                producer.send(new ProducerRecord<>("produktion", key, json.toString()));
            }
        }
        System.out.println("fertig!");
    }
}
