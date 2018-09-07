package de.predic8;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

import java.util.Collection;

/**
 * Created by thomas on 31.10.16.
 */
public class LogRebalanceListener implements ConsumerRebalanceListener {

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition>partitions) {
        System.out.println("Revoked from: ");

        for (TopicPartition partition : partitions) {
            System.out.print("collection = [" + partition + "]");
        }

        System.out.println();
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        System.out.print("Assigned to: ");

        for (TopicPartition partition : partitions) {
            System.out.print(partition + " ");
        }

        System.out.println();

    }

}
