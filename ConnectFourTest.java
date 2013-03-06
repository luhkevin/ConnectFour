import static org.junit.Assert.*;
import java.util.Stack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectFourTest {

	ConnectFourI connect = new ConnectFour();
	ConnectFourI connect2 = new ConnectFour();
	ConnectFourI connectUndo = new ConnectFour();
	ConnectFourI connect25 = new ConnectFour();
	ConnectFourI connect3 = new ConnectFour();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 3; i++) {
			for (int j = i; j < 4; j++) {
				if (i == 2 && j == 2) {
					connect.makeMove(4);
				}
				connect.makeMove(j);
			}
		}
		for (int i = 0; i < 6; i++) {
			connect.makeMove(6);
		}
		System.out.println();
//		connect.printBoard();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testIsGameOver() {
		connect.makeMove(3);
		assertEquals(connect.getWinner().getName(), "x");
		assertTrue(connect.isGameOver());
		connect.undoMove();
	}

	@Test
	public void testGetWinner() {
		Stack<Integer> stackMoves = new Stack<Integer>();
		for (int i : connect.getMoves())
			stackMoves.push(i);
		for (int i = 0; i <= 5; i++) {
			assertTrue(stackMoves.contains(i));
		}
		assertFalse(stackMoves.contains(6));
	}

	@Test
	public void testGetSquare() {
		assertEquals(connect.getSquare(0, 6).getName(), "o");
	}
	
	//My Tests
	@Test
	public void testSimpleGameOver(){
		connect2 = new ConnectFour();
		connect2.makeMove(5);
		connect2.makeMove(2);
		connect2.makeMove(4);
		assertFalse(connect2.isGameOver());
		connect2.makeMove(3);
		assertFalse(connect2.isGameOver());	
		
		try{
			assertEquals(connect2.getWinner(), null);
		} catch(Exception e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testMakeUndoMove(){
		//make two moves
		connectUndo.makeMove(0);
		connectUndo.makeMove(0);

		//undo both
		connectUndo.undoMove();
		connectUndo.undoMove();
		assertEquals(connectUndo.toMove.getName(), "x");
		
				
		//undo but nothing happens (because there are no more moves left in the stack)
		connectUndo.undoMove();
		
		//Create winning board. Try to move, but can't because board is won
		for(int i = 0; i < connectUndo.BOARD_HEIGHT; i++){
			for(int j = 0; j < connectUndo.BOARD_WIDTH; j++){
				connectUndo.makeMove(j);
			}
			connectUndo.printBoard();

		}
		

		//x o x
		connectUndo.makeMove(0);
		connectUndo.makeMove(1);
		connectUndo.makeMove(2);

		
		//undo multiple times and check to see whose turn it is
		connectUndo.undoMove();	
		connectUndo.undoMove();
		connectUndo.undoMove();
		connectUndo.undoMove();
		assertEquals(connectUndo.getToMove().getName(), "x");
		
		//undo on an empty board
		connect2 = new ConnectFour();
		connect2.makeMove(0);
		connect2.undoMove();
		connect2.undoMove();
		assertEquals(connect2.getToMove().getName(), "x");
	}
	
	@Test
	public void testWonUndo(){ 
		//undo on a won board
		connect2 = new ConnectFour();
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(0);
		
		assertTrue(connect2.isGameOver());

		connect2.undoMove();
		assertEquals(connect2.getToMove().getName(), "x");

		connect2.undoMove();	
		assertEquals(connect2.getToMove().getName(), "o");
	}
	
	@Test
	public void testGetMoves() throws Exception {
		connect2 = new ConnectFour();
		//All columns valid		
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(3);
		connect2.makeMove(4);
		connect2.makeMove(5);
		connect2.makeMove(6);
		
		assertEquals(connect2.getMoves()[0], 0);
		assertEquals(connect2.getMoves()[1], 1);
		assertEquals(connect2.getMoves()[2], 2);
		assertEquals(connect2.getMoves()[3], 3);
		assertEquals(connect2.getMoves()[4], 4);
		assertEquals(connect2.getMoves()[5], 5);
		assertEquals(connect2.getMoves()[6], 6);


		//Middle columns (6, 5, 4, 3, 2) valid		
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(0);
		connect2.makeMove(0);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(1);
		connect2.makeMove(1);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.printBoard();

		assertEquals(connect2.getMoves()[0], 2);
		assertEquals(connect2.getMoves()[1], 3);
		assertEquals(connect2.getMoves()[2], 4);
		assertEquals(connect2.getMoves()[3], 5);
		assertEquals(connect2.getMoves()[4], 6);
		
		try{
			assertEquals(connect2.getMoves()[5], null);
		} catch(Exception e){
			assertTrue(true);
		}
		
		//Only (2, 3, 4) columns valid
		connect2.makeMove(6);
		connect2.makeMove(5);
		connect2.makeMove(6);
		connect2.makeMove(5);
		connect2.makeMove(5);
		connect2.makeMove(6);
		connect2.makeMove(5);
		connect2.makeMove(6);
		connect2.makeMove(5);
		connect2.makeMove(6);

		assertEquals(connect2.getMoves()[0], 2);
		assertEquals(connect2.getMoves()[1], 3);
		assertEquals(connect2.getMoves()[2], 4);
		
		try{
			assertEquals(connect2.getMoves()[3], null);
		} catch(Exception e){
			assertTrue(true);
		}

	}
	
	@Test
	public void testGetWinner2() throws Exception {
		/*Player x wins; horz
				"5555555" 
				"4444444" 
				"3333333" 
				"2222222" 
				"ooo1111" 
				"xxxx111");
		*/
		connect2.makeMove(0);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(2);
		connect2.makeMove(3);
		connect2.printBoard();
	
		assertEquals(connect2.getWinner().getName(), "x");
		assertFalse(connect2.getWinner().getName().equals("o"));


		/*Player o wins; vert
				"5555555" +
				"4444444" +
				"333333o" +
				"222222o" +
				"111111o" +
				"xxoxxxo");
		*/
		connect25.makeMove(0);
		connect25.makeMove(6);
		connect25.makeMove(1);
		connect25.makeMove(2);
		connect25.makeMove(3);
		connect25.makeMove(6);
		connect25.makeMove(4);
		connect25.makeMove(6);
		connect25.makeMove(5);
		connect25.makeMove(6);
		connect25.printBoard();
		assertEquals(connect25.getWinner().getName(), "o");
		
		connect2 = new ConnectFour();
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(2);
		connect2.makeMove(3);
		connect2.makeMove(2);
		connect2.makeMove(3);
		connect2.makeMove(4);
		connect2.makeMove(3);
		connect2.makeMove(3);

		//Player x wins; diagonal
		
		assertEquals(connect2.getWinner().getName(), "x");
		
		connect2 = new ConnectFour();
		//No player wins
		connect2.makeMove(0);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(2);

		try {
			assertEquals(connect2.getWinner().getName(), null);
		} catch(Exception e){
			assertTrue(true);
		}
	}
	
	//Winning piece is in the middle of the horizontal
	@Test
	public void testMiddleWinner() throws Exception {	
		connect2 = new ConnectFour();
		connect2.makeMove(6);
		connect2.makeMove(0);
		connect2.makeMove(6);
		connect2.makeMove(6);
		connect2.makeMove(5);
		connect2.makeMove(1);
		connect2.makeMove(4);
		connect2.makeMove(3);
		connect2.makeMove(5);
		connect2.makeMove(2);
		connect2.printBoard();
		assertTrue(connect2.isGameOver());
		assertEquals(connect2.getWinner().getName(), "o");
	}
	
	@Test
	public void testComplicatedNoWin() throws Exception{
		connect2 = new ConnectFour();
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(0);
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(4);
		connect2.makeMove(5);
		connect2.makeMove(6);
		connect2.makeMove(3);
		connect2.makeMove(4);
		connect2.makeMove(5);
		connect2.makeMove(4);
		connect2.makeMove(5);
		connect2.makeMove(5);
		connect2.makeMove(4);
		connect2.makeMove(6);
		connect2.makeMove(2);
		connect2.printBoard();
		assertFalse(connect2.isGameOver());
				
	}
	
	//Another almost win with diagonals
	@Test
	public void testDiagAlmost() throws Exception {
		connect2 = new ConnectFour();
		connect2.makeMove(0);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(3);
		connect2.makeMove(1);
		connect2.makeMove(2);
		connect2.makeMove(2);
		connect2.makeMove(1);
		connect2.makeMove(4);
		connect2.makeMove(3);
		connect2.makeMove(6);
		connect2.makeMove(4);
		connect2.printBoard();
		try {
			assertEquals(connect2.getWinner().getName(), null);
		} catch(Exception e){
			assertTrue(true);
		}
	}
	@Test
	public void testMiddleDiagonal() {
		connect2 = new ConnectFour();
		connect2.makeMove(5);
		connect2.makeMove(5);
		connect2.makeMove(4);
		connect2.makeMove(6);
		connect2.makeMove(3);
		connect2.makeMove(2);
		connect2.makeMove(5);
		connect2.makeMove(5);
		connect2.makeMove(4);
		connect2.makeMove(4);
		connect2.makeMove(6);
		connect2.makeMove(3);
		
		assertTrue(connect2.getWinner().getName().equals("o"));
//		connect2.printBoard();





	}
	@Test
	public void testMakeMove() throws Exception{
		//vertically: "xoxo" on col 0;
		connect3.makeMove(0);
		connect3.makeMove(0);
		connect3.makeMove(0);
		connect3.makeMove(0);
		
		//horizontally: "xxoxoxo"
		connect3.makeMove(1);
		connect3.makeMove(2);
		connect3.makeMove(3);
		connect3.makeMove(4);
		connect3.makeMove(5);
		connect3.makeMove(6);
		
		//One more "x" at position (4,3)
		connect3.makeMove(3);
		
		assertEquals(connect3.getSquare(4, 3).getName(), "x");
		assertEquals(connect3.getSquare(5, 0).getName(), "x");
		assertEquals(connect3.getSquare(5, 3).getName(), "x");
		assertEquals(connect3.getSquare(3, 0).getName(), "x");

	}
}
