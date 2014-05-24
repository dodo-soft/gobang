package com.dodosoft.gobang;

/**
 * @author Yuhi Ishikura
 */
public final class Judgement implements GobangModel.Listener {

    private Mark winner = null;
    private State state = State.DEFAULT;

    Judgement() {

    }

    public State getState() {
        return state;
    }

    public Mark getWinner() {
        return winner;
    }

    @Override
    public void onMark(final GobangModel model, final int x, final int y, final Mark mark) {
        // winnerとstateを更新
    }

    public static enum State {
        DEFAULT, STARTED, FINISHED
    }

}
