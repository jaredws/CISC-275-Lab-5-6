package Game3;
import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	
	static OverallGame o;
	static OverallGame o2;
	static Game3 g;
	static Image img;
	static Game3 g2;
	static JLabel jl;
	static ActionListener l;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		jl = new JLabel();
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		o.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g	= new Game3(o);
		o2 = new OverallGame();
		o2.setGameWindow(new gameWindow(o));
		o2.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g2 = new Game3(10.0,3,55,4,new ArrayList<Plant>(),new ArrayList<Runoff>(),new ArrayList<Mussel>(),new ArrayList<Tile>(),true,false,o,new JPanel(),new JPanel(),new JFrame(),new JLabel(),(long)100,new Timer(5,null),new JPopupMenu(),new JProgressBar(),new ArrayList<JLabel>(),jl,jl,img,new JMenuItem(),new JMenuItem(),null,null);
		try {
			img = ImageIO.read(new File("images/coin.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		l = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		};
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		o = null;
	}
	
	/**
	 * First	Test:	Checks if the method properly adds score
	 * Second	Test:	Checks if the mussels correctly grow per update
	 * Third	Test:	Checks if the score properly updates
	 * Fourth	Test:	Checks if update will add runoff
	 * Fifth	Test:	Checks if update will end the game
	 */
	@Test
	public void testUpdate() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		o.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g	= new Game3(o);
		g.getTimer().stop();
		assertFalse(2	<	g.getMussels().get(0).getStage());
		g.setTime(0.05);
		//First	Test
		g.update();
		assertEquals(10,g.getScore());
		//Second	Test
		g.update();
		assertTrue(2	<	g.getMussels().get(0).getStage());
		//Third	Test
		assertEquals("Score:"+Integer.toString(g.getScore()),g.getTimeAndScore().getText());
		//Fourth	Test
		g.setTickCount(6);
		for (int i	=	0;	i<50;	i++) {
			g.update();
		}
		assertTrue(0 < g.getEnemies().size()); //small chance that this fails due to random numbers being generated
		//Fifth	Test
		assertEquals(false,o.getGamesComplete()[2]);
		g.setTime(-6.0);
		g.update();
		assertEquals(true,o.getGamesComplete()[2]);
		
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
	/*@Test
	public void testOnClick() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		o.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g	= new Game3(o);
		g.getMussels().get(0).setStage(50);
		g.getTimer().stop();
		int	musselX	=	g.getMussels().get(0).getXLoc()+30;
		int	musselY	=	g.getMussels().get(0).getYLoc()+30;
		//First		Test
		assertEquals(g.getMussels().get(0).getXLoc(),musselX-30);
		assertEquals(g.getMussels().get(0).getYLoc(),musselY-30);
		g.onClickForTesting(musselY, musselX); g.getTimer().stop();
		assertEquals(g.getMussels().get(0).getXLoc(),musselX-30);
		assertEquals(g.getMussels().get(0).getYLoc(),musselY-30);
		//Second	Test
		g.getMussels().get(0).setStage(100);
		g.onClickForTesting(musselY, musselX); g.getTimer().stop();
		assertFalse(g.getMussels().get(0).getXLoc()==musselX-30);
		assertFalse(g.getMussels().get(0).getYLoc()==musselY-30);
		//Third		Test
		g.onClickForTesting(Game3.yOffset+2*Game3.scalor, Game3.xOffset+2*Game3.scalor);
		assertTrue(g.getMenu().isShowing());
		assertFalse(g.getTimer().isRunning());
		//Fourth	Test
		g.onClickForTesting(10, Game3.xOffset);
		assertFalse(g.getMenu().isVisible());
		assertTrue(g.getTimer().isRunning());
		g.getTimer().stop();
		
	}*/
	
	/**
	 * MenuRegen is too simple of a method to provide complex tests on
	 * First	Test:	Tests to see if the new menu item is different
	 * than the old menu item
	 */
	@Test
	public void testMenuRegen() {
		g.getTimer().stop();
		JMenuItem initGrass	=	g.getGrass();
		JMenuItem initMangrove	=	g.getMangrove();
		g.menuRegen();
		assertFalse(initGrass.equals(g.getGrass()));
		assertFalse(initMangrove.equals(g.getMangrove()));
	}

	/**
	 * Test 1: Tests the addition of a plant to a row and column
	 * Test 2: Tests the addition of a plant to a different row and column
	 */
	@Test
	public void testAddPlant() {
		g.getTimer().stop();
		g.addPlant(0, 0, "Grass");
		Plant	testPlant	=	new	Plant(0,0,"Grass");
		assertTrue(g.getPlants().get(0).equals(testPlant));
		g.addPlant(0, 2, "Mangrove");
		Plant	testPlant2	=	new	Plant(0,2,"Mangrove");
		assertTrue(g.getPlants().get(1).equals(testPlant2));
	}
	

	/**
	 * First	Test:	Tests the addition of max 4 runoff
	 * Second	Test:	Tests that all runoff have different row locations
	 */
	@Test
	public void testAddRunoff() {
		g.getTimer().stop();
		g.addRunoff();
		g.addRunoff();
		g.addRunoff();
		g.addRunoff();
		//First	Test
		assertEquals(4,g.getEnemies().size());
		g.addRunoff();
		assertEquals(4,g.getEnemies().size());
		//Second	Test
		assertTrue(g.getEnemies().get(0).getRow()!=g.getEnemies().get(1).getRow());
		assertTrue(g.getEnemies().get(0).getRow()!=g.getEnemies().get(2).getRow());
		assertTrue(g.getEnemies().get(0).getRow()!=g.getEnemies().get(3).getRow());
		assertTrue(g.getEnemies().get(1).getRow()!=g.getEnemies().get(2).getRow());
		assertTrue(g.getEnemies().get(1).getRow()!=g.getEnemies().get(3).getRow());
		assertTrue(g.getEnemies().get(2).getRow()!=g.getEnemies().get(3).getRow());
	}
	
	/**
	 * First	Test:	Tests that the location has changed
	 * Second	Test:	Tests that the runoff will grow if it reaches a new tile
	 * Third	Test:	Tests that the runoff will battle if next to a plant
	 * Fourth	Test:	Tests that the runoff will go away if it reaches the end
	 */
	@Test
	public void testMoveRunoff() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		o.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g	= new Game3(o);
		g.getTimer().stop();
		g.getEnemies().removeAll(g.getEnemies());
		g.addRunoff();
		int initLoc	=	g.getEnemies().get(0).getFront();
		assertEquals(1,g.getEnemies().size());
		g.moveRunoff();
		//First	and	Second	Tests
		assertFalse(g.getEnemies().get(0).getFront() == initLoc);
		assertEquals(2,g.getEnemies().get(0).getLength());
		g.addPlant(g.getEnemies().get(0).getRow(), 5, "Grass");
		g.getEnemies().get(0).setFront(5*Game3.scalor+Game3.xOffset);
		int initHealth	=	g.getEnemies().get(0).getHealth().get(0);
		g.moveRunoff();
		//Third	Test
		assertFalse(initHealth	==	g.getEnemies().get(0).getHealth().get(0));
		//Fourth	Test
		assertEquals(4,g.getMussels().size());
		g.getEnemies().get(0).setFront(Game3.xOffset);
		g.moveRunoff();
		assertEquals(0,g.getEnemies().size());
		assertEquals(3,g.getMussels().size());
		
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
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		o.getGameWindow().getFrame().setBounds(0, 0, OverallGame.frameWidth, OverallGame.frameHeight);
		g	= new Game3(o);
		g.getTimer().stop();
		g.addPlant(0, 3, "Mangrove");
		g.getEnemies().set(0, new Runoff(0,4*Game3.scalor+Game3.xOffset));
		g.getEnemies().get(0).grow();
		g.getPlants().get(0).setStrength(g.getEnemies().get(0).getHealth().get(1));
		//First		Test
		int health	=	150;
		g.battle(g.getPlants().get(0), g.getEnemies().get(0));
		assertTrue(health	== g.getEnemies().get(0).getHealth().get(0));
		//Second	Test
		assertTrue(g.getEnemies().get(0).hasDied	==	true);
		assertTrue(g.getEnemies().get(0).getLength()	==	1);
		//Third		Test
		assertTrue(g.getEnemies().get(0).getFront() != 4*Game3.scalor+Game3.xOffset);
		//Fourth	Test
		g.getEnemies().get(0).setStrength(g.getPlants().get(0).getHealth());
		g.battle(g.getPlants().get(0), g.getEnemies().get(0));
		assertTrue(g.getPlants().size()	==	0);
		assertTrue(g.getEnemies().get(0).getLength()	==	0);
	}
	
	/**
	 * First	Test:	Create Mussels and add a new one to make sure they don't overlap
	 * 
	 * Second	Test:	Add mussels until no more mussels can be added
	 * 
	 */
	@Test
	public	void	testAddMussel()	{
		g.getTimer().stop();
		g.addMussel();
		Mussel	testMussel	=	g.getMussels().get(g.getMussels().size()-1);
		Iterator<Mussel> it	=	g.getMussels().iterator();
		while (it.hasNext()) {
			Mussel	current	=	it.next();
			if (it.hasNext() != false) {
				assertFalse(testMussel.equals(current));
			}
		}
		g.getMussels().removeAll(g.getMussels());
		for (int i	=	0;	i<50	;	i++){
			g.addMussel();
		}
		assertFalse(g.getMussels().size() ==50);
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
		g.getTimer().stop();
		assertEquals(g.getMoney()/100,g.getCoins().size());
		g.setMoney(900);
		g.addMoney(100);
		assertEquals(1000,g.getMoney());
		assertEquals(10,g.getCoins().size());
		g.addMoney(100);
		assertEquals(1,g.getCoins().size());
		assertEquals(" X11",g.getTotalCoin().getText());
		g.addMoney(-200);
		assertEquals(9,g.getCoins().size());
		g.setTotalCoin(new JLabel("Coins"));
		g.setMoney(1000);
		g.addMoney(1000);
		assertTrue(g.getTotalCoin().getText().equals(" X20"));
		g.setMoney(5000);
		g.setTotalCoin(null);
		g.addMoney(1000);
		ImageIcon imi = new ImageIcon("images/coin.png");
		assertTrue(g.getCoins().get(g.getCoins().size()-1).getIcon().toString().equals(imi.toString()));
		
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
		g.getTimer().stop();
		assertEquals(0,o.getOverallScore());
		assertEquals(false,o.getGamesComplete()[2]);
		g.setScore(-500);	//Don't want the game to affect the actual high scores
		g.endGame("testHighScores.txt");
		assertEquals(-500,o.getOverallScore());
		assertEquals(true,o.getGamesComplete()[2]);
	}
	
	@Test
	public void setGameRunningTest() {
		g.setGameRunning(true);
		assertTrue("Game running should be true",g.isGameRunning());
	}
	
	@Test
	public void setGameOverTest() {
		g.setGameOver(false);
		assertFalse("Game over should be false",g.isGameOver());
	}
	
	@Test
	public void setStartTime() {
		g.setStartTime(5);
		assertTrue("Start time should be 5",g.getStartTime()==5);
	}
	
	@Test
	public void setBigGameTest() {
		o.setOverallScore(50);
		o2.setOverallScore(1000);
		g.setBigGame(o2);
		assertEquals("Overall should be 1000 if setBigGame works",g.getBigGame().getOverallScore(),1000);
	}
	
	@Test
	public void setPlantsTest() {
		ArrayList<Plant> p = new ArrayList<Plant>();
		p.add(new Plant(3, 4, "test"));
		g.setPlants(p);
		assertEquals("Row for first element of plants arraylist should be 3",g.getPlants().get(0).getRow(),3);
		assertEquals("Column for first element of plants arraylist should be 4",g.getPlants().get(0).getCol(),4);
	}
	
	@Test
	public void setEnemiesTest() {
		ArrayList<Runoff> r = new ArrayList<Runoff>();
		r.add(new Runoff(5, 2));
		r.add(new Runoff(9, 8));
		g.setEnemies(r);
		assertEquals("Row for second element of runoff arraylist shoult be 9",g.getEnemies().get(1).getRow(),9);
		assertEquals("Front for second element of runoff arraylist shoult be 8",g.getEnemies().get(1).getFront(),8);
	}
	
	@Test
	public void setMusselsTest() {
		ArrayList<Mussel> m = new ArrayList<Mussel>();
		m.add(new Mussel(2, 6));
		m.add(new Mussel(7, 22));
		g.setMussels(m);
		assertEquals("xloc for second element of mussel arraylist shoult be 7",g.getMussels().get(1).getXLoc(),7);
		assertEquals("yloc for second element of mussel arraylist shoult be 22",g.getMussels().get(1).getYLoc(),22);
	}
	
	@Test
	public void setTilesTest() {
		ArrayList<Tile> t = new ArrayList<Tile>();
		t.add(new Tile(32, 21));
		t.add(new Tile(3, 52));
		g.setTiles(t);
		assertEquals("Row for second element of tile arraylist shoult be 3",g.getTiles().get(1).getRow(),3);
		assertEquals("Front for second element of tile arraylist shoult be 52",g.getTiles().get(1).getCol(),52);
	}
	
	@Test
	public void setTimeAndScoreTest() {
		JLabel j = new JLabel("Score: 33, Time: 20");
		g.setTimeAndScore(j);
		assertTrue("Score should be 33",g.getTimeAndScore().getText().contains("Score: 33"));
		assertTrue("Time should be 20",g.getTimeAndScore().getText().contains("Time: 20"));
	}
	
	@Test
	public void setGamePanelTest() {
		JPanel j2 = new JPanel();
		j2.add(new JLabel("Some Label"));
		g.setGamePanel(j2);
		assertEquals("GamePanel should have 1 component",g.getGamePanel().getComponentCount(),1);
	}
	
	@Test
	public void setBigGamePanelTest() {
		JPanel j2 = new JPanel();
		j2.add(new JLabel("Big Game Label"));
		j2.add(new JLabel("Some Label"));
		g.setBigGamePanel(j2);
		assertEquals("BigGamePanel should have 2 components",g.getBigGamePanel().getComponentCount(),2);
	}
	
	@Test
	public void setGameFrameTest() {
		JPanel j2 = new JPanel();
		j2.add(new JLabel("Some Game Labe"));
		j2.add(new JLabel("Some Label"));
		JFrame frame = new JFrame();
		frame.setContentPane(j2);
		g.setGameFrame(frame);
		assertEquals("GameFrame should have a content pane with 2 components",g.getGameFrame().getContentPane().getComponentCount(),2);
	}
	
	@Test
	public void setBackgroundTest() {
		Game3.setBackground(img);
		assertTrue("Image should be coin.png",Game3.getBackground().equals(img));
	}
	
	@Test
	public void setMenuTest() {
		JPopupMenu jpm = new JPopupMenu();
		jpm.setSize(50, 100);
		g.setMenu(jpm);
		assertEquals("PopupMenu should have a width of 50",(int)g.getMenu().getSize().getWidth(),(int)50);
		assertEquals("PopupMenu should have a height of 100",(int)g.getMenu().getSize().getHeight(),(int)100);
	}
	
	@Test
	public void setTimeBarTest() {
		JProgressBar jpb = new JProgressBar();
		jpb.setString("Time Progress");
		g.setTimeBar(jpb);
		assertTrue("TimeBar should contain the string 'Time Progress'",g.getTimeBar().getString().equals("Time Progress"));
	}
	
	@Test
	public void setCoinsTest() {
		ArrayList<JLabel> jl = new ArrayList<JLabel>();
		jl.add(new JLabel("Coin 1"));
		jl.add(new JLabel("Coin 2"));
		g.setCoins(jl);
		assertTrue("Coins arraylist should contain a 'Coin 2'",g.getCoins().get(1).getText().equals("Coin 2"));
	}
	
	@Test
	public void setTotalCoinTest() {
		JLabel jl = new JLabel("10 Coins");
		g.setTotalCoin(jl);
		assertTrue("TotalCoins arraylist should contain '10 Coins'",g.getTotalCoin().getText().equals("10 Coins"));
	}
	
	@Test
	public void setPipesTest() {
		JLabel jl = new JLabel("Some Pipes");
		g.setPipes(jl);
		assertTrue("Pipes should contain 'Some Pipes'",g.getPipes().getText().equals("Some Pipes"));
	}
	
	@Test
	public void getSerialversionuidTest() {
		assertEquals("SerialVersionUID should be 300",Game3.getSerialversionuid(),300);
	}
	
	@Test
	public void setGrassListenTest() {
		g.setGrassListen(l);
		assertEquals("HashCode of GrassListen and l should be equal",g.getGrassListen().hashCode(),l.hashCode());
	}
	
	@Test
	public void setMangroveListenTest() {
		g.setMangroveListen(l);
		assertEquals("HashCode of MangroveListen and l should be equal",g.getMangroveListen().hashCode(),l.hashCode());
	}
	
	@Test
	public void toStringTest() {
		assertTrue("Printing toString should contain the following: ",g.toString().contains("[Plants [ Row: 0, Col: 0, Strength: 3, Health: 80, Type: Grass]")); 
	}
	
	@Test
	public void onClickTest1() {
		//Initialize
		ArrayList<Mussel> m = new ArrayList<Mussel>();
		m.add(new Mussel(5, 3));m.add(new Mussel(2, 6));
		g.setMussels(m);
		g.getTimer().stop();
		
		//If xLoc < xOffset
		MouseEvent e = new MouseEvent(new JLabel(), 4, 45, 3, g.getMussels().get(0).getXLoc()+30, g.getMussels().get(0).getYLoc()+30, 6, false);
		g.onClick(e);
	}
	
	/*@Test
	public void onClickTest2() {
		//Initialize
		ArrayList<Mussel> m = new ArrayList<Mussel>();
		m.add(new Mussel(5, 3));m.add(new Mussel(2, 6));
		g.setMussels(m);
		
		//Else if clause
		MouseEvent e2 = new MouseEvent(new JLabel(), 4, 45, 3,Game3.xOffset + 7*Game3.scalor - 10, Game3.yOffset + 4*Game3.scalor - 10, 6, false);
		g.setMoney(1000);
		g.onClick(e2);
	}*/
	
	@Test
	public void onClickTest3() {
		//Initialize
		ArrayList<Mussel> m = new ArrayList<Mussel>();
		m.add(new Mussel(5, 3));m.add(new Mussel(2, 6));
		g.setMussels(m);
		
		//Else
		MouseEvent e3 = new MouseEvent(new JLabel(), 4, 45, 3,Game3.xOffset+10, g.getMussels().get(0).getYLoc()+30, 6, false);
		g.onClick(e3);
		assertFalse("Menu should not be visible",g.getMenu().isVisible());
	}
	
}