package com.dodosoft.gobang.view;

import com.dodosoft.gobang.ai.AiManager;
import com.dodosoft.gobang.ai.Automator;
import com.dodosoft.gobang.ai.Example_StraightAi;
import com.dodosoft.gobang.ai.ManAi;
import com.dodosoft.gobang.model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.stream.Stream;


public class Controller implements GobangModel.Listener {

    @FXML
    private Label textLabel;
    @FXML
    private GobangView gobangView;
    @FXML
    private ComboBox<String> player1Ai;
    @FXML
    private ComboBox<String> player2Ai;
    @FXML
    private Label messageLabel;

    private GobangModel model;
    private final Judgement judgement;
    private AiManager aiManager;

    public Controller() {
        this.model = new ArrayGobangModel(19, 19);
        this.judgement = new Judgement();
        model.addListener(judgement);
        model.addListener(this);
    }

    public void initializeUI() {
        this.gobangView.initialize(this.model, this.judgement);
        this.gobangView.setUserInputEnabled(false);
        this.messageLabel.setVisible(false);

        this.messageLabel.setStyle("-fx-background-color:rgba(0,0,0,0.5); -fx-text-fill:rgb(220,220,220); -fx-border-style:solid; -fx-border-radius:10; -fx-background-radius: 10; -fx-font-size: 20");

        this.aiManager = new AiManager(new JfxGobangModel(this.model), this.judgement, this.gobangView);
        this.aiManager.register("Man", ManAi.class);
        this.aiManager.register("Straight", Example_StraightAi.class);

        Stream.of(this.player1Ai, this.player2Ai).forEach(aiCombo -> {
            final ObservableList<String> list = aiCombo.itemsProperty().get();
            list.clear();
            for (String aiName : this.aiManager.getAiNames()) {
                list.add(aiName);
            }
        });
        this.player1Ai.getSelectionModel().select(this.aiManager.getPlayer1());
        this.player2Ai.getSelectionModel().select(this.aiManager.getPlayer2());
    }

    @FXML
    private void clearButtonPressed(ActionEvent actionEvent) {
        this.model.clear();
        this.messageLabel.setVisible(false);
        this.gobangView.setUserInputEnabled(false);
    }

    @FXML
    public void startButtonPressed(ActionEvent actionEvent) {
        this.aiManager.selectPlayer1(this.player1Ai.getSelectionModel().getSelectedItem());
        this.aiManager.selectPlayer2(this.player2Ai.getSelectionModel().getSelectedItem());
        final Automator automator = this.aiManager.createAutomator();
        automator.start();
    }

    @Override
    public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {
        // do nothing
    }

    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
        this.gobangView.setMark(x, y, mark);
        if (this.judgement.getState() == Judgement.State.FINISHED) {
            this.messageLabel.setVisible(true);
            this.messageLabel.setText("Winner is " + this.judgement.getWinner());
        }
    }

    @Override
    public void onClear(final GobangModel model) {
        this.gobangView.clearMarks();
    }

}
