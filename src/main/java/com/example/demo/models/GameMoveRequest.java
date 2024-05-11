package com.example.demo.models;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GameMoveRequest {
    @Valid
    private Cell cell;
    private List<Cell> board;
}
