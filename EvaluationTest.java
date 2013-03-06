import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class EvaluationTest {
	ConnectFourI connect;
	ConnectFourI connect2;
	ConnectFourI connectSimple;

	EvaluationI eval;
	EvaluationI eval2;
	EvaluationI evalSimple;

	@Before
	public void setUp() throws Exception {
	    connect = new ConnectFour();
	    eval = new Evaluation(connect.x);

		connect.makeMove(3); //x
		connect.makeMove(4); //o
		connect.makeMove(3); //x
	}

	@After
	public void tearDown() throws Exception {
	    connect = null;
	    eval = null;
	}

	@Test
	public void testEvaluateSimple(){
	    connectSimple = new ConnectFour();
	    evalSimple = new Evaluation(connect.x);
	    
	    connectSimple.makeMove(5);
	    connectSimple.makeMove(1);
	    connectSimple.makeMove(5);
	    
		assertEquals(evalSimple.evaluate(connectSimple, 5), -4);
	}
	
	@Test
	public void testEvaluateTwo() {
		assertEquals(eval.evaluate(connect, 0), -4);
		connect.makeMove(0);
		assertEquals(eval.evaluate(connect, 0), 4);
	}
	
	@Test
	public void testEvaluateThree() {
		connect.makeMove(0); //o
		connect.makeMove(3); //x

		assertEquals(eval.evaluate(connect, 0), -40);
	}
	@Test
	public void testEvaluateFour() {
		connect.makeMove(0);
		connect.makeMove(3);
		connect.makeMove(2);
		connect.makeMove(3);
		connect.printBoard();
		
		assertEquals((eval.nInARow(connect, 4))[1], 1);
		assertEquals(eval.evaluate(connect, 3), -1003);
	}
	
	@Test
	public void testnInARow(){
		connect.makeMove(1);
		connect.makeMove(2);
		connect.makeMove(6);
		connect.makeMove(2);
		connect.makeMove(1);
		connect.makeMove(1);
		connect.printBoard();
		assertEquals((eval.nInARow(connect, 2))[0],1);
		assertEquals((eval.nInARow(connect, 2))[1],7);
	}
	
	@Test
	public void test2InARow(){
	    connect2 = new ConnectFour();
		//Zero 2 in a rows
	    
	    connect2.makeMove(0); //x
	    connect2.makeMove(1); //o
	    connect2.makeMove(2); //x
	    connect2.makeMove(3); //o
	    
		assertEquals((eval.nInARow(connect2, 2))[0],0);
		assertEquals((eval.nInARow(connect2, 2))[1],0);
		
		//One 2 in a row
	    connect2.makeMove(0);//xx
	    
		//Two 2 in a rows; adjacent and creating "doubles"...check "o"s
	    connect2.makeMove(3);
	    connect2.makeMove(2);
	    connect2.makeMove(4);
	    connect2.makeMove(6);
	    connect2.makeMove(4);
	    
	}
	
	@Test
	public void test3InARow(){
		//AI MAKES FIRST MOVE. arr[0] = PLAYER X; arr[1] = PLAYER O
	    connect2 = new ConnectFour();
	    eval2 = new Evaluation(connect.o);

		//Zero 3 in a rows
	    connect2.makeMove(0);
		assertEquals((eval2.nInARow(connect2, 3))[0],0);
		assertEquals((eval2.nInARow(connect2, 3))[1],0);

		//One 3 in a row (x)
	    connect2.makeMove(1);
	    connect2.makeMove(0);
	    connect2.makeMove(1);
	    connect2.makeMove(0);
		assertEquals((eval2.nInARow(connect2, 3))[0],1); //x
		assertEquals((eval2.nInARow(connect2, 3))[1],0); //o
		
		
		//Two 3 in a rows; adjacent and creating "doubles"...check "o"s
	    connect2.makeMove(1);
	    connect2.makeMove(6);
	    connect2.makeMove(2);
	    connect2.makeMove(6);
	    connect2.makeMove(2);
	    connect2.makeMove(5);
	    connect2.makeMove(2);
	    
		assertEquals((eval2.nInARow(connect2, 3))[0],1); //x
		assertEquals((eval2.nInARow(connect2, 3))[1],2); //o

	    
	    //Diagonal 3 in a rows
	    connect2.makeMove(5);
	    connect2.makeMove(3);
//	    connect2.printBoard();
//	    System.out.println();

		assertEquals((eval2.nInARow(connect2, 3))[0],1); //x
		assertEquals((eval2.nInARow(connect2, 3))[1],4); //o
		
		//3-in-a-row within a 4-in-a-row
		connect2.makeMove(0);
	  
		assertEquals((eval2.nInARow(connect2, 3))[0],2); //x
		assertEquals((eval2.nInARow(connect2, 3))[1],4); //o

		//2-in-a-rows within 4-in-a-row
		assertEquals((eval2.nInARow(connect2, 2))[0],9); //x
		assertEquals((eval2.nInARow(connect2, 2))[1],13); //o
		
		connect2.undoMove();
		connect2.printBoard();

		assertEquals(connect2.getToMove().getName(), "x");
		assertEquals(eval2.evaluate(connect2, 0), 4 * (8 - 13) + 32 * (1 - 4));
	}
	
}

