package com.dodosoft.gobang;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        final GobangModel model = new ArrayGobangModel(19, 19);
        final GobangView view = new GobangView();
        view.setModel(model);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(view));
        primaryStage.show();
        primaryStage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
