package de.predic8.d_log_compaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

import static org.apache.kafka.clients.producer.ProducerConfig.*;

public class SimpleProducer {

    public static void main(String[] args) throws InterruptedException {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ACKS_CONFIG, "all");
        props.put(RETRIES_CONFIG, 0);
        props.put(BATCH_SIZE_CONFIG, 25000); // nicht warten
        props.put(LINGER_MS_CONFIG, 200);
        props.put(BUFFER_MEMORY_CONFIG, 33554432);
        props.put(KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        StringBuffer muellBuffer = new StringBuffer();
        for(int j = 0; j < 100000; j++) {
            muellBuffer.append(j);
        }
        String muell = muellBuffer.toString();

        Producer<String, String> producer = new KafkaProducer<>(props);

        System.out.println("Start sending!");
        for(int i = 1; i <= 10000; i++) {
            int key = i % 10;
            producer.send(new ProducerRecord<>("produktion", Integer.toString(key), muell));
        }
        System.out.println("done!");

        producer.close();
    }
}
