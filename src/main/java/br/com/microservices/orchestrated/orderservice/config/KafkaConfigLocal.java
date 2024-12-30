package br.com.microservices.orchestrated.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;



@Configuration
@Profile("local")
public class KafkaConfigLocal {

    private static final Integer PARTITION_COUNT = 1;
    private static final Integer REPLICA_COUNT = 1;

    @Value("${spring.kafka.topic.consumer}")
    private String consumer;

    @Value("${spring.kafka.topic.producer}")
    private String producer;

    private NewTopic buildTopic(String name) {
        return TopicBuilder
            .name(name)
            .partitions(PARTITION_COUNT)
            .replicas(REPLICA_COUNT)
            .build();
    }

    @Bean
    public NewTopic start() {
        return buildTopic(consumer);
    }

    @Bean
    public NewTopic ending() {
        return buildTopic(producer);
    }
}