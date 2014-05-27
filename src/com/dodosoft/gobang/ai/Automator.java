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
import com.dodosoft.gobang.model.Judgement;


/**
 * @author Yuhi Ishikura
 */
public class Automator implements Runnable {

    private final Ai ai1;
    private final Ai ai2;
    private final Judgement judgement;
    private final GobangModel model;
    private final Ui ui;
    private Ai current;
    private long minimumExecutionTime = 100;
    private Thread thread;

    Automator(GobangModel model, Judgement judgement, Ui ui, Ai ai1, Ai ai2) {
        assert model != null;
        assert judgement != null;
        assert ai1 != null;
        assert ai2 != null;

        this.model = model;
        this.ui = ui;
        this.ai1 = ai1;
        this.ai2 = ai2;
        this.judgement = judgement;
        this.current = this.ai1;
    }

    public long getMinimumExecutionTime() {
        return minimumExecutionTime;
    }

    public void setMinimumExecutionTime(final long minimumExecutionTime) {
        this.minimumExecutionTime = minimumExecutionTime;
    }

    public void start() {
        synchronized (this) {
            if (isRunning()) {
                throw new IllegalStateException();
            }
            this.thread = new Thread(this);
            this.thread.setDaemon(true);
            this.thread.start();
        }
    }

    public void stop() {
        synchronized (this) {
            if (isRunning() == false) {
                throw new IllegalStateException();
            }
            this.thread.interrupt();
            this.thread = null;
        }
    }

    public void stopSafely() {
        synchronized (this) {
            if (isRunning()) {
                stop();
            }
        }
    }

    private boolean isRunning() {
        synchronized (this) {
            return this.thread != null;
        }
    }

    @Override
    public void run() {
        this.ui.setUserInputEnabled(false);

        this.ai1.start(this.model, this.judgement, Go.WHITE, this.ui);
        this.ai2.start(this.model, this.judgement, Go.BLACK, this.ui);
        try {
            while (this.judgement.getState() != Judgement.State.FINISHED) {
                final long started = System.currentTimeMillis();
                executeAi();
                final long elapsedTime = System.currentTimeMillis() - started;
                final long sleepTime = this.minimumExecutionTime - elapsedTime;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (InterruptedException e) {
            return;
        } finally {
            this.ai1.stop();
            this.ai2.stop();
            this.ui.setUserInputEnabled(true);
        }
    }

    private void executeAi() {
        this.current.execute();

        if (this.current == this.ai1) {
            this.current = this.ai2;
        } else if (this.current == this.ai2) {
            this.current = this.ai1;
        } else {
            throw new IllegalStateException();
        }
    }

}
