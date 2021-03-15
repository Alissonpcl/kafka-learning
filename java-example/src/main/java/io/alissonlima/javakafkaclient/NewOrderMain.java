package io.alissonlima.javakafkaclient;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        // This key will be used by Kafka to identify in which topic
        // partition the message will become available
        var key = UUID.randomUUID().toString();
        var value = key + ",456,789";
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);

        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("topic: " + data.topic() + " partition: " + data.partition() + " offset: " + data.offset() + " data: " + data.timestamp());
        };
        producer.send(record, callback).get();

        var email = "alissonpcl@alisson.com";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", email, email);
        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        Properties properties = new Properties();

        // Onde esta rodando o Kafka
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        // Key and value must be treated as String, so it will be
        // converted from Strings to Bytes
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
