package com.example.demo.controllers;

import com.example.demo.models.Train;
import com.example.demo.repositories.CarriageRepository;
import com.example.demo.repositories.TrainRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carriage")
public class CarriageController {
    private final TrainRepository trainRepository;
    private final CarriageRepository carriageRepository;

    public CarriageController(TrainRepository trainRepository, CarriageRepository carriageRepository) {
        this.trainRepository = trainRepository;
        this.carriageRepository = carriageRepository;
    }

    @DeleteMapping("{carriageId}")
    public ResponseEntity<?> deleteCarriage(@PathVariable String carriageId) {
        boolean carriageDeleted = false;

        Iterable<Train> allTrains = trainRepository.findAll();
        Train foundTrain = null;
        for (Train train : allTrains) {
            boolean exists = train.getCarriages().removeIf(carriage -> carriage.getId().equals(carriageId));
            if (exists) {
                foundTrain = train;
                carriageDeleted = true;
                break;
            }
        }

        if (carriageDeleted) {
            carriageRepository.deleteById(carriageId);
            trainRepository.save(foundTrain);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
