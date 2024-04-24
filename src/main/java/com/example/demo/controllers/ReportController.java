package com.example.demo.controllers;

import com.example.demo.models.Report;
import com.example.demo.models.Train;
import com.example.demo.repositories.TrainRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.repositories.ReportRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.models.Carriage;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportRepository reportRepository;
    private final TrainRepository trainRepository;
    public ReportController(ReportRepository reportRepository, TrainRepository trainRepository) {
        this.reportRepository = reportRepository;
        this.trainRepository = trainRepository;
    }

    @PostMapping
    public ResponseEntity<Report> addReport(@Valid @RequestBody Report report) {
        Report savedReport = reportRepository.save(report);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping
    public ResponseEntity<List<ReportWithCarriages>> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        List<ReportWithCarriages> reportsWithCarriages = reports.stream()
                .map(report -> {
                    Train train = trainRepository.findById(report.getTrainId()).orElse(null);
                    return new ReportWithCarriages(report, train != null ? train.getCarriages() : null);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(reportsWithCarriages);
    }

    @Setter
    @Getter
    static class ReportWithCarriages {
        private Report report;
        private List<Carriage> carriages;

        public ReportWithCarriages(Report report, List<Carriage> carriages) {
            this.report = report;
            this.carriages = carriages;
        }

    }
}
