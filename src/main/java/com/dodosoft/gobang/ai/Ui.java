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

/**
 * @author Yuhi Ishikura
 */
public interface Ui {

    /**
     * 碁盤へのユーザによる入力が有効であるか調べます。
     *
     * @return 碁盤へのユーザによる入力が有効かどうか
     */
    boolean isUserInputEnabled();

    /**
     * 碁盤へのユーザによる入力の有効/無効を設定します。
     *
     * @param enabled 碁盤へのユーザによる入力を有効にするのであればtrue、そうでなければfalse
     */
    void setUserInputEnabled(boolean enabled);

}
