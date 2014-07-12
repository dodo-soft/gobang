package com.dodosoft.gobang.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.LogManager;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(Main.class.getResourceAsStream("logging.properties"));

        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainView.fxml"));
        Parent root = loader.load();

        Controller ctrl = loader.getController();
        ctrl.initializeUI();
        primaryStage.getIcons().add(new Image("/com/dodosoft/gobang/view/sample-icon.png"));
        primaryStage.setTitle("Gobang");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
