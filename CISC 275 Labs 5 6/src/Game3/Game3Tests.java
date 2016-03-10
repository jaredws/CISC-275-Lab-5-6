package Game3;
import static org.junit.Assert.*;

import java.util.Iterator;

import javax.swing.JMenuItem;

import org.junit.Test;

import OverallGame.OverallGame;
import OverallGame.gameWindow;

/**
 * @author Brendan, David, Danielle, Zhanglong and Huayu
 * @version 0.1
 * @since   2015-11-02
 * Handles the tests for game 3
 */
public class Game3Tests {
	/**
	 * First	Test:	Checks if the method properly adds score
	 * Second	Test:	Checks if the mussels correctly grow per update
	 * Third	Test:	Checks if the score properly updates
	 * Fourth	Test:	Checks if update will add runoff
	 * Fifth	Test:	Checks if update will end the game
	 */
	@Test
	public void testUpdate() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		assertFalse(2	<	testGame3.getMussels().get(0).getStage());
		testGame3.setTime(0.05);
		//First	Test
		testGame3.update();
		assertEquals(10,testGame3.getScore());
		//Second	Test
		testGame3.update();
		assertTrue(2	<	testGame3.getMussels().get(0).getStage());
		//Third	Test
		assertEquals("Score:"+Integer.toString(testGame3.getScore()),testGame3.getTimeAndScore().getText());
		//Fourth	Test
		testGame3.setTickCount(6);
		for (int i	=	0;	i<50;	i++) {
			testGame3.update();
		}
		assertTrue(0 < testGame3.getEnemies().size()); //small chance that this fails due to random numbers being generated
		//Fifth	Test
		assertEquals(false,testGame.getGamesComplete()[2]);
		testGame3.setTime(-6.0);
		testGame3.update();
		assertEquals(true,testGame.getGamesComplete()[2]);
		
	}

	/**
	 * First	Test:	Will click on an undeveloped mussel and show that it
	 * is still there afterwards
	 * Second 	Test:	Will click on a developed mussel and show that it
	 * is harvested
	 * Third	Test:	Will Click on a tile and show that it pulls up a menu
	 * Fourth	Test:	Will Click away from the menu to show that the menu
	 * will go away
	 * Testing for adding a plant via a menu isn't possible with the way the methods are arranged
	 * Several Tests also test whether the timer startting and stopping works correctly
	 */
	@Test
	public void testOnClick() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		testGame.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getMussels().get(0).setStage(50);
		testGame3.getTimer().stop();
		int	musselX	=	testGame3.getMussels().get(0).getXLoc()+30;
		int	musselY	=	testGame3.getMussels().get(0).getYLoc()+30;
		//First		Test
		assertEquals(testGame3.getMussels().get(0).getXLoc(),musselX-30);
		assertEquals(testGame3.getMussels().get(0).getYLoc(),musselY-30);
		testGame3.onClickForTesting(musselY, musselX); testGame3.getTimer().stop();
		assertEquals(testGame3.getMussels().get(0).getXLoc(),musselX-30);
		assertEquals(testGame3.getMussels().get(0).getYLoc(),musselY-30);
		//Second	Test
		testGame3.getMussels().get(0).setStage(100);
		testGame3.onClickForTesting(musselY, musselX); testGame3.getTimer().stop();
		assertFalse(testGame3.getMussels().get(0).getXLoc()==musselX-30);
		assertFalse(testGame3.getMussels().get(0).getYLoc()==musselY-30);
		//Third		Test
		testGame3.onClickForTesting(Game3.yOffset+2*Game3.scalor, Game3.xOffset+2*Game3.scalor);
		assertEquals(true,testGame3.getMenu().isShowing());
		assertEquals(false,testGame3.getTimer().isRunning());
		//Fourth	Test
		testGame3.onClickForTesting(10, Game3.xOffset);
		assertEquals(false,testGame3.getMenu().isVisible());
		assertEquals(true,testGame3.getTimer().isRunning());
		testGame3.getTimer().stop();
		
	}
	
	/**
	 * MenuRegen is too simple of a method to provide complex tests on
	 * First	Test:	Tests to see if the new menu item is different
	 * than the old menu item
	 */
	@Test
	public void testMenuRegen() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		JMenuItem initGrass	=	testGame3.getGrass();
		JMenuItem initMangrove	=	testGame3.getMangrove();
		testGame3.menuRegen();
		assertFalse(initGrass.equals(testGame3.getGrass()));
		assertFalse(initMangrove.equals(testGame3.getMangrove()));
	}

	/**
	 * Test 1: Tests the addition of a plant to a row and column
	 * Test 2: Tests the addition of a plant to a different row and column
	 */
	@Test
	public void testAddPlant() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		testGame3.addPlant(0, 0, "Grass");
		Plant	testPlant	=	new	Plant(0,0,"Grass");
		assertTrue(testGame3.getPlants().get(0).equals(testPlant));
		testGame3.addPlant(0, 2, "Mangrove");
		Plant	testPlant2	=	new	Plant(0,2,"Mangrove");
		assertTrue(testGame3.getPlants().get(1).equals(testPlant2));
	}
	

	/**
	 * First	Test:	Tests the addition of max 4 runoff
	 * Second	Test:	Tests that all runoff have different row locations
	 */
	@Test
	public void testAddRunoff() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		testGame3.addRunoff();
		testGame3.addRunoff();
		testGame3.addRunoff();
		testGame3.addRunoff();
		//First	Test
		assertEquals(4,testGame3.getEnemies().size());
		testGame3.addRunoff();
		assertEquals(4,testGame3.getEnemies().size());
		//Second	Test
		assertTrue(testGame3.getEnemies().get(0).getRow()!=testGame3.getEnemies().get(1).getRow());
		assertTrue(testGame3.getEnemies().get(0).getRow()!=testGame3.getEnemies().get(2).getRow());
		assertTrue(testGame3.getEnemies().get(0).getRow()!=testGame3.getEnemies().get(3).getRow());
		assertTrue(testGame3.getEnemies().get(1).getRow()!=testGame3.getEnemies().get(2).getRow());
		assertTrue(testGame3.getEnemies().get(1).getRow()!=testGame3.getEnemies().get(3).getRow());
		assertTrue(testGame3.getEnemies().get(2).getRow()!=testGame3.getEnemies().get(3).getRow());
	}
	
	/**
	 * First	Test:	Tests that the location has changed
	 * Second	Test:	Tests that the runoff will grow if it reaches a new tile
	 * Third	Test:	Tests that the runoff will battle if next to a plant
	 * Fourth	Test:	Tests that the runoff will go away if it reaches the end
	 */
	@Test
	public void testMoveRunoff() {
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		testGame3.getEnemies().removeAll(testGame3.getEnemies());
		testGame3.addRunoff();
		int initLoc	=	testGame3.getEnemies().get(0).getFront();
		assertEquals(1,testGame3.getEnemies().size());
		testGame3.moveRunoff();
		//First	and	Second	Tests
		assertFalse(testGame3.getEnemies().get(0).getFront() == initLoc);
		assertEquals(2,testGame3.getEnemies().get(0).getLength());
		testGame3.addPlant(testGame3.getEnemies().get(0).getRow(), 5, "Grass");
		testGame3.getEnemies().get(0).setFront(5*Game3.scalor+Game3.xOffset);
		int initHealth	=	testGame3.getEnemies().get(0).getHealth().get(0);
		testGame3.moveRunoff();
		//Third	Test
		assertFalse(initHealth	==	testGame3.getEnemies().get(0).getHealth().get(0));
		//Fourth	Test
		assertEquals(4,testGame3.getMussels().size());
		testGame3.getEnemies().get(0).setFront(Game3.xOffset);
		testGame3.moveRunoff();
		assertEquals(0,testGame3.getEnemies().size());
		assertEquals(3,testGame3.getMussels().size());
		
	}

	/**
	 * First	Test:	Checks to make sure only the front of the runoff is damaged
	 * Second	Test:	Checks for the destruction of runoff after low health
	 * Third	Test:	Checks that the runoff front is shifted after destruction
	 * Fourth 	Test:	Checks for the destruction of a plant after low health
	 * 
	 */
	@Test
	public	void	testBattle()	{
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		testGame3.addPlant(0, 3, "Mangrove");
		testGame3.getEnemies().set(0, new Runoff(0,4*Game3.scalor+Game3.xOffset));
		testGame3.getEnemies().get(0).grow();
		testGame3.getPlants().get(0).setStrength(testGame3.getEnemies().get(0).getHealth().get(1));
		//First		Test
		int health	=	150;
		testGame3.battle(testGame3.getPlants().get(0), testGame3.getEnemies().get(0));
		assertTrue(health	== testGame3.getEnemies().get(0).getHealth().get(0));
		//Second	Test
		assertTrue(testGame3.getEnemies().get(0).hasDied	==	true);
		assertTrue(testGame3.getEnemies().get(0).getLength()	==	1);
		//Third		Test
		assertTrue(testGame3.getEnemies().get(0).getFront() != 4*Game3.scalor+Game3.xOffset);
		//Fourth	Test
		testGame3.getEnemies().get(0).setStrength(testGame3.getPlants().get(0).getHealth());
		testGame3.battle(testGame3.getPlants().get(0), testGame3.getEnemies().get(0));
		assertTrue(testGame3.getPlants().size()	==	0);
		assertTrue(testGame3.getEnemies().get(0).getLength()	==	0);
	}
	
	/**
	 * First	Test:	Create Mussels and add a new one to make sure they don't overlap
	 * 
	 * Second	Test:	Add mussels until no more mussels can be added
	 * 
	 */
	@Test
	public	void	testAddMussel()	{
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		testGame3.addMussel();
		Mussel	testMussel	=	testGame3.getMussels().get(testGame3.getMussels().size()-1);
		Iterator<Mussel> it	=	testGame3.getMussels().iterator();
		while (it.hasNext()) {
			Mussel	current	=	it.next();
			if (it.hasNext() != false) {
				assertFalse(testMussel.equals(current));
			}
		}
		testGame3.getMussels().removeAll(testGame3.getMussels());
		for (int i	=	0;	i<50	;	i++){
			testGame3.addMussel();
		}
		assertFalse(testGame3.getMussels().size() ==50);
	}
	
	/**
	 * First Test:	Make sure initialization has worked
	 * Second Test: Tests adding the money and adding coins to the images
	 * Third Test:	Tests the addition of an additional coin, which changes
	 * 				the format from a list to a stack with a number
	 * Fourth Test:	Tests taking money away and thus changing the format back
	 * 				into the list of coins
	 */
	@Test
	public	void	testAddMoney()	{
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		assertEquals(testGame3.getMoney()/100,testGame3.getCoins().size());
		testGame3.setMoney(900);
		testGame3.addMoney(100);
		assertEquals(1000,testGame3.getMoney());
		assertEquals(10,testGame3.getCoins().size());
		testGame3.addMoney(100);
		assertEquals(1,testGame3.getCoins().size());
		assertEquals(" X11",testGame3.getTotalCoin().getText());
		testGame3.addMoney(-200);
		assertEquals(9,testGame3.getCoins().size());
	}
	
	/**
	 * First Assert Equals are there to test that the values have 
	 * initialized properly and no false positives can be read
	 * 
	 * Second set checks to see if endGame() correctly affected 
	 * the overall game
	 */
	@Test
	public	void	testEndGame()	{
		OverallGame testGame	=	new	OverallGame();
		testGame.setGameWindow(new	gameWindow(testGame));
		Game3		testGame3	=	new	Game3(testGame);
		testGame3.getTimer().stop();
		assertEquals(0,testGame.getOverallScore());
		assertEquals(false,testGame.getGamesComplete()[2]);
		testGame3.setScore(-500);	//Don't want the game to affect the actual high scores
		testGame3.endGame("testHighScores.txt");
		assertEquals(-500,testGame.getOverallScore());
		assertEquals(true,testGame.getGamesComplete()[2]);
		
	}
}