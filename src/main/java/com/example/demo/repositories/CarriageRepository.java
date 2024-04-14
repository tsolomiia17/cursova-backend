package com.example.demo.repositories;

import com.example.demo.models.Carriage;
import com.example.demo.models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CarriageRepository extends MongoRepository<Carriage, String> {
    boolean existsByCarriageNumber(int carriageNumber);
    void deleteAllByIdIn(List<String> ids);
}
