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
package com.dodosoft.gobang;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Yuhi Ishikura
 */
public class GobangView extends GridPane implements GobangModel.Listener {

    private GobangModel model;
    private Map<CellKey, Label> cells = new HashMap<CellKey, Label>();
    private Judgement judgement;

    public GobangView() {
        this.judgement = new Judgement();
    }

    void setModel(final GobangModel model) {
        if (this.model != null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
        this.model.addListener(this.judgement);
        this.model.addListener(this);
        for (int y = 0; y < model.getHeight(); y++) {
            for (int x = 0; x < model.getWidth(); x++) {
                Label label = new Label();
                label.setStyle("-fx-border-style: dashed;");
                label.setMinWidth(30);
                label.setMinHeight(30);
                add(label, y, x);
                final CellKey key = new CellKey(x, y);
                this.cells.put(key, label);
                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(final MouseEvent event) {
                        model.mark(key.x, key.y, judgement.getCurrent());
                    }
                });
            }
        }
    }

    private Label getLabel(int x, int y) {
        return this.cells.get(new CellKey(x, y));
    }

    @Override
    public void onMark(final GobangModel model, final int x, final int y, final Go mark) {
        if (mark == Go.BLACK) {
            getLabel(x, y).setText("●");
        } else if (mark == Go.WHITE) {
            getLabel(x, y).setText("○");
        } else {
            throw new IllegalArgumentException("unsupported mark : " + mark);
        }
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