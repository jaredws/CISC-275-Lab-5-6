package OverallGame;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game1.RipRapGame;
import Game2.Animal;
import Game2.CrabCatcherGame;
import Game3.Game3;


/**
 * @author Brendan, David, Danielle, Zhanglong and Huayu
 * @version 0.1
 * @since   2015-11-02
 * A Class for testing the Overall Game Tests
 */
public class OverallGameTest {
	
	OverallGame o;
	boolean[] b = {true,false};
	
	@Before
	public void setUp() {
		o = new OverallGame();
	}
	
	@After
	public void tearDown() {
		o = null;
	}
	
	@Test
	public void SerializationTest() throws IOException {
		OverallGame.serialize(o, "testOutput.ser");
		OverallGame loadedGame = (OverallGame)OverallGame.deserialize("testOutput.ser");
		assertEquals(loadedGame.toString(), o.toString());
	}
	
	@Test
	public void initializeHighscoresTest() {
		o.setHighscores(o.initializeHighscores("testHighScores.txt"));
		String	highScores1	=	o.getHighscores();
		o.setHighscores(o.initializeHighscores("highScores.txt"));
		String	highScores2	=	o.getHighscores();
		o.setHighscores(o.initializeHighscores("testHighScores2.txt"));
		String	highScores3	=	o.getHighscores();
		String	highScores4	=	"Brendan:	42000\nDanielle:	3915\nDavid:	666\nHuayu:	350\nZhanglong:	333\n";
		assertEquals(highScores1,highScores2);
		assertFalse(highScores1.equals(highScores3));
		assertEquals(highScores1,highScores4);
	}
	
	@Test
	public void updateHighScoresTest() {
		o.setGameWindow(new gameWindow(o));
		o.setOverallScore(95);
		o.setHighscores(o.initializeHighscores("testHighScoreUpdate.txt"));
		o.updateHighScores("testHighScoreUpdate.txt");
		String originalScores	=	"Brendan:	100\nDavid:	99\nDanielle:	98\nZhanglong:	97\nHuayu:	96\n";
		assertTrue(o.getHighscores().equals(originalScores));
		o.setOverallScore(1000);
		//USER MUST TYPE "Brendan" At This point for the test to work
		o.updateHighScores("testHighScoreUpdate.txt");
		String newScores	=	"Brendan:	1000\nBrendan:	100\nDavid:	99\nDanielle:	98\nZhanglong:	97";
		assertFalse(o.getHighscores().equals(newScores));
		File resetScores	=	new File("testHighScoreUpdate.txt");
		FileOutputStream	resetScoresOP	=	null;
		try {
			resetScoresOP	=	new FileOutputStream(resetScores, false);
		}	catch	(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			resetScoresOP.write(originalScores.getBytes());
			resetScoresOP.close();
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void setGamesCompleteTest() {
		o.setGamesComplete(b);
		assertTrue("Games complete should return [true,false]",o.getGamesComplete().equals(b));
	}
	
	@Test
	public void setGamesRunningTest() {
		o.setGamesRunning(3);
		assertEquals("Games running should be 3",o.getGamesRunning(),3);
	}
	
	@Test
	public void setTimeInIdleTest() {
		o.setTimeInIdle(50.0);
		assertEquals("Time in idle should be 50.0",(int)o.getTimeInIdle(),(int)50.0);
	}
	
	@Test
	public void setGame1() {
		gameWindow g = new gameWindow(o);
		o.setGameWindow(g);
		RipRapGame r = new RipRapGame(10, o, g.getFrame());
		o.setGame1(r);
		assertTrue("True if getGame1 is an instanceof RipRapGame",o.getGame1() instanceof RipRapGame);
	}
	
	@Test
	public void setGame2() {
		gameWindow g = new gameWindow(o);
		o.setGameWindow(g);
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, g.getFrame());
		o.setGame2(c);
		assertTrue("True if getGame2 is an instanceof CrabCatcherGame",o.getGame2() instanceof CrabCatcherGame);
	}
	
	@Test
	public void setGame3() {
		gameWindow g = new gameWindow(o);
		o.setGameWindow(g);
		Game3 g3 = new Game3(o);
		o.setGame3(g3);
		assertTrue("True if getGame3 is an instanceof Game3",o.getGame3() instanceof Game3);
	}
	
	@Test
	public void toStringTest1() {
		gameWindow w1 = new gameWindow(o);
		o.setGameWindow(w1);
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c1 = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, w1.getFrame());
		RipRapGame r1 = new RipRapGame(10, o, w1.getFrame());
		Game3 g1 = new Game3(o);
		OverallGame o2 = new OverallGame(100, b, 1, "",10.2,c1,r1,g1,w1);
		o2.setGamesRunning(1);
		PrintStream originalOut = System.out;
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
		System.out.print(o2.toString());
		assertTrue("Output contains RipRapGame",os.toString().contains("Current Game: [RipRapGame"));
		System.setOut(originalOut);
	}
	
	@Test
	public void toStringTest2() {
		gameWindow w1 = new gameWindow(o);
		o.setGameWindow(w1);
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c1 = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, w1.getFrame());
		RipRapGame r1 = new RipRapGame(10, o, w1.getFrame());
		Game3 g1 = new Game3(o);
		OverallGame o2 = new OverallGame(100, b, 1, "",10.2,c1,r1,g1,w1);
		o2.setGamesRunning(2);
		PrintStream originalOut = System.out;
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
		System.out.print(o2.toString());
		assertTrue("Output contains CrabCatcherGame",os.toString().contains("Current Game: CrabCatcherGame"));
		System.setOut(originalOut);
	}
	
	@Test
	public void toStringTest3() {
		gameWindow w1 = new gameWindow(o);
		o.setGameWindow(w1);
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c1 = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, w1.getFrame());
		RipRapGame r1 = new RipRapGame(10, o, w1.getFrame());
		Game3 g1 = new Game3(o);
		OverallGame o2 = new OverallGame(100, b, 1, "",10.2,c1,r1,g1,w1);
		o2.setGamesRunning(3);
		PrintStream originalOut = System.out;
		OutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
	    System.setOut(ps);
		System.out.print(o2.toString());
		assertTrue("Output contains Game3",os.toString().contains("Current Game: Game3"));
		System.setOut(originalOut);
	}
	
	@Test
	public void getSerialversionuidTest() {
		assertEquals("Serialversionuid should be 0",OverallGame.getSerialversionuid(),0);
	}
	

}