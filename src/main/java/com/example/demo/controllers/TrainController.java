package com.example.demo.controllers;

import com.example.demo.models.Carriage;
import com.example.demo.repositories.TrainRepository;
import com.example.demo.repositories.CarriageRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.Train;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trains")
public class TrainController {
    private final TrainRepository trainRepository;
    private final CarriageRepository carriageRepository;

    public TrainController(TrainRepository trainRepository, CarriageRepository carriageRepository) {
        this.trainRepository = trainRepository;
        this.carriageRepository = carriageRepository;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping
    public Train createTrain(@Valid @RequestBody Train train) {
        return trainRepository.save(train);
    }

    @GetMapping
    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable String id) {
        Optional<Train> trainOptional = trainRepository.findById(id);
        return trainOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable String id) {
        Optional<Train> trainOptional = trainRepository.findById(id);
        if (!trainOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Train train = trainOptional.get();
        List<String> carriageIds = train.getCarriages().stream()
                .map(Carriage::getId)
                .collect(Collectors.toList());

        carriageRepository.deleteAllByIdIn(carriageIds);
        trainRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{trainId}/carriages")
    public Train addCarriageToTrain(@Valid @PathVariable String trainId, @RequestBody Carriage carriage) {
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> new RuntimeException("Поїзд з ідентифікатором " + trainId + " не знайдено"));
        if (carriageRepository.existsByCarriageNumber(carriage.getCarriageNumber())) {
            throw new RuntimeException("Carriage number must be unique");
        }
        Set<String> existingCarriageTypes = new HashSet<>();
        for (Carriage existingCarriage : train.getCarriages()) {
            existingCarriageTypes.add(existingCarriage.getType());
        }
        System.out.println(existingCarriageTypes);
        System.out.println(carriage.getType());
        if(!existingCarriageTypes.contains(carriage.getType())){
            if (existingCarriageTypes.size() >= train.getDirections().size()) {
                throw new RuntimeException("Кількість типів вагонів перевищує кількість напрямків потяга");
            }
        }

        Carriage createdCarriage = carriageRepository.save(carriage);

        train.getCarriages().add(createdCarriage);

        return trainRepository.save(train);
    }



}
