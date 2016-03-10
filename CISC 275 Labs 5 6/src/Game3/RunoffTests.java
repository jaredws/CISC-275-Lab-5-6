package Game3;
import static org.junit.Assert.*;

import org.junit.Test;

import OverallGame.OverallGame;
import OverallGame.gameWindow;

public class RunoffTests {

	

	/**
	 * Runoff are equal only if they have the same location
	 */
	@Test
	public void testGrowAndRemoveFront() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		Runoff testRunoff1 = new Runoff(1,1000) ;
		assertEquals(1,testRunoff1.getLength());
		testRunoff1.grow();
		assertEquals(2,testRunoff1.getLength());
		testRunoff1.grow();
		testRunoff1.grow();
		testRunoff1.grow();
		testRunoff1.grow();
		testRunoff1.grow();
		assertEquals(5, testRunoff1.getLength());
		assertEquals(1000,testRunoff1.getFront());
		testRunoff1.removeFront();
		assertEquals(4,testRunoff1.getLength());
		assertEquals(true,testRunoff1.hasDied);
		assertEquals(1000+Game3.scalor,testRunoff1.getFront());
		testRunoff1.grow();
		assertEquals(4, testRunoff1.getLength());
	}

}