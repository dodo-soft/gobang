package com.dodosoft.gobang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Controller {

    @FXML
    private Label textLabel;
    @FXML
    private GobangView gobangView;

    private GobangModel model;

    @FXML
    private void clearButtonPressed(ActionEvent actionEvent) {
        this.model.clear();
    }

    public void initialize(GobangModel model, Judgement judgement) {
        this.model = model;
        gobangView.initialize(model, judgement);
    }
}
