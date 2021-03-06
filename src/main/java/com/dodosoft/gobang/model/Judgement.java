package com.dodosoft.gobang.model;

import java.util.logging.Logger;


/**
 * @author Yuhi Ishikura
 */
public final class Judgement implements GobangModel.Listener {

    private static final Logger logger = Logger.getLogger(Judgement.class.getName());
    private static final int[][] DIRECTIONS = new int[][] {
        {1, 1}, {1, 0}, {0, 1}, {1, -1}
    };
    private Go winner;
    private State state;
    private Go current;

    public Judgement() {
        initialize();
    }

    private void initialize() {
        this.winner = null;
        this.state = State.DEFAULT;
        this.current = Go.WHITE;
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
    public void onPreMark(final GobangModel model, final int x, final int y, final Go mark) {
        if (canMark(model, x, y) == false) {
            logger.finest(String.format("Detected illegal action(x=%d, y=%d, mark=%s)", x, y, mark));
            throw new IllegalLocationException();
        }
    }

    @Override
    public void onPostMark(final GobangModel model, final int x, final int y, final Go mark) {
        this.state = State.STARTED;
        if (checkWin(model, x, y, mark)) {
            logger.finest("Game finished.  Winner is " + mark);
            this.winner = mark;
            this.state = State.FINISHED;
        }
        nextTurn();
    }

    public boolean canMark(final GobangModel model, final int x, final int y) {
        if (this.state == State.FINISHED) {
            return false;
        }
        if (x < 0 || x >= model.getWidth()) {
            return false;
        }
        if (y < 0 || y >= model.getHeight()) {
            return false;
        }
        if (model.getMark(x, y) != null) {
            return false;
        }
        return true;
    }

    private void nextTurn() {
        if (this.current == Go.WHITE) {
            this.current = Go.BLACK;
        } else {
            this.current = Go.WHITE;
        }
    }

    @Override
    public void onClear(GobangModel model) {
        initialize();
    }

    private boolean checkWin(final GobangModel model, final int x, final int y, final Go mark) {
        for (int i = 0; i < DIRECTIONS.length; i++) {
            final int[] direction = DIRECTIONS[i];
            int length = 1;
            int xx = x;
            int yy = y;
            while (true) {
                xx += direction[0];
                yy += direction[1];
                final Go g = getMarkSafely(model, xx, yy);
                if (g == mark) {
                    length++;
                } else {
                    break;
                }
            }
            xx = x;
            yy = y;
            while (true) {
                xx -= direction[0];
                yy -= direction[1];
                final Go g = getMarkSafely(model, xx, yy);
                if (g == mark) {
                    length++;
                } else {
                    break;
                }
            }
            if (length >= 5) {
                return true;
            }
        }
        return false;
    }

    private static Go getMarkSafely(GobangModel model, int x, int y) {
        if (x < 0 || x >= model.getWidth()) {
            return null;
        }
        if (y < 0 || y >= model.getHeight()) {
            return null;
        }
        return model.getMark(x, y);
    }

    public static enum State {
        DEFAULT, STARTED, FINISHED
    }

}
