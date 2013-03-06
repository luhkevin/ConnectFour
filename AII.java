public interface AII {

    /**
     * Returns the best move given the current board, employing
     * negamax. Negamax should select the move that maximizes
     * the negation of the value of the position from the
     * opponents point of view after the move has been played.
     * This is equivalent to minimizing the opponent's evaluation
     * of the move being played.
     * @param node - game node
     * @param depth - depth to search
     * @return
     */
    public abstract int findMove(ConnectFourI node, int depth);

    /**
     * Returns the score of the best move given the current board.
     * Negamax should select the move that maximizes
     * the negation of the value of the position from the
     * opponents point of view after the move has been played.
     * This is equivalent to minimizing the opponent's evaluation
     * of the move being played. If the depth is zero, then
     * the evaluation function is simply called.
     * @param node - game node
     * @param depth - depth to search
     * @return
     */
    public abstract int negamax(ConnectFourI node, int depth);

}