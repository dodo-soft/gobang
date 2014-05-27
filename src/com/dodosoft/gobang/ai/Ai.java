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
import com.dodosoft.gobang.model.IllegalLocationException;
import com.dodosoft.gobang.model.Judgement;

import java.text.MessageFormat;


/**
 * AIを表すクラスです。
 *
 * @author Yuhi Ishikura
 */
public abstract class Ai implements GobangModel.Listener {

    private final GobangModel.Listener listener;
    private GobangModel model;
    private Judgement judgement;
    private Go mark;
    private boolean marked;
    private Ui ui;

    /**
     * {@link Ai}オブジェクトを構築します。
     */
    public Ai() {
        this.listener = new GobangModel.Listener() {
            @Override
            public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {
                try {
                    Ai.this.onPreMark(model, x, y, mark);
                } catch (IllegalLocationException ex) {
                    throw new IllegalAiActionException(Ai.this.mark, "Tried to deny other player's action.");
                }
            }

            @Override
            public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
                Ai.this.onPostMark(model, x, y, mark);
                if (mark == getMark()) {
                    marked = true;
                }
            }

            @Override
            public void onClear(final GobangModel model) {
                Ai.this.onClear(model);
            }
        };
    }

    /**
     * AIを開始します。<p>
     * フレームワーク外から呼び出さないでください。
     *
     * @param model     モデル
     * @param judgement 審判
     * @param mark      AIの碁
     */
    final void start(GobangModel model, Judgement judgement, Go mark, Ui ui) {
        if (this.model != null) {
            throw new IllegalStateException("Already running.");
        }
        this.model = model;
        this.judgement = judgement;
        this.mark = mark;
        this.ui = ui;
        model.addListener(this.listener);
    }

    /**
     * AIを終了します。<p>
     * フレームワーク外から呼び出さないでください。
     */
    final void stop() {
        if (this.model == null) {
            throw new IllegalStateException("Not started.");
        }
        this.model.removeListener(this.listener);
        this.model = null;
        this.judgement = null;
    }

    /**
     * AIが置くべき碁を取得します。
     *
     * @return AIが置くべき碁
     */
    protected final Go getMark() {
        return this.mark;
    }

    /**
     * 碁盤の横幅を取得します。
     *
     * @return 碁盤の横幅
     * @see GobangModel#getWidth()
     */
    protected final int getWidth() {
        return this.model.getWidth();
    }

    /**
     * 碁盤の縦幅を取得します。
     *
     * @return 碁盤の縦幅
     * @see GobangModel#getHeight()
     */
    protected final int getHeight() {
        return this.model.getHeight();
    }

    /**
     * 与えられた座標の碁を取得します。
     *
     * @param x x座標
     * @param y y座標
     * @return 碁
     * @see GobangModel#getMark(int, int)
     */
    protected final Go getMark(int x, int y) {
        return this.model.getMark(x, y);
    }

    /**
     * 与えられた座標に碁を配置します。
     *
     * @param x x座標
     * @param y y座標
     * @see GobangModel#mark(int, int, Go)
     */
    protected final void mark(int x, int y) {
        if (this.marked) {
            throw new IllegalAiActionException(this.mark, "Cannot mark twice.");
        }
        if (canMark(x, y) == false) {
            throw new IllegalAiActionException(this.mark, MessageFormat.format("Cannot mark the specified location: ({0}, {1})", x, y));
        }
        this.model.mark(x, y, this.mark);
    }

    /**
     * 与えられた座標に碁を配置可能かどうか調べます。
     *
     * @param x x座標
     * @param y y座標
     * @return 配置可能かどうか
     */
    protected final boolean canMark(int x, int y) {
        return this.judgement.canMark(this.model, x, y);
    }

    /**
     * 全てのセルに対しアクションを実行します。
     *
     * @param action アクション
     */
    protected final void forEachCells(CellAction action) {
        forEachMarks(action, false);
    }

    /**
     * 全ての碁が配置されたセルに対しアクションを実行します。
     *
     * @param action アクション
     */
    protected final void forEachMarks(CellAction action) {
        forEachMarks(action, true);
    }

    /**
     * 碁が配置されていない全てのセルに対しアクションを実行します。
     *
     * @param action アクション
     */
    protected final void forEachEmptyCells(CellAction action) {
        l:
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (canMark(x, y) == false) {
                    continue;
                }
                final Go mark = getMark(x, y);
                if (action.execute(x, y, mark)) {
                    break l;
                }
            }
        }
    }

    /**
     * 碁盤へのユーザによる入力の有効/無効を設定します。
     *
     * @param enabled 碁盤へのユーザによる入力を有効にするのであればtrue、そうでなければfalse
     */
    protected final void setUserInputEnabled(boolean enabled) {
        this.ui.setUserInputEnabled(enabled);
    }

    /**
     * 碁盤へのユーザによる入力が有効であるか調べます。
     *
     * @return 碁盤へのユーザによる入力が有効かどうか
     */
    protected final boolean isUserInputEnabled() {
        return this.ui.isUserInputEnabled();
    }

    private final void forEachMarks(CellAction action, boolean ignoreEmpty) {
        l:
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                final Go mark = getMark(x, y);
                if (ignoreEmpty && mark == null) {
                    continue;
                }
                if (action.execute(x, y, mark)) {
                    break l;
                }
            }
        }
    }

    /**
     * 碁が配置される直前に呼び出されます。
     *
     * @param model モデル
     * @param x     x座標(0~{@link #getWidth()}-1)
     * @param y     y座標(0~{@link #getHeight()}-1)
     * @param mark  碁
     */
    @Override
    public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {

    }

    /**
     * 碁が配置された直後に呼び出されます。
     *
     * @param model モデル
     * @param x     x座標(0~{@link #getWidth()}-1)
     * @param y     y座標(0~{@link #getHeight()}-1)
     * @param mark  碁
     */
    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {

    }

    /**
     * 碁盤がクリアされた時に呼び出されます。
     *
     * @param model モデル
     */
    @Override
    public void onClear(final GobangModel model) {

    }

    /**
     * AIのターンを実行します。<p>
     * フレームワーク外から呼び出さないでください。
     */
    final void execute() {
        onYourTurn();
        if (this.marked == false) {
            throw new IllegalAiActionException(this.mark, "Not marked.");
        }
        this.marked = false;
    }

    /**
     * AIのターンを実行します。<p>
     * このクラス以外から呼び出さないでください。
     *
     * @throws IllegalAiActionException AIが不正なアクションを試みた場合
     */
    protected abstract void onYourTurn();

    /**
     * セルに対するアクションを表すインターフェースです。
     */
    @FunctionalInterface
    protected static interface CellAction {

        /**
         * アクションを実行します。
         *
         * @param x    x座標
         * @param y    y座標
         * @param mark セルに配置された碁。配置されていない場合はnull
         * @return アクションが完了したらtrue、次のセルを続行するならばfalse
         */
        boolean execute(int x, int y, Go mark);

    }

}
