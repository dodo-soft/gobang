package com.dodosoft.gobang;

/**
 * 碁盤を表すインターフェースです。
 *
 * @author Yuhi Ishikura
 */
public interface GobangModel {

    /**
     * 碁盤の横幅(マス数)を取得します。
     *
     * @return 碁盤の横幅(マス数)
     */
    int getWidth();

    /**
     * 碁盤の縦幅(マス数)を取得します。
     *
     * @return 碁盤の縦幅(マス数)
     */
    int getHeight();

    /**
     * 与えられた座標に碁を配置します。
     *
     * @param x    x座標(0~{@link #getWidth()}-1)
     * @param y    y座標(0~{@link #getHeight()}-1)
     * @param mark 碁
     */
    void mark(int x, int y, Go mark);

    /**
     * 碁を取得します。
     *
     * @param x x座標(0~{@link #getWidth()}-1)
     * @param y y座標(0~{@link #getHeight()}-1)
     * @return 碁
     */
    Go getMark(int x, int y);

    /**
     * モデルをクリアします。
     */
    void clear();

    /**
     * このモデルを監視するリスナーを追加します。
     *
     * @param listener リスナー
     */
    void addListener(Listener listener);

    /**
     * このモデルを監視するリスナーを削除します。
     *
     * @param listener リスナー
     */
    void removeListener(Listener listener);

    /**
     * {@link GobangModel}を監視するリスナーインターフェースです。
     */
    public static interface Listener {

        /**
         * 碁が配置される前に呼び出されます。
         *
         * @param model モデル
         * @param x     x座標(0~{@link #getWidth()}-1)
         * @param y     y座標(0~{@link #getHeight()}-1)
         * @param mark  碁
         * @throws com.dodosoft.gobang.IllegalLocationException 不正な場所に置こうとした場合。この場合には{@link #onPostMark(GobangModel, int, int, Go)}は呼び出されません。
         */
        void onPreMark(GobangModel model, int x, int y, Go mark);

        /**
         * 碁が配置された時に呼び出されます。
         *
         * @param model モデル
         * @param x     x座標(0~{@link #getWidth()}-1)
         * @param y     y座標(0~{@link #getHeight()}-1)
         * @param mark  碁
         */
        void onPostMark(GobangModel model, int x, int y, Go mark);

        /**
         * モデルがクリアされた時に呼び出されます。
         *
         * @param model モデル
         */
        void onClear(GobangModel model);

    }

}
