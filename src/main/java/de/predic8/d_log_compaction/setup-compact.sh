#!/usr/bin/env bash
# Compaction
export KAFKA_HOME=/Users/thomas/java/kafka/kafka_2.12-0.10.2.1

# Achtung: delete.topic.enable=true in config!

$KAFKA_HOME/bin/kafka-topics.sh --delete --zookeeper localhost:2181  --topic produktion
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic produktion \
                                                                              --config min.cleanable.dirty.ratio=0.01 \
                                                                              --config cleanup.policy=compact \
                                                                              --config segment.ms=100 \
                                                                              --config delete.retention.ms=100