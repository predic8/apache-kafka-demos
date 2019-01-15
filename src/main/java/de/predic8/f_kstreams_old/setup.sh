export KAFKA_HOME=/Users/thomas/java/kafka/kafka_2.11-0.10.1.0
$KAFKA_HOME/bin/kafka-topics.sh --delete --zookeeper localhost:2181  --topic produktion
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic produktion