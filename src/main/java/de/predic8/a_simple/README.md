# Vorbereitung

1. ./bin/zookeeper-server-start.sh myconfig/zookeeper.properties 
1. ./bin/kafka-server-start.sh myconfig/node-0.properties 
1. ./bin/kafka-topics.sh --describe --zookeeper localhost:2181  --topic produktion
1. ./bin/kafka-topics.sh  --zookeeper localhost:2181 --describe produktion

# Demo: Simple

1. exec SimpleProducer
1. exec SimpleConsumer_A => Keine Nachrichten
1. exec SimpleProducer
1. exec SimpleConsumer_A => Ab Offset 11

# Demo: Performance

1. 1.000.000 Messages


# Demo: 1:n mit 3 Consumer

1. exec SimpleConsumer_A
1. exec SimpleConsumer_B
1. exec SimpleConsumer_C
1. exec SimpleProducer
1. Alle Konsolen ansehen
