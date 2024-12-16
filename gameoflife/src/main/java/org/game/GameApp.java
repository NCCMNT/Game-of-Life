package org.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.game.controller.SimulationController;

public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("grid.fxml"));
        BorderPane root = loader.load();
        SimulationController controller = loader.getController();

        configureStage(stage, root);
        stage.show();
    }

    private void configureStage(Stage stage, BorderPane root) {
        var scene = new Scene(root);

        String cssPath = getClass().getClassLoader().getResource("grid.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle("Game of Life");

        root.setMinHeight(800);
        root.setMinWidth(800);
    }
}
