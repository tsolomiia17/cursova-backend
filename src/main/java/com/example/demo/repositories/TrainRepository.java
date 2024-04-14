package com.example.demo.repositories;

import com.example.demo.models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TrainRepository extends MongoRepository<Train, String> {
    //List<Train> findBySubProgramId(String subProgramId);
}
