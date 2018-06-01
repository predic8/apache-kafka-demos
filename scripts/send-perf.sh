kafka-producer-perf-test.sh \
--producer-props bootstrap.servers=localhost:9092 \
--throughput 15000000 \
--topic test \
--num-records 100000 \
--record-size 10

