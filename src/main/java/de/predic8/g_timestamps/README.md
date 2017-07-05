# Vorbereitung

1. ./bin/zookeeper-server-start.sh myconfig/zookeeper.properties 
1. ./bin/kafka-server-start.sh myconfig/node-0.properties 
1. ./bin/kafka-topics.sh  --zookeeper localhost:2181 --describe produktion

