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
package com.dodosoft.gobang.ai;

import com.dodosoft.gobang.model.ArrayGobangModel;
import com.dodosoft.gobang.model.Go;
import com.dodosoft.gobang.model.GobangModel;
import com.dodosoft.gobang.model.Judgement;


/**
 * @author Yuhi Ishikura
 */
public class TestingFixture {

    public final AiPlayer ai;
    public final Player enemy = new PlayerImpl();
    public final Judgement judgement;
    private final GobangModel model;

    public TestingFixture(Ai ai) {
        this.model = new ArrayGobangModel(19, 19);
        this.judgement = new Judgement();
        this.ai = new AiPlayer(ai, this.model);
        this.model.addListener(this.judgement);
        ai.start(this.model, this.judgement, Go.WHITE, new NullUi());
    }

    public void printModel() {
        for (int y = 0; y < this.model.getHeight(); y++) {
            for (int x = 0; x < this.model.getWidth(); x++) {
                final Go mark = this.model.getMark(x, y);
                String c;
                if (mark == null) {
                    c = "-";
                } else {
                    c = mark.toString();
                }
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static interface Player {

        void mark(int x, int y);

    }

    private class PlayerImpl implements Player {

        @Override
        public void mark(final int x, final int y) {
            TestingFixture.this.model.mark(x, y, Go.BLACK);
        }
    }

    private static class AiPlayer implements Player, GobangModel.Listener {

        Ai ai;
        int lastX = -1;
        int lastY = -1;

        AiPlayer(Ai ai, GobangModel model) {
            this.ai = ai;
            model.addListener(this);
        }

        @Override
        public void mark(final int x, final int y) {
            this.lastX = -1;
            this.lastY = -1;
            ai.execute();
            if (this.lastX != x || this.lastY != y) {
                throw new AssertionError(String.format("Expected (%d, %d), but actually (%d, %d)", x, y, this.lastX, this.lastY));
            }
        }

        @Override
        public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {

        }

        @Override
        public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
            this.lastX = x;
            this.lastY = y;
        }

        @Override
        public void onClear(final GobangModel model) {

        }
    }

}
