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

/**
 * @author Yuhi Ishikura
 */
public class Go {

    public static final Go WHITE = new Go('○');
    public static final Go BLACK = new Go('●');
    private char go;

    Go(char go) {
        this.go = go;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Go go1 = (Go)o;

        if (go != go1.go) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int)go;
    }

    @Override
    public String toString() {
        return String.valueOf(this.go);
    }
}
