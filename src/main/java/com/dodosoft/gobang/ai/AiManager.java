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

import com.dodosoft.gobang.model.GobangModel;
import com.dodosoft.gobang.model.Judgement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * @author Yuhi Ishikura
 */
public class AiManager {

    private final GobangModel model;
    private final Judgement judgement;
    private final Ui ui;
    private Map<String, Class<? extends Ai>> ai = new LinkedHashMap<String, Class<? extends Ai>>();
    private String ai1;
    private String ai2;

    public AiManager(GobangModel model, Judgement judgement, Ui ui) {
        assert model != null;
        assert judgement != null;

        this.model = model;
        this.judgement = judgement;
        this.ui = ui;

        loadDefautAi();
    }

    private void loadDefautAi() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("ai.list")))) {
            br.lines().forEach((line) -> {
                if (line.length() == 0) {
                    return;
                }
                final String[] s = line.split("\\s");
                if (s.length != 2) {
                    throw new IllegalStateException("Unsupported format line : " + line);
                }
                final String name = s[0];
                final String className = s[1];
                try {
                    register(name, (Class<? extends Ai>)Class.forName(className));
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException ex) {
            throw new IllegalStateException();
        }
    }

    public Set<String> getAiNames() {
        return this.ai.keySet();
    }

    public String getNameOfAi(Ai ai) {
        for (Map.Entry<String, Class<? extends Ai>> entry : this.ai.entrySet()) {
            if (ai.getClass() == entry.getValue()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void register(String name, Class<? extends Ai> ai) {
        if (this.ai.containsKey(name)) {
            throw new IllegalArgumentException();
        }
        if (this.ai.containsValue(ai)) {
            throw new IllegalArgumentException();
        }
        this.ai.put(name, ai);
        if (this.ai1 == null) {
            this.ai1 = name;
        }
        if (this.ai2 == null) {
            this.ai2 = name;
        }
    }

    public void selectPlayer1(String aiName) {
        this.ai1 = aiName;
    }

    public String getPlayer1() {
        return this.ai1;
    }

    public void selectPlayer2(String aiName) {
        this.ai2 = aiName;
    }

    public String getPlayer2() {
        return this.ai2;
    }

    public Automator createAutomator() {
        return new Automator(this.model, this.judgement, this.ui, createAi(this.ai1), createAi(this.ai2));
    }

    private Ai createAi(String aiName) {
        Class<? extends Ai> ai = this.ai.get(aiName);
        if (ai == null) {
            throw new IllegalArgumentException();
        }
        try {
            return ai.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
