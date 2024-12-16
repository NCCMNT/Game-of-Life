package org.game.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimulationController {

    @FXML
    private GridPane gridPane;

    private int rows = 100;
    private int cols = 100;
    private int cellSize = 20;

    private boolean[][] cellStates;

    public void initialize() {
        System.out.println("Controller initialized");
        cellStates = new boolean[rows][cols];
        generateGrid();
    }

    private void generateGrid() {
        gridPane.getChildren().clear();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.getStyleClass().add("cell");

                final int r = row;
                final int c = col;
                cell.setOnMouseClicked(mouseEvent -> toggleCellState(r, c, cell));

                gridPane.add(cell, col, row);
            }
        }
    }

    private void toggleCellState(int row, int col, Rectangle cell) {
        cellStates[row][col] = !cellStates[row][col];
        if (cellStates[row][col]) {
            cell.getStyleClass().remove("dead");
            cell.getStyleClass().add("alive");
        }
        else{
            cell.getStyleClass().remove("alive");
            cell.getStyleClass().add("dead");
        }
    }
}
