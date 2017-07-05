# Achtung: delete.topic.enable=true in config!

if [ -z "$KAFKA_HOME" ]; then
    echo "KAFKA_HOME ist nicht gesetzt!"
    exit 1
fi

$KAFKA_HOME/bin/kafka-topics.sh --delete --zookeeper localhost:2181  --topic produktion