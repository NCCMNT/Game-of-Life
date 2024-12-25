package org.game.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.game.GameApp;

import java.io.IOException;


public class StartingController implements Controller {

    @FXML
    public Button setupButton;
    @FXML
    public TextField widthField;
    @FXML
    public TextField heightField;
    @FXML
    public TextField cellSizeField;

    private Scene scene;
    private Stage stage;
    private Parent root;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void initialize() {
        System.out.println("New app opened");
    }

    private int getValidatedValue(String text, int defaultValue) {
        if (text == null || !text.matches("[1-9]\\d*")) {
            return defaultValue;
        }
        return Integer.parseInt(text);
    }

    public void setupGrid(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("grid.fxml"));
            root = loader.load();
            SimulationController simulationController = loader.getController();

            int cols = getValidatedValue(widthField.getText(), 50);
            int rows = getValidatedValue(heightField.getText(), 50);
            int cellSize = getValidatedValue(cellSizeField.getText(), 20);

            simulationController.setCols(cols);
            simulationController.setRows(rows);
            simulationController.setCellSize(cellSize);
            simulationController.initialize();

            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            simulationController.setScene(scene);

            String cssPath = getClass().getClassLoader().getResource("darkmode.css").toExternalForm();
            scene.getStylesheets().add(cssPath);
            stage.setScene(scene);

            stage.setTitle("Game of Life");
            stage.setMinHeight(800);
            stage.setMinWidth(800);

            stage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
