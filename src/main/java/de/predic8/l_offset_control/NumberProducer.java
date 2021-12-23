package de.predic8.l_offset_control;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;

import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import static de.predic8.utils.Utils.randInt;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG;

public class NumberProducer {

    public static void main(String[] args) throws Exception {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        props.put(ACKS_CONFIG,"1"); // Notwendig da min.insync.replicas im Broker vermutlich auf 3 steht.

        try(Producer<Integer, Integer> producer = new KafkaProducer<>(props, new IntegerSerializer(), new IntegerSerializer())) {
            for(int i=0; i < 10000; i++) {
                producer.send(new ProducerRecord<>("numbers", i, randInt(7)));
                Thread.sleep(2000);
            }
        }

    }
}
