package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Carriages")
@Getter
public class Carriage {
    @Id
    private String id;

    @Setter
    @NotBlank(message = "Type cannot be empty")
    private String type;

    @Setter
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Setter
    @Indexed(unique = true)
    @NotBlank(message = "Сarriage Number cannot be empty")
    private int carriageNumber;

    @Setter
    @NotBlank(message = "Owner cannot be empty")
    private String owner;
}