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
package com.dodosoft.gobang.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


/**
 * @author Yuhi Ishikura
 */
public final class ArrayGobangModel implements GobangModel {

    private static Logger logger = Logger.getLogger(ArrayGobangModel.class.getName());
    private Go[][] marks;
    private List<Listener> listeners = Collections.synchronizedList(new ArrayList<Listener>());

    public ArrayGobangModel(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.marks = new Go[width][height];
    }

    @Override
    public int getWidth() {
        return marks.length;
    }

    @Override
    public int getHeight() {
        return marks[0].length;
    }

    @Override
    public void mark(final int x, final int y, final Go mark) {
        logger.finest(() -> String.format("#mark(x=%d, y=%d, mark=%s", x, y, mark));
        for (Listener listener : this.listeners) {
            listener.onPreMark(this, x, y, mark);
        }
        this.marks[x][y] = mark;
        for (Listener listener : this.listeners) {
            listener.onPostMark(this, x, y, mark);
        }
    }

    @Override
    public void clear() {
        logger.finest(String.format("#clear()"));
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                this.marks[x][y] = null;
            }
        }
        for (Listener listener : this.listeners) {
            listener.onClear(this);
        }
    }

    @Override
    public Go getMark(final int x, final int y) {
        return this.marks[x][y];
    }

    @Override
    public void addListener(final Listener listener) {
        logger.finest(() -> String.format("#addListener(listener=%s)", listener));
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(final Listener listener) {
        logger.finest(() -> String.format("#removeListener(listener=%s)", listener));
        this.listeners.remove(listener);
    }
}
