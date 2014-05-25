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

import javafx.application.Platform;

import java.util.concurrent.Semaphore;


/**
 * ビューへの変更を伴う処理を、JavaFXイベントディスパッチスレッド上で実行するラッパークラスです。
 *
 * @author Yuhi Ishikura
 */
public class JfxGobangModel implements GobangModel {

    private GobangModel model;

    public JfxGobangModel(GobangModel model) {
        this.model = model;
    }

    @Override
    public int getWidth() {
        return this.model.getWidth();
    }

    @Override
    public int getHeight() {
        return this.model.getHeight();
    }

    @Override
    public void mark(final int x, final int y, final Go mark) {
        runAndWait(() -> this.model.mark(x, y, mark));
    }

    private void runAndWait(final Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            final Semaphore s = new Semaphore(0);
            Platform.runLater(() -> {
                runnable.run();
                s.release();
            });
            try {
                s.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Go getMark(final int x, final int y) {
        return this.model.getMark(x, y);
    }

    @Override
    public void clear() {
        runAndWait(() -> this.model.clear());
    }

    @Override
    public void addListener(final Listener listener) {
        this.model.addListener(listener);
    }

    @Override
    public void removeListener(final Listener listener) {
        this.model.removeListener(listener);
    }
}
