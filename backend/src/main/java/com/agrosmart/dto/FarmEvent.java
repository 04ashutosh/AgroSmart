package com.agrosmart.dto;

public class FarmEvent {
    private Long farmId;
    private String soilType;
    private String cropType;
    private Double temperature;
    private Double humidity;

    //Default constructor needed for JSON deserialization
    public  FarmEvent() {}

    public FarmEvent(Long farmId,String soilType,String cropType,Double temperature,Double humidity) {
        this.farmId = farmId;
        this.soilType = soilType;
        this.cropType = cropType;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    //Getters and Setters
    public Long getFarmId() { return farmId; }
    public void setFarmId(Long farmId) { this.farmId = farmId; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature;}
    public Double getHumidity() { return humidity; }
    public void setHumidity(Double humidity) { this.humidity = humidity; }
}

