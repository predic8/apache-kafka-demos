package de.predic8.i_headers;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Properties;

import static java.lang.Long.valueOf;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class HeaderProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        try(Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer())) {

            long t1 = System.currentTimeMillis();

            int i = 0;
            for (; i < 10; i++) {

                String key = String.valueOf(round(random() * 1000));
                double value = valueOf(round(random() * 10000000L)).intValue() / 1000.0;

                JsonObject json = Json.createObjectBuilder()
                        .add("windrad", key)
                        .add("kw", value)
                        .build();

                ProducerRecord<String, String> record = new ProducerRecord<>("headers", key, json.toString());

                producer.send(record);
            }
            System.out.println("fertig " + i + " Nachrichten in " + (System.currentTimeMillis() - t1 + " ms"));

        }
    }
}
