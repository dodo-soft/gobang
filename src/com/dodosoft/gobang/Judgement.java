package com.dodosoft.gobang;

/**
 * @author Yuhi Ishikura
 */
public final class Judgement implements GobangModel.Listener {

    private Go winner = null;
    private State state = State.DEFAULT;
    private Go current = Go.WHITE;

    Judgement() {

    }

    public State getState() {
        return state;
    }

    public Go getWinner() {
        return winner;
    }

    public Go getCurrent() {
        return current;
    }

    @Override
    public void onMark(final GobangModel model, final int x, final int y, final Go mark) {
        this.state = State.STARTED;
        if (this.current == Go.WHITE) {
            this.current = Go.BLACK;
        } else {
            this.current = Go.WHITE;
        }
    }

    public static enum State {
        DEFAULT, STARTED, FINISHED
    }

}
