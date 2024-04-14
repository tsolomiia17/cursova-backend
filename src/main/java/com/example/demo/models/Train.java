package com.example.demo.models;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "Trains")
@Getter
public class Train {
    @Id
    private String id;
    private List<Carriage> carriages = new ArrayList<>();

    @Setter
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Setter
    @NotEmpty(message = "Directions cannot be empty")
    private List<String> directions;
}
