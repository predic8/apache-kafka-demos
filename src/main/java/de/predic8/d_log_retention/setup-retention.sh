# Compaction

# Achtung: delete.topic.enable=true in config!

export KAFKA_HOME=/Users/thomas/java/kafka/kafka_2.12-0.10.2.1
$KAFKA_HOME/bin/kafka-topics.sh --delete --zookeeper localhost:2181  --topic produktion
$KAFKA_HOME/bin/kafka-topics.sh  --create --zookeeper localhost:2181 \
                                  --partitions 1 \
                                  --replication-factor 1 \
                                  --topic produktion \
                                  --config cleanup.policy=delete \
                                  --config segment.ms=100 \
                                  --config delete.retention.ms=100