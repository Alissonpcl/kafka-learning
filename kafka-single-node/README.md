>Commands below must be executed inside kafka-single-node folder

Getting container up

```docker-compose up -d```

Reading container logs

```docker-compose logs zookeeper | grep -i binding```
```docker-compose logs kafka | grep -i started```

Creating a topic

```docker-compose exec kafka kafka-topics --create --topic ECOMMERCE_NEW_ORDER --partitions 1 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181```
```docker-compose exec kafka kafka-topics --describe --topic ECOMMERCE_NEW_ORDER --zookeeper zookeeper:2181```
```docker-compose exec kafka kafka-topics --delete --topic ECOMMERCE_NEW_ORDER --zookeeper zookeeper:2181```

Producing test messages

```docker-compose exec kafka bash -c "seq 100 | kafka-console-producer --request-required-acks 1 --broker-list localhost:29092 --topic ECOMMERCE_NEW_ORDER && echo 'Produced 100 messages.'"```

Consuming messages

```docker-compose exec kafka kafka-console-consumer --bootstrap-server localhost:29092 --topic ECOMMERCE_NEW_ORDER --from-beginning --max-messages 100```