package de.predic8.f_streams;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;

import static java.lang.Math.random;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;

public class ContinuousNumberProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        Producer<Integer, Double> producer = new KafkaProducer<>(props, new IntegerSerializer(), new DoubleSerializer() );


        int i = 0;
        while ( true) {
            i++;
            producer.send(new ProducerRecord<>("numbers-cont", i, random() * 1000));
            Thread.sleep(1000);
        }

    }
}
