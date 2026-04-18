package com.agrosmart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic farmDataTopic(){
        return new NewTopic("farm-data-topic",3,(short)1);
    }

    @Bean
    public NewTopic recommendationTopic() {
        return new NewTopic("recommendation-topic", 3, (short) 1);
    }
}