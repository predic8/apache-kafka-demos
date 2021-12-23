package de.predic8.c_queueing;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import static java.lang.Math.random;
import static java.lang.Math.round;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class RandomProducer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");


        try(Producer<Long, String> producer = new KafkaProducer<>(props, new LongSerializer(), new StringSerializer())) {

            System.out.println("Start sending!");
            for (int i = 1; i <= 12; i++) {
                producer.send(new ProducerRecord<>("produktion", round(random() * 6) + 1, "Message: " + i));
            }
            System.out.println("done!");

        }
    }
}
