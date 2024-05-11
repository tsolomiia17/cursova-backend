package com.example.demo.controllers;
import com.example.demo.models.Board;
import com.example.demo.models.Cell;
import com.example.demo.models.GameMoveRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class TicTacToeController {
    @PostMapping("/checkMove")
    public boolean checkMove(@Valid @RequestBody GameMoveRequest gameMoveRequest) {
        Cell newCell = gameMoveRequest.getCell();
        List<Cell> board = gameMoveRequest.getBoard();
        if(!board.isEmpty()) {
            final int MAX_DISTANCE = 5;
            int minDistance = Integer.MAX_VALUE;
            for (Cell cell : board) {
                int distance = calculateChebyshevDistance(newCell, cell);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
            System.out.println(minDistance);
            if (minDistance > MAX_DISTANCE) {
                System.out.println("New cell is too far from the nearest existing cell.");
                return false;
            }

            System.out.println("New cell placement is valid.");
        }
        return true;
    }
    private int calculateChebyshevDistance(Cell cell1, Cell cell2) {
        return Math.max(Math.abs(cell1.getX() - cell2.getX()), Math.abs(cell1.getY() - cell2.getY()));
    }

}

