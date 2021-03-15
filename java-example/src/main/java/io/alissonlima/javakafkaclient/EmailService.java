package io.alissonlima.javakafkaclient;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {

    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        consumer.subscribe(Collections.singletonList("ECOMMERCE_SEND_EMAIL"));

        while (true) {
            var records = consumer.poll(Duration.ofSeconds(1));

            if (records.isEmpty()) {
                continue;
            }

            System.out.println(String.format("Encontrei %d registros", records.count()));

            for (var record : records) {
                System.out.println("----------------------------");
                System.out.println("Sending e-mail");
                try {
                    Thread.sleep(Duration.ofSeconds(1).toMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Email sent");
            }
        }
    }

    private static Properties properties() {
        Properties properties = new Properties();

        //Onde esta rodando o Kafka
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");

        // Key and value must be treated as String, so it will be
        // converted from StrBytes to String
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Group is used to differentiate consumers that must consuming
        // messages from the same Topic
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, EmailService.class.getName());

        return properties;
    }
}
