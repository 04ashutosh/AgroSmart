package com.agrosmart.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "farm_id")
    private Long farmId;

    @Column(name = "irrigation_advice", columnDefinition = "TEXT")
    private String irrigationAdvice;

    @Column(name = "fertilizer_suggestion", columnDefinition = "TEXT")
    private String fertilizerSuggestion;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    //Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFarmId() { return farmId; }
    public void setFarmId(Long farmId) { this.farmId = farmId; }
    public String getIrrigationAdvice() { return irrigationAdvice; }
    public void setIrrigationAdvice(String irrigationAdvice) { this.irrigationAdvice = irrigationAdvice; }
    public String getFertilizerSuggestion() { return fertilizerSuggestion; }
    public void setFertilizerSuggestion(String fertilizerSuggestion) { this.fertilizerSuggestion = fertilizerSuggestion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
