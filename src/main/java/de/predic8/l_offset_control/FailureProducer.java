package de.predic8.l_offset_control;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class FailureProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties props = new Properties();

        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16000);
        props.put(LINGER_MS_CONFIG, 100);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<Integer, String> producer1 = new KafkaProducer<>(props);

        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer2 = new KafkaProducer<>(props);

        long t1 = System.currentTimeMillis();

        for(int i = 0; i < 7; i++) {
            producer1.send(new ProducerRecord<>("produktion",i, "Foo")).get();
        }

        producer2.send(new ProducerRecord<>("produktion","10", "Bar")).get();

        for(int i = 8; i < 12; i++) {
            producer1.send(new ProducerRecord<>("produktion",i, "Foo")).get();
        }

        System.out.println("fertig ");

        producer1.close();
        producer2.close();
    }

}
