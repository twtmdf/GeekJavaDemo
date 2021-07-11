# 启动命令
./bin/kafka-server-start.sh config/server3.properties
# 创建发送者和消费者
./bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic test32 --partitions 3 --replication-factor 2
./bin/kafka-console-producer.sh --bootstrap-server localhost:9003 --topic test32
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9001 --topic test32 --from-beginning
# 查看topic
./bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic test32
# 发送性能测试命令
./bin/kafka-producer-perf-test.sh --topic test32 --num-records 100000 --record-size 1000 --throughput 20 --producer-props bootstrap.servers=localhost:9092
# 消费性能测试命令
./bin/kafka-consumer-perf-test.sh --bootstrap-server localhost:9092 --topic test32 --fetch-size 1048576 --messages 10000 --threads 1
