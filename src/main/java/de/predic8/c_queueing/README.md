# Vorbereitung

1. Start node-0
1. Falls nötig: ./bin/kafka-topics.sh --delete --zookeeper localhost:2181  --topic baz
1. ./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic produktion

# Switch of Partition assignment

1. exec SimpleConsumer
1. Welche Partitions wurden den Consumer zugeordnet?

Assigned to :
baz-1 baz-2 baz-0 

1. exec SimpleConsumer (2. Prozess)
1. Welche Partitions wurden den Consumer zugeordnet?

Assigned to :
baz-2 

1. Welche Partitions sind noch dem ersten Consumer zugeordnet?

1. exec SimpleConsumer (3. Prozess)
1. exec SimpleProducer (4. Prozess)
1. Betrachte die Ausgabe aller Consumer


Consumer 1:

Assigned to :
baz-1 
Partitions: [baz-1] Count: 3
partition=1, offset= 0, key= 4, value= Message: 4
partition=1, offset= 1, key= 6, value= Message: 6
partition=1, offset= 2, key= 10, value= Message: 10


Consumer 2:

baz-0 
Partitions: [baz-0] Count: 4
partition=0, offset= 0, key= 1, value= Message: 1
partition=0, offset= 1, key= 5, value= Message: 5
partition=0, offset= 2, key= 7, value= Message: 7
partition=0, offset= 3, key= 8, value= Message: 8


Consumer 3:
Assigned to :
baz-2 
Partitions: [baz-2] Count: 3
partition=2, offset= 0, key= 2, value= Message: 2
partition=2, offset= 1, key= 3, value= Message: 3
partition=2, offset= 2, key= 9, value= Message: 9


1. Wo gibt es eine Ausgabe?
1. Consumer mit Ausgabe abschießen
1. exec SimpleProducer
1. Wo gibt es eine Ausgabe?


# Troubleshoting

* Change group-id
