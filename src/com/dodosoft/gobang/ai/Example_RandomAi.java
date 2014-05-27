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

import com.dodosoft.gobang.model.Go;
import com.dodosoft.gobang.model.GobangModel;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Yuhi Ishikura
 */
public class Example_RandomAi extends Ai {

    private int remainingCells = -1;

    @Override
    protected void onYourTurn() throws InterruptedException {
        final int n = (int)(Math.random() * this.remainingCells);
        final AtomicInteger i = new AtomicInteger(0);
        forEachEmptyCells((x, y, mark) -> {
            if (i.getAndIncrement() == n) {
                mark(x, y);
                return true;
            }
            return false;
        });
        final AtomicBoolean marked = new AtomicBoolean(false);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                final Go mark = getMark(x, y);
                if (mark == null) {

                }
            }
        }
    }

    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
        if (this.remainingCells < 0) {
            this.remainingCells = getWidth() * getHeight();
        }
        this.remainingCells--;
    }
}
