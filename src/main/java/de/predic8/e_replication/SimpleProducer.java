package de.predic8.e_replication;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Properties;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class SimpleProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 32000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, String> producer = new KafkaProducer<>(props);

        JsonObject json = Json.createObjectBuilder()
                .add("windrad", 6)
                .add("kw/h",33)
                .build();

        String msg= json.toString();

        for(int i = 1; i <= 10; i++) {

            String key = String.valueOf(round(random() * 1000));
            double value = new Double(round(random()*10000000L)).intValue()/1000.0;

            producer.send(new ProducerRecord<>("produktion", key,msg ));
        }
        System.out.println("fertig!");

        producer.close();
    }
}
