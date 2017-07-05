package de.predic8.h_performance;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;
import java.util.Collections;

public class AssignmentDisplayRebalanceListener implements ConsumerRebalanceListener {

    private boolean resetted;


    @Override
    public void onPartitionsRevoked(Collection<TopicPartition>partitions) {
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.println("Assigned to :");

        for (TopicPartition partition : partitions) {
            System.out.print(partition );
        }

        System.out.println();
    }

}
