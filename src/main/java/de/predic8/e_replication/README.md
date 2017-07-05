# Vorbereitung:

./bin/zookeeper-server-start.sh myconfig/zookeeper.properties 

./bin/kafka-server-start.sh myconfig/node-0.properties
./bin/kafka-server-start.sh myconfig/node-1.properties
./bin/kafka-server-start.sh myconfig/node-2.properties


# Durchführung

1. ./bin/kafka-server-start.sh myconfig/node-0.properties
1. ./bin/kafka-server-start.sh myconfig/node-1.properties
1. ./bin/kafka-server-start.sh myconfig/node-2.properties
1. exec SimpleProducer
1. exec SimpleConsumer 
1. Ausgabe Consumer ansehen
1. ./bin/kafka-topics.sh --describe --zookeeper localhost:2181  --topic bar
1. kill node-1
1. Ausgabe Consumer ansehen
1. kill node-0
1. decribe topic
1. Wer ist leader
1. start node-0
1. decribe topic



# Compacting



# Troubleshoting

nfig.BATCH_SIZE_CONFIG hat Einfluss auf die Anzahl der übrigbleibenden Einträge