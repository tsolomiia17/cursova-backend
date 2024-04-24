package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "Reports")
@Getter
public class Report {
    @Id
    private String id;

    @Setter
    @NotNull(message = "Report details cannot be empty")
    private List<ReportDetail> reportDetails;

    @Setter
    @NotBlank(message = "Train ID cannot be empty")
        private String trainId;

    @Setter
    private Date reportTime = new Date();

    @Setter
    @NotBlank(message = "Executor name cannot be empty")
    private String executorName;
}

@Setter
@Getter
class ReportDetail {
    @NotBlank(message = "Type cannot be empty")
    private String type;

    @NotBlank(message = "Direction cannot be empty")
    private String direction;
}
