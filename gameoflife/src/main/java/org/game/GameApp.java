package org.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.game.controller.StartingController;

public class GameApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("startingPage.fxml"));
        BorderPane root = loader.load();

        Scene scene = configureStage(stage, root, "startingPage.css");
        stage.show();

        StartingController startingController = loader.getController();
        startingController.setStage(stage);

        startingController.setScene(scene);
    }

    public Scene configureStage(Stage stage, BorderPane root, String cssFile) {
        Scene scene = new Scene(root);

        String cssPath = getClass().getClassLoader().getResource(cssFile).toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setScene(scene);
        stage.setTitle("Game of Life");
        stage.setMinHeight(800);
        stage.setMinWidth(800);

        return scene;
    }
}
