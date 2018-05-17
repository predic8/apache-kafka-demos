package de.predic8.c_queueing;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import static java.lang.Math.random;
import static java.lang.Math.round;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class RandomProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 16384);
        props.put(LINGER_MS_CONFIG, 0);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.LongSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");


        org.apache.kafka.clients.producer.Producer<Long, String> producer = new KafkaProducer<>(props);

        System.out.println("Start sending!");
        for(int i = 1; i <= 12; i++) {
            producer.send(new ProducerRecord<>("produktion", round(random() * 6) + 1, "Message: " + i));
        }
        System.out.println("done!");

        producer.close();
    }
}
