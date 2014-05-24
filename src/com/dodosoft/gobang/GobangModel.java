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
    void mark(int x, int y, Mark mark);

    /**
     * 碁を取得します。
     *
     * @param x x座標(0~{@link #getWidth()}-1)
     * @param y y座標(0~{@link #getHeight()}-1)
     * @return 碁
     */
    Mark getMark(int x, int y);

}
