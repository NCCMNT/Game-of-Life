package org.game.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.game.model.GridMap;
import org.game.model.Position;
import org.game.simulation.Simulation;
import org.game.utils.MapObserver;

import java.util.HashMap;
import java.util.List;

public class SimulationController implements MapObserver{

    @FXML
    private GridPane gridPane;

    private int rows = 20;
    private int cols = 20;
    private int cellSize = 20;

    private HashMap<Position, Boolean> cellStates;
    private HashMap<Position, Rectangle> cellRectangles;
    private Simulation simulation;

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void initialize() {
        System.out.println("Controller initialized");
        cellStates = new HashMap<>();
        cellRectangles = new HashMap<>();

        generateGrid();
    }

    private void generateGrid() {
        gridPane.getChildren().clear();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                Position position = new Position(row, col);
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cellStates.put(position, false);
                cellRectangles.put(position, cell);

                cell.getStyleClass().add("cell");
                cell.setOnMouseClicked(mouseEvent -> toggleCellState(position));

                gridPane.add(cell, col, row);
            }
        }
    }

    private void toggleCellState(Position position) {
        Rectangle cell = cellRectangles.get(position);
        cellStates.put(position, !cellStates.get(position));
        if (cellStates.get(position)) {
            cell.getStyleClass().remove("dead");
            cell.getStyleClass().add("alive");
        }
        else{
            cell.getStyleClass().remove("alive");
            cell.getStyleClass().add("dead");
        }
    }

    public void onStartButtonClicked() {
        GridMap grid = new GridMap(this.rows, this.cols, this.cellStates);
        grid.addObserver(this);
        Simulation simulation = new Simulation(grid);
        this.setSimulation(simulation);
        this.simulation.run();
    }

    public void onStopButtonClicked() {
        this.simulation.stop();
    }

    public void onResetButtonClicked() {
        this.onStopButtonClicked();
        gridPane.getChildren().clear();
        initialize();
    }

    @Override
    public void cellsToBeChanged(GridMap gridMap, List<Position> positions) {
        for (Position position : positions) {
            this.toggleCellState(position);
        }
    }
}
