package com.agrosmart.service;

import com.agrosmart.dto.FarmEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private final KafkaTemplate<String, FarmEvent> kafkaTemplate;

    public ProducerService(KafkaTemplate<String,FarmEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFarmData(FarmEvent farmEvent){
        kafkaTemplate.send("farm-data-topic",farmEvent);
        System.out.println("Published farm event to Kafka: "+farmEvent.getFarmId());
    }
}
