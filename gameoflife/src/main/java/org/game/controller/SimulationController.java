package org.game.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.game.model.GridMap;
import org.game.model.Position;
import org.game.simulation.Simulation;
import org.game.utils.MapObserver;

import java.util.HashMap;
import java.util.List;

public class SimulationController implements MapObserver, Controller{

    @FXML
    public Button startButton;
    @FXML
    public Button stopButton;
    @FXML
    public Button resetButton;
    @FXML
    public Button themeButton;
    @FXML
    public Slider speedSlider;
    @FXML
    public Label generationLabel;
    @FXML
    private GridPane gridPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox backgroundVBox;

    private Scene scene;
    private Stage stage;
    private int rows = 50;
    private int cols = 50;
    private int cellSize = 20;

    private HashMap<Position, Boolean> cellStates;
    private HashMap<Position, Rectangle> cellRectangles;
    private Simulation simulation;
    private boolean darkMode = true;
    private boolean resumable = false;

    private Scale scaleTransform = new Scale(1,1);

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        System.out.println("Scene set: " + (scene != null));
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        System.out.println("Controller initialized");
        cellStates = new HashMap<>();
        cellRectangles = new HashMap<>();

        generateGrid();

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (simulation != null) {
                simulation.setSpeed(newValue.intValue());
            }
        });

        stopButton.setDisable(true);
        resetButton.setDisable(true);

        backgroundVBox.getTransforms().add(scaleTransform);

        scrollPane.setOnScroll(event -> {
            if (event.isControlDown()) {
                double zoomFactor = event.getDeltaY() > 0 ? 1.1 : 0.9;
                scaleTransform.setX(scaleTransform.getX() * zoomFactor);
                scaleTransform.setY(scaleTransform.getY() * zoomFactor);
            }
        });
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
                cell.setOnMouseClicked(mouseEvent -> {
                    if (!startButton.isDisable()) toggleCellState(position);
                });

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

    public void toggleTheme(){
        if (scene == null) return;
        scene.getStylesheets().clear();
        if (!darkMode) {
            themeButton.setText("Light theme");
            String cssPath = getClass().getClassLoader().getResource("darkmode.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        }
        else {
            themeButton.setText("Dark theme");
            String cssPath = getClass().getClassLoader().getResource("lightmode.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
        }
        darkMode = !darkMode;
        System.out.println("Theme changed to " + (darkMode ? "dark" : "light"));
    }

    public void onStartButtonClicked() {
        if (resumable) {
            simulation.resume();

            startButton.setDisable(true);
            stopButton.setDisable(false);


            Thread simulationThread = new Thread(simulation);
            simulationThread.setDaemon(true); // Ensure it stops when the app exits
            simulationThread.start();
        }
        else {
            System.out.println("Simulation started");
            GridMap grid = new GridMap(this.rows, this.cols, this.cellStates);
            grid.addObserver(this);

            Simulation simulation = new Simulation(grid, (int) speedSlider.getValue());
            this.setSimulation(simulation);

            startButton.setDisable(true);
            stopButton.setDisable(false);
            resumable = true;

            Thread simulationThread = new Thread(simulation);
            simulationThread.setDaemon(true); // Ensure it stops when the app exits
            simulationThread.start();
        }
    }

    public void onStopButtonClicked() {
        System.out.println("Simulation stopped");
        this.simulation.stop();
        stopButton.setDisable(true);
        resetButton.setDisable(false);
        startButton.setDisable(false);
    }

    public void onResetButtonClicked() {
        System.out.println("Simulation reset");
        this.onStopButtonClicked();
        gridPane.getChildren().clear();
        initialize();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        resetButton.setDisable(true);
        resumable = false;
    }

    @Override
    public void cellsToBeChanged(GridMap gridMap, List<Position> positions) {
        Platform.runLater(() -> {
            if (positions.isEmpty()) onResetButtonClicked();
            for (Position position : positions) {
                this.toggleCellState(position);
            }
        });
    }

    @Override
    public void generationCountChanged(int generationCount){
        Platform.runLater(() -> generationLabel.setText("Generations: " + generationCount));
    }
}
