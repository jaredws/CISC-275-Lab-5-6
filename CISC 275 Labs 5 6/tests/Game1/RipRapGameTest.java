package Game1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Game2.Animal;
import Game2.CrabCatcherGame;
import Game3.Game3;
import OverallGame.OverallGame;
import OverallGame.gameWindow;

public class RipRapGameTest {
	
	RipRapGame r;
	OverallGame o;
	Crab c;
	JumpingBar j;
	Sun s;
	boolean[] b = {true,false};
	gameWindow w;
	
	@Before
	public void setUp() throws Exception {
		o = new OverallGame();
		w = new gameWindow(o);
		o.setGameWindow(w);
		r = new RipRapGame(1,o,w.getFrame());
		c = new Crab(1,2,3);
		j = new JumpingBar(2,1,r);
		s = new Sun(43);
	}

	@After
	public void tearDown() throws Exception {
		o = null;
		r = null;
		c = null;
		w = null;
		s = null;
		j = null;
	}

	@Test
	public void addScoreTest() {
		r.score = 10;
		r.addScore(5);
		assertEquals("Score should be 15",r.score,15);
	}
	
	@Test
	public void setCrabTest() {
		r.setCrab(c);
		assertEquals("Crab x position should be 1",(int)r.getCrab().getPosition().getX(),1);
		assertEquals("Crab y position should be 2",(int)r.getCrab().getPosition().getY(),2);
		assertEquals("Crab size should be 3",r.getCrab().getSize(),3);
	}
	
	@Test
	public void setJumpingTest() {
		r.setJumpingBar(j);
		assertEquals("JumpingBar stop1 should be 2",r.getJumpingBar().stop1,2);
		assertEquals("JumpingBar stop2 should be 1",r.getJumpingBar().stop2,1);
	}
	
	@Test
	public void setSunTest() {
		r.setSun(s);
		assertEquals("Sun size should be 43",r.getSun().getSize(),43);
	}
	
	@Test
	public void getScoreTest() {
		r.score = 32;
		assertEquals("Score should be 32",r.getScore(),32);
	}
	
	@Test
	public void getTimeTest() {
		r.currtime = 29;
		assertEquals("Time should be 29",r.getTime(),29);
	}
	
	@Test
	public void setBigGameTest() {
		ArrayList<Animal> aa = new ArrayList<Animal>();
		CrabCatcherGame c1 = new CrabCatcherGame(15.3, aa, 20, 3, 41, null, 21, false, o, w.getFrame());
		RipRapGame r1 = new RipRapGame(10, o, w.getFrame());
		Game3 g1 = new Game3(o);
		OverallGame o2 = new OverallGame(100, b, 1, "",10.2,c1,r1,g1,w);
		r.setBigGame(o2);
		assertTrue("Time in idle should be 10.2",r.getBigGame().getTimeInIdle()==10.2);
	}

}
