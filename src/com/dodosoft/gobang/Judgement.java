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
		if(checkWin(model,x,y)){
			this.winner = this.current;
			this.state = State.FINISHED;
		}
		if (this.current == Go.WHITE) {
			this.current = Go.BLACK;
		} else {
			this.current = Go.WHITE;
		}

	}

//	private int checkRightLeft(final GobangModel model, final int x, final int y, int count){
//		if(count<5){
//			if(model.getMark(x+1, y) == this.current)
//				checkRightLeft(model, x+1, y, count+1);
//		}
//		
//	}
	
	private boolean checkWin(final GobangModel model, final int x, final int y){
		final int Width = model.getWidth();
		final int Height = model.getWidth();
		int count=0;
		boolean result = true;
		
		// check 横
		for(count=0; count<5; count++){
			if(x+count<Width){
				if(model.getMark(x+count, y) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;

		result = true;
		for(count=0; count<5; count++){
			if(x-count >= 0){
				if(model.getMark(x-count, y) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;

		//check 縦
		result = true;
		for(count=0; count<5; count++){
			if(y+count<Height){
				if(model.getMark(x, y+count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;
		
		result = true;
		for(count=0; count<5; count++){
			if(y-count>=0){
				if(model.getMark(x, y-count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;
		
		//check 斜め
		result = true;
		for(count=0; count<5; count++){
			if(x+count<Width && y+count<Height){
				if(model.getMark(x+count, y+count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;
		
		result = true;
		for(count=0; count<5; count++){
			if(x-count>=0 && y+count<Height){
				if(model.getMark(x-count, y+count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;

		result = true;
		for(count=0; count<5; count++){
			if(x-count>=0 && y-count>=0){
				if(model.getMark(x-count, y-count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;
		
		result = true;
		for(count=0; count<5; count++){
			if(x+count<Width && y-count>=0){
				if(model.getMark(x+count, y-count) != this.current){
					result = false;
					break;
				}
			}
			else
				result = false;
		}
		if(result)
			return result;

		return result;
	}

	public static enum State {
		DEFAULT, STARTED, FINISHED
	}

}
