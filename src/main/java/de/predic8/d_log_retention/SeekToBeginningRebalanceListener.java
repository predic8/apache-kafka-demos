package de.predic8.d_log_retention;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;

public class SeekToBeginningRebalanceListener implements ConsumerRebalanceListener {


    private final KafkaConsumer<String, String> consumer;
    private boolean resetted;

    public SeekToBeginningRebalanceListener(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition>partitions) {
        System.out.println("Revoked from");

        for (TopicPartition partition : partitions) {
            System.out.println("collection = [" + partition + "]");
        }
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("Assigned to :");

        for (TopicPartition partition : partitions) {
            System.out.print(partition );
        }

        System.out.println();

        if (!resetted) {
            consumer.seekToBeginning(Collections.singletonList(new TopicPartition("produktion",0)));
            resetted = true;
        }
    }

}
