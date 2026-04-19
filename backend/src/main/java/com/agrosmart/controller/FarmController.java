package com.agrosmart.controller;

import com.agrosmart.model.Farm;
import com.agrosmart.repository.FarmRepository;
import com.agrosmart.service.ProducerService;
import com.agrosmart.service.WeatherService;
import com.agrosmart.dto.FarmEvent;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/farms")
@CrossOrigin("*")
public class FarmController {

    private final FarmRepository farmRepository;
    private final ProducerService producerService;
    private final WeatherService weatherService; // NEW

    public FarmController(FarmRepository farmRepository, ProducerService producerService, WeatherService weatherService) {
        this.farmRepository = farmRepository;
        this.producerService = producerService;
        this.weatherService = weatherService;
    }

    @PostMapping
    public Farm createFarm(@RequestBody Farm farm) {
        Farm savedFarm = farmRepository.save(farm);

        // Fetch Real Live Weather using Open-Meteo
        double[] weather = weatherService.getLiveWeather(savedFarm.getLatitude(), savedFarm.getLongitude());

        FarmEvent event = new FarmEvent(
                savedFarm.getId(),
                savedFarm.getSoilType(),
                savedFarm.getCropType(),
                weather[0], // Realistic Temp
                weather[1]  // Realistic Humidity
        );
        producerService.sendFarmData(event);

        return savedFarm;
    }

    @GetMapping
    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }
}
