package OverallGame;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import org.junit.Test;


/**
 * @author Brendan, David, Danielle, Zhanglong and Huayu
 * @version 0.1
 * @since   2015-11-02
 * A Class for testing the Overall Game Tests
 */
public class OverallGameTest {
	
	@Test
	public void SerializationTest() throws IOException {
		OverallGame testGame = new OverallGame() ;
		OverallGame.serialize(testGame, "testOutput.ser");
		OverallGame loadedGame = (OverallGame)OverallGame.deserialize("testOutput.ser");
		assertEquals(loadedGame.toString(), testGame.toString());
	}
	
	@Test
	public void initializeHighscoresTest() {
		OverallGame	testGame	=	new OverallGame();
		testGame.setHighscores(testGame.initializeHighscores("testHighScores.txt"));
		String	highScores1	=	testGame.getHighscores();
		testGame.setHighscores(testGame.initializeHighscores("highScores.txt"));
		String	highScores2	=	testGame.getHighscores();
		testGame.setHighscores(testGame.initializeHighscores("testHighScores2.txt"));
		String	highScores3	=	testGame.getHighscores();
		String	highScores4	=	"Brendan:	42000\nDanielle:	3915\nDavid:	666\nHuayu:	350\nZhanglong:	333\n";
		assertEquals(highScores1,highScores2);
		assertFalse(highScores1.equals(highScores3));
		assertEquals(highScores1,highScores4);
	}
	
	@Test
	public void updateHighScoresTest() {
		OverallGame	testGame	=	new	OverallGame();
		testGame.setGameWindow(new gameWindow(testGame));
		testGame.setOverallScore(95);
		testGame.setHighscores(testGame.initializeHighscores("testHighScoreUpdate.txt"));
		testGame.updateHighScores("testHighScoreUpdate.txt");
		String originalScores	=	"Brendan:	100\nDavid:	99\nDanielle:	98\nZhanglong:	97\nHuayu:	96\n";
		assertTrue(testGame.getHighscores().equals(originalScores));
		testGame.setOverallScore(1000);
		//USER MUST TYPE "Brendan" At This point for the test to work
		testGame.updateHighScores("testHighScoreUpdate.txt");
		String newScores	=	"Brendan:	1000\nBrendan:	100\nDavid:	99\nDanielle:	98\nZhanglong:	97\n";
		assertTrue(testGame.getHighscores().equals(originalScores));
		assertFalse(testGame.getHighscores().equals(newScores));
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
	

}