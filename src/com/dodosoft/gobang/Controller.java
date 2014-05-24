package com.dodosoft.gobang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {
    @FXML
    private Label textLabel;
    @FXML
    private GobangView gobangView;
    @FXML
    private void clearButtonPressed(ActionEvent actionEvent) {
        System.out.println("clear button pressed");
    }
    public void initialize(GobangModel model, Judgement judgement){
        gobangView.initialize(model, judgement);
    }
}
