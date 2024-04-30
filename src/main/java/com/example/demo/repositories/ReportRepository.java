package com.example.demo.repositories;

import com.example.demo.models.Report;
import com.example.demo.models.Train;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String> {
    //List<Train> findBySubProgramId(String subProgramId);
    List<Report> findByTrainId(String trainId);
}
