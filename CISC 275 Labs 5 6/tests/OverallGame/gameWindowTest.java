package OverallGame;

import static org.junit.Assert.*;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import Game1.RipRapGame;
import Game2.Animal;
import Game2.CrabCatcherGame;
import Game3.Game3;

public class gameWindowTest {

	static OverallGame o;
	static gameWindow g;
	boolean[] b = {true,false};
	static Image img1;
	static Image img2;
	static Image img3;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		o = new OverallGame();
		g = new gameWindow(o);
		o.setGameWindow(g);
		img1 = ImageIO.read(new File("images/Game1Button.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		img2 = ImageIO.read(new File("images/Game2Button.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		img3 = ImageIO.read(new File("images/Game3Button.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		o = null;
		g = null;
	}

	@Test
	public void setBigGameTest() {
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c1 = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, g.getFrame());
		RipRapGame r1 = new RipRapGame(10, o, g.getFrame());
		Game3 g1 = new Game3(o);
		OverallGame o2 = new OverallGame(100, b, 1, "",22.2,c1,r1,g1,g);
		g.setBigGame(o2);
		assertTrue("Time in idle should be 22.2",g.getBigGame().getTimeInIdle()==22.2);
	}
	
	@Test
	public void setGame1Button() {
		g.setGame1Button(img1);
		assertTrue("Game1Button should be equal to img1",g.getGame1Button().equals(img1));
	}
	
	@Test
	public void setGame2Button() {
		g.setGame2Button(img2);
		assertTrue("Game2Button should be equal to img2",g.getGame2Button().equals(img2));
	}
	
	@Test
	public void setGame3Button() {
		g.setGame3Button(img3);
		assertTrue("Game3Button should be equal to img3",g.getGame3Button().equals(img3));
	}
	
	@Test
	public void setCurrentScore() {
		JLabel j = new JLabel("Current Score: 33");
		g.setCurrentScore(j);
		assertTrue("Current Score should be 33",g.getCurrentScore().getText().contains("33"));
	}
	
	@Test
	public void getSerialversionuidTest() {
		assertEquals("Serialversionuid should be 1",gameWindow.getSerialversionuid(),1);
	}
	
	@Test
	public void setViewHighScores() {
		JButton j = new JButton("Test: 1500");
		g.setViewHighScores(j);
		assertTrue("ViewHighScores should have a value 'Test: 1500'",g.getViewHighScores().getText().equals("Test: 1500"));
	}
	
	@Test
	public void setFrameTest() {
		JFrame jf = new JFrame();
		jf.add(new JLabel("test"));
		jf.setBounds(3, 4, 32, 21);
		g.setFrame(jf);
		assertEquals("X position of frame is 3",g.getFrame().getX(),3);
		
	}
	
}
