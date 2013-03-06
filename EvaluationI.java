public interface EvaluationI {
    /**
     * Returns the score of the current board from the point
     * of view of the player to move. 
     * @param node
     * @return the score as evaluated by the player who's
     * turn it is to move.
     */
	
    public abstract int evaluate(ConnectFourI node, int depth);
    
    public abstract int[] nInARow(ConnectFourI node, int n);
    

}