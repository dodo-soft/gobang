package com.dodosoft.gobang;

/**
 * @author Yuhi Ishikura
 */
public final class Judgement implements GobangModel.Listener {

    private Go winner = null;
    private State state = State.DEFAULT;

    Judgement() {

    }

    public State getState() {
        return state;
    }

    public Go getWinner() {
        return winner;
    }

    @Override
    public void onMark(final GobangModel model, final int x, final int y, final Go mark) {
        // winnerとstateを更新
    }

    public static enum State {
        DEFAULT, STARTED, FINISHED
    }

}
