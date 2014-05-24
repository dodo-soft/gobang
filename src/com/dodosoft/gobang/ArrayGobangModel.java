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

import java.util.ArrayList;
import java.util.List;


/**
 * @author Yuhi Ishikura
 */
final class ArrayGobangModel implements GobangModel {

    private Go[][] marks;
    private List<Listener> listeners = new ArrayList<Listener>();

    ArrayGobangModel(int width, int height) {
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
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(final Listener listener) {
        this.listeners.remove(listener);
    }
}
