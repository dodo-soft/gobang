/**
 * Copyright (C) 2014 uphy.jp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dodosoft.gobang.view;

import com.dodosoft.gobang.ai.Ui;
import com.dodosoft.gobang.model.Go;
import com.dodosoft.gobang.model.GobangModel;
import com.dodosoft.gobang.model.Judgement;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Yuhi Ishikura
 */
public class GobangView extends GridPane implements Ui {

    private GobangModel model;
    private Map<CellKey, Label> cells = new HashMap<CellKey, Label>();
    private boolean userInputEnabled = true;

    public GobangView() {
        setStyle("-fx-background-image:url(\"/com/dodosoft/gobang/view/background.jpg\");-fx-background-size: stretch;");
    }

    void initialize(final GobangModel model, final Judgement judgement) {
        if (this.model != null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getWidth(); x++) {
                Label label = new Label();
                label.setStyle("-fx-border-style: solid;");
                label.setMinWidth(30);
                label.setMinHeight(30);
                add(label, x, y);
                final CellKey key = new CellKey(x, y);
                this.cells.put(key, label);
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        if (isUserInputEnabled()) {
                            model.mark(key.x, key.y, judgement.getCurrent());
                        }
                    }
                });
            }
        }
    }

    public void setMark(int x, int y, Go mark) {
        String css = "-fx-border-style:solid;-fx-background-size: 90%;-fx-background-repeat:stretch;-fx-background-position:center;";
        if (mark == Go.BLACK) {
            css = css + "-fx-background-image:url(\"/com/dodosoft/gobang/view/black.png\")";
        } else if (mark == Go.WHITE) {
            css = css + "-fx-background-image:url(\"/com/dodosoft/gobang/view/white.png\")";
        } else if (mark == null) {
            // do nothing
        } else {
            throw new IllegalArgumentException("unsupported mark : " + mark);
        }
        final Label label = getLabel(x, y);
        label.setStyle(css);
    }

    private Label getLabel(int x, int y) {
        return this.cells.get(new CellKey(x, y));
    }

    public void clearMarks() {
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getWidth(); x++) {
                setMark(x, y, null);
            }
        }
    }

    @Override
    public boolean isUserInputEnabled() {
        return this.userInputEnabled;
    }

    @Override
    public void setUserInputEnabled(final boolean enabled) {
        this.userInputEnabled = enabled;
    }

    static class CellKey {

        int x, y;

        CellKey(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            final CellKey cellKey = (CellKey)o;

            if (x != cellKey.x) return false;
            if (y != cellKey.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }

}
