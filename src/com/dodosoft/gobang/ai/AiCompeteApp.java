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

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;


/**
 * @author Yuhi Ishikura
 */
public class AiCompeteApp {

    public static void main(String[] args) {
        AtomicInteger white = new AtomicInteger(0);
        AtomicInteger black = new AtomicInteger(0);
        IntStream.range(0, 10000).parallel().forEach((i) -> {
            final GobangModel model = new ArrayGobangModel(19, 19);
            final Judgement judgement = new Judgement();
            model.addListener(judgement);

            final Ai ai1 = new Example_RandomAi();
            final Ai ai2 = new Example_RandomAi();

            final Automator automator = new Automator(model, judgement, new NullUi(), ai1, ai2);
            automator.setMinimumExecutionTime(0);
            automator.startSync();
            if (judgement.getWinner() == Go.WHITE) {
                white.incrementAndGet();
            } else if (judgement.getWinner() == Go.BLACK) {
                black.incrementAndGet();
            }
        });
        System.out.println("White: " + white);
        System.out.println("Black: " + black);
    }

}
