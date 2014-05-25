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

import java.text.MessageFormat;


/**
 * @author Yuhi Ishikura
 */
public class IllegalAiActionException extends RuntimeException {

    private Go mark;

    public IllegalAiActionException(Go mark, String message) {
        super(message);
        this.mark = mark;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format("{0} performed illegal action.  Details: {1}", this.mark, super.getMessage());
    }
}
