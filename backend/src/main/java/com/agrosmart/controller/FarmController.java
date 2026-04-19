package com.agrosmart.controller;

import com.agrosmart.dto.FarmEvent;
import com.agrosmart.model.Farm;
import com.agrosmart.repository.FarmRepository;
import com.agrosmart.service.ProducerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
@CrossOrigin("*") //Disables CORS explicitly so our React frontend can connect securely
public class FarmController {
    private final FarmRepository farmRepository;
    private final ProducerService producerService;

    //Dependency Injection
    public FarmController(FarmRepository farmRepository,ProducerService producerService){
        this.farmRepository = farmRepository;
        this.producerService = producerService;
    }

    @PostMapping
    public Farm createFarm(@RequestBody Farm farm){
        //Save to Database
        Farm savedFarm = farmRepository.save(farm);

        //Formulate event payload and trigger Python AI Service via Kafka
        //Note: For now using 25.0C temp and 60.0% humidity as Mock Weather data

        FarmEvent event = new FarmEvent(
                savedFarm.getId(),
                savedFarm.getSoilType(),
                savedFarm.getCropType(),
                25.0,
                60.0
        );

        producerService.sendFarmData(event);

        return savedFarm;
    }

    @GetMapping
    public List<Farm> getAllFarms(){
        return farmRepository.findAll();
    }
}