package com.agrosmart.service;

import com.agrosmart.model.Recommendation;
import com.agrosmart.repository.RecommendationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private final RecommendationRepository repository;

    public ConsumerService(RecommendationRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "recommendation-topic", groupId = "backend-group")
    public void consumeRecommendation(Recommendation recommendation){
        repository.save(recommendation);
        System.out.println("Saved AI recommendation to Database for Farm ID: "+recommendation.getFarmId());
    }
}
