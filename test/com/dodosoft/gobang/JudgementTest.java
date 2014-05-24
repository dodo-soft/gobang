package com.dodosoft.gobang;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class JudgementTest {

    @Test
    public void testOnMark_right() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(i, 0, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_right2() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        model.mark(5, 5, Go.WHITE);
        model.mark(6, 5, Go.WHITE);
        model.mark(4, 5, Go.WHITE);
        model.mark(7, 5, Go.WHITE);
        model.mark(3, 5, Go.WHITE);
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_left() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(5 - i - 1, 0, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_down() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(0, i, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_up() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(0, 5 - i - 1, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_leftUp() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(5 - i - 1, 5 - i - 1, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_rightUp() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(i, 5 - i - 1, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_leftDown() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(5 - i - 1, i, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

    @Test
    public void testOnMark_rightDown() throws Exception {
        final Judgement sut = new Judgement();
        final GobangModel model = new ArrayGobangModel(19, 19);
        model.addListener(sut);

        assertThat(sut.getWinner(), is((Go)null));
        for (int i = 0; i < 5; i++) {
            model.mark(i, i, Go.WHITE);
        }
        assertThat(sut.getWinner(), is(Go.WHITE));
    }

}