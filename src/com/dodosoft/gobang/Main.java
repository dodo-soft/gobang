package com.dodosoft.gobang;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(new File("src/com/dodosoft/gobang/MainView.fxml").toURI().toURL());
        Parent root = loader.load();
        final GobangModel model = new ArrayGobangModel(19, 19);

        Controller ctrl = loader.getController();
        ctrl.setModel(model);

        //final GobangView view = new GobangView();
        //view.setModel(model);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.sizeToScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
