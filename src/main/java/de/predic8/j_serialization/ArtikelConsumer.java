package de.predic8.j_serialization;

import java.util.Properties;

import static org.apache.kafka.clients.consumer.ConsumerConfig.*;

public class ArtikelConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(GROUP_ID_CONFIG, "a");
        props.put(ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(SESSION_TIMEOUT_MS_CONFIG, "30000");


    }
}
