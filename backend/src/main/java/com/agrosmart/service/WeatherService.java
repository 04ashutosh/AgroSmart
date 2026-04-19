package com.agrosmart.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public double[] getLiveWeather(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) return new double[]{25.0, 60.0}; // Fallback

        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=%s&longitude=%s&current=temperature_2m,relative_humidity_2m", latitude, longitude);

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode current = root.path("current");

            double temp = current.path("temperature_2m").asDouble();
            double humidity = current.path("relative_humidity_2m").asDouble();

            return new double[]{temp, humidity};
        } catch (Exception e) {
            System.err.println("Weather API failed, using fallback metrics. " + e.getMessage());
            return new double[]{25.0, 60.0};
        }
    }
}
