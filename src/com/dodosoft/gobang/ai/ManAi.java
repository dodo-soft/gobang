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

import java.util.concurrent.Semaphore;


/**
 * 人間がプレイする{@link Ai}です。
 *
 * @author Yuhi Ishikura
 */
public final class ManAi extends Ai {

    private Semaphore semaphore;

    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
        if (mark == getMark()) {
            if (this.semaphore == null) {
                throw new IllegalStateException();
            }
            this.semaphore.release();
        }
    }

    @Override
    protected void onYourTurn() throws InterruptedException {
        this.semaphore = new Semaphore(0);
        final boolean userInputEnabledOld = isUserInputEnabled();
        setUserInputEnabled(true);
        try {
            this.semaphore.acquire();
        } finally {
            setUserInputEnabled(userInputEnabledOld);
        }
    }
}
