import java.util.Stack;


public class ConnectFour extends ConnectFourI {
	private Stack<Integer> stackCol = new Stack<Integer>();
	private Stack<Integer> stackRow = new Stack<Integer>();
	
	public ConnectFour(){ 
		x = new Player("x");
		o = new Player("o");
		toMove = x;
	}
	
    @Override
    public int[] getMoves() {
    	int k = 0;
    	int[] tempBoard = new int[BOARD_WIDTH];
    	for(int i = 0; i < BOARD_WIDTH; i++){
    		for(int j = 0; j < BOARD_HEIGHT; j++){
    			if(board[j][i] == null){
    				//Add the column
    				tempBoard[k] = i;
    				k++;
    				break;
    			} 
    		}
    		
    	}
    	
    	int[] subBoard = new int[k];
    	for(int x = 0; x < tempBoard.length; x++){
    		if(tempBoard[x] != 0 || x == 0){
    			subBoard[x] = tempBoard[x];
    		}
    	}
        return subBoard;
    }

    @Override
    public boolean isGameOver() {
    	if(this.getWinner() == null){
    		return false;
    	}
        return (this.getMoves() == null || this.getWinner().getName().equals("x") || this.getWinner().getName().equals("o"));
    }

    @Override
    public Player getWinner() {
    	if(stackCol.isEmpty() && stackRow.isEmpty()){
    		return null;
    	}
    	
    	int lastCol = stackCol.peek();
    	int lastRow = stackRow.peek();
    	Player who = getSquare(lastRow, lastCol);
    	
    	if(checkNbd(who, lastRow, lastCol)){
    		return who;
    	} else {
    		return null;
    	}
    }
    
    //Checks a neighborhood of the last played position to determine if there is a winner
    private boolean checkNbd(Player who, int lastRow, int lastCol){
    	int horzCnt = 0;
		int vertCnt = 0;
		int diagCnt1 = 0;
		int diagCnt2 = 0;
		
    	for(int i = 0; i < 4; i++){
    		//Horizontals (Across columns), Verticals (up and down rows), Diagonals(positive and negative "slopes")
    		for(int j = 0; j < 4; j++){
	    		horzCnt = checkDir(who, horzCnt, lastRow, lastCol - i + j);	    		
	    		vertCnt = checkDir(who, vertCnt, lastRow - i + j, lastCol);	    	
	    		diagCnt1 = checkDir(who, diagCnt1, lastRow + i - j, lastCol - i + j);
	    		diagCnt2 = checkDir(who, diagCnt2, lastRow + i - j, lastCol + i - j);	    		
    		}
    		
        	if(horzCnt >= 4 || vertCnt >= 4 || diagCnt1 >= 4|| diagCnt2 >= 4){
        		return true;
        	} else { 		
	        	horzCnt = 0;
	        	vertCnt = 0;
	        	diagCnt1 = 0;
	        	diagCnt2 = 0;
        	}
    	}
    	return false;
    }
    
    private int checkDir(Player who, int cnt, int row, int col){
    	if(checkPlayer(who, row, col)){
    		cnt++;
    	}
    	return cnt;
    }
 
    //Checks if the player at (row, col) is "who"
    private boolean checkPlayer(Player who, int row, int col){
    	if(getSquare(row, col) == null){
    		return false;
    	}
    	return who.getName() == getSquare(row, col).getName();
    }
    
    @Override
    public boolean makeMove(int col) {
    	if((col > BOARD_WIDTH - 1 || col < 0) || this.isGameOver()){
    		return false;
    	}

    	//Decreasing, since row-major order is the opposite order of insertion
    	for(int i = BOARD_HEIGHT - 1; i >= 0; i--){
    		if(board[i][col] == null){
    			if(toMove == x){
    				board[i][col] = new Player("x"); 
    				toMove = o;
    			} else if (toMove == o){
    				board[i][col] = new Player("o");
    				toMove = x;
    			}
				stackCol.push(col);
				stackRow.push(i);
    			return true;

    		}
    	}
        return false;
    }

    @Override
    public void undoMove() {    	
    	if(!stackRow.isEmpty() && !stackCol.isEmpty()){
	        int row = stackRow.pop();
	        int col = stackCol.pop();

	        //Resetting the movecounters
	        if(this.getSquare(row, col).getName().equals("x")){
	        	toMove = x;
	        } else if(this.getSquare(row, col).getName().equals("o")){
	        	toMove = o;
	        }
	        
	        board[row][col] = null;
    	}
    }

    @Override
    public Player getSquare(int row, int col) {
    	if(row > BOARD_HEIGHT - 1 || row < 0 || col > BOARD_WIDTH - 1 || col < 0){
    		return null;
    	}
        return board[row][col];
    }

    @Override
    public Player getToMove() {
    	if(this.isGameOver()){
    		return null;
    	}
    	
    	if(toMove == x){
    		return x;
    	} else {
    		return o;
    	}
    }
}
