public class AI implements AII {
	EvaluationI ev;
	private int move;
	public AI(EvaluationI ev) {
		this.ev = ev;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see AII#findMove(ConnectFourI, int)
	 */
	
	@Override
	public int findMove(ConnectFourI node, int depth) {
		negamax(node, depth);
		return move;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AII#negamax(ConnectFourI, int)
	 */
	@Override
	public int negamax(ConnectFourI node, int depth) {
		if(depth == 0 || node.isGameOver()){
			return ev.evaluate(node, depth);
		}
		
		int max = -10000;
		for(int col: node.getMoves()){
			node.makeMove(col);			
			int score = -negamax(node, depth - 1);
			node.undoMove();
			if(score > max){
				move = col;
				max = score;
			}			

		}
		
		return max;
	}
}
