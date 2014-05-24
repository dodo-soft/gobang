package com.dodosoft.gobang;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class Controller implements GobangModel.Listener {

    private final Judgement judgement;
    @FXML
    private Label textLabel;
    @FXML
    private GobangView gobangView;

    private GobangModel model;

    public Controller() {
        this.model = new ArrayGobangModel(19, 19);
        this.judgement = new Judgement();
        model.addListener(judgement);
        model.addListener(this);
    }

    public void start() {
        this.gobangView.initialize(this.model, this.judgement);
    }

    @FXML
    private void clearButtonPressed(ActionEvent actionEvent) {
        this.model.clear();
    }

    @Override
    public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {
        // do nothing
    }

    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
        this.gobangView.setMark(x, y, mark);
        if (this.judgement.getState() == Judgement.State.FINISHED) {
            System.out.println("Winner is " + this.judgement.getWinner());
        }
    }

    @Override
    public void onClear(final GobangModel model) {
        this.gobangView.clearMarks();
    }
}
