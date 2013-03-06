public class Evaluation implements EvaluationI {
	Player pl;
	private int totalSum_O = 0;
	private int totalSum_X = 0;

	public Evaluation(Player player) {
		this.pl = player;
	}

	@Override
	public int evaluate(ConnectFourI node, int depth) {
		Player turnToMove = node.getToMove();
		
		int score = 0;
		int twos_0 = this.nInARow(node, 2)[0];
		int twos_1 = this.nInARow(node, 2)[1];
		int threes_0 = this.nInARow(node, 3)[0];
		int threes_1 = this.nInARow(node, 3)[1];
		
		if(node.getWinner() != null){
			return -(1000 + depth);
		}
		
		//X's turn to move, and arr[0] = x; arr[1] = o
		if((turnToMove.getName().equals("x") && pl.getName().equals("o")) || 
				(turnToMove.getName().equals("o") && pl.getName().equals("x"))){
			score = 4 * (twos_0 - twos_1) + 32 * (threes_0 - threes_1); 
		} 
		
		//X's turn to move, and arr[0] = o; arr[1] = x
		else if((turnToMove.getName().equals("x") && pl.getName().equals("x")) || 
				turnToMove.getName().equals("o") && pl.getName().equals("o")){
			score = 4 * (twos_1 - twos_0) + 32 * (threes_1 - threes_0); 
		}
					
		return score;
	}

	@Override
	//arr[0] is the person who last moved; arr[1] is the person whose turn it is
	public int[] nInARow(ConnectFourI node, int n) {
		int[] rowArr = new int[2];
		Player who;
		totalSum_O = 0;
		totalSum_X = 0;
		for(int i = 0; i < node.BOARD_WIDTH; i++){
			for(int j = node.BOARD_HEIGHT - 1; j > 0; j--){
				if(node.board[j][i] == null){
					break;
				} else {
					who = node.board[j][i];			
					if(who.getName().equals("o")){
						totalSum_O += findNbd(j, i, n, node, who);
					} else if(who.getName().equals("x")) {
						totalSum_X += findNbd(j, i, n, node, who);
					}
				}	
			}
		}
		//We divide totalSums by 2 to account for double counting
		//AI is playing "o", so AI is "x", so AI goes first, so rowArr[0] = x's in-a-rows; arr[1] = o's in-a-rows
		if(pl.getName().equals("o")){
			rowArr[0] = totalSum_X / 2;
			rowArr[1] = totalSum_O / 2;
		}
		//AI is playing "x", so AI is "o", so Player goes first, so arr[0] = o's in-a-rows; arr[1] = x's in-a-rows
		else if (pl.getName().equals("x")){
			rowArr[0] = totalSum_O / 2;
			rowArr[1] = totalSum_X / 2;
		}
		return rowArr;
	}
	
    //Checks if the player at (row, col) is the player in question
    private boolean checkPlayer(Player who, ConnectFourI node, int row, int col){
    	if(node.getSquare(row, col) == null){
    		return false;
    	}
    	return who.getName().equals(node.getSquare(row, col).getName());
    }
    
    private int checkDir(Player who, ConnectFourI node, int[] valBuf, int[] valArr, int row, int col, int n, int i){
		if (checkPlayer(who, node, row, col)){
			valBuf[i]++;
			if(valBuf[i] == n - 1){
				valArr[i]++;
			}
		}
		return valArr[i];
    }

	//Finds, as in ConnectFour, a neighborhood for which there are "in a rows"	
	/*
	valArr[0] = vertUp;
	valArr[1] =  vertDn;
	valArr[2] =  horzLt;
	valArr[3] =  horzRt;
	valArr[4] =  diagPosUp;
	valArr[5] =  diagPosDn;
	valArr[6] =  diagNegUp;
	valArr[7] =  diagNegDn;
	
	and similarly for valBuf
	*/
	private int findNbd(int row, int col, int n, ConnectFourI node, Player who){
		int rowUp;
		int rowDn;
		int colLt;
		int colRt;
		
		int[] valBuf = {0, 0, 0, 0, 0, 0, 0, 0};
		int[] valArr = {0, 0, 0, 0, 0, 0, 0, 0};

		
		for(int i = 1; i < n; i++){			
			rowUp = row - i;
			rowDn = row + i;
			colLt = col - i;
			colRt = col + i;
			
			//Check verticals				
			valArr[0] = checkDir(who, node, valBuf, valArr, rowUp, col, n, 0);
			valArr[1] = checkDir(who, node, valBuf, valArr, rowDn, col, n, 1);
			
			//Check horizontals
			valArr[2] = checkDir(who, node, valBuf, valArr, row, colLt, n, 2);
			valArr[3] = checkDir(who, node, valBuf, valArr, row, colRt, n, 3);
			
			//Check Diagonals
			valArr[4] = checkDir(who, node, valBuf, valArr, rowUp, colRt, n, 4);
			valArr[5] = checkDir(who, node, valBuf, valArr, rowDn, colLt, n, 5);
			valArr[6] = checkDir(who, node, valBuf, valArr, rowUp, colLt, n, 6);
			valArr[7] = checkDir(who, node, valBuf, valArr, rowDn, colRt, n, 7);
		}

		int sum = 0;
		for(int i = 0; i < valArr.length; i++){
			if(valBuf[i] == n - 1){
				sum += valArr[i];
			}
		}
		return sum;
	}
}