package Game1;

import static org.junit.Assert.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import Game2.Animal;
import Game2.CrabCatcherGame;
import Game3.Game3;
import OverallGame.OverallGame;
import OverallGame.gameWindow;

public class RipRapGameTest {
	
	static RipRapGame r;
	static OverallGame o;
	static Crab c;
	static JumpingBar j;
	static Sun s;
	boolean[] b = {true,false};
	static gameWindow w;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		o = new OverallGame();
		w = new gameWindow(o);
		o.setGameWindow(w);
		r = new RipRapGame(1,o,w.getFrame());
		c = new Crab(1,2,3);
		j = new JumpingBar(2,1,r);
		s = new Sun(43);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
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
	
	@Test
	public void initGameTest() {
		r.panel = new JPanel();
		r.panel.setBounds(10, 13, 45, 64);
		r.initGame();
		assertEquals("Objects contains a Sun with size 200",r.objects.get(r.objects.size()-1).getSize(),200);
	}
	
	@Test
	public void toStringTest() {
		r.getBigGame().setOverallScore(250);
		assertTrue("toString contains 'Overall Score: 250'",r.toString().contains("Overall Score: 250"));
	}
	
	@Test
	public void firstRunPanelTest() {
		r.panel = new JPanel();
		r.panel.setBounds(10, 13, 45, 64);
		r.score = 25;
		r.firstRunPanel();
		assertTrue("TS label should have a score of 25",r.TS.getText().equals("Score:25"));
	}
	
	@Test
	public void updatePanelTest() {
		r.panel = new JPanel();
		r.panel.setBounds(10, 13, 45, 64);
		r.score = 35;
		r.updatePanel();
		assertTrue("TS label should have a score of 35",r.TS.getText().equals("Score:35"));
	}
	
	@Test
	public void runTest() {
		r.panel = new JPanel();
		r.panel.setBounds(10, 13, 45, 64);
		r.run();
		assertTrue("The timer should be running",r.timer.isRunning());
	}
	
	@Test
	public void updateTimeTest() {
		long sysTime = System.currentTimeMillis();
		r.starttime = sysTime+1000;
		r.time = 10;
		r.updateTime();
		assertEquals("currTime should be 11",r.currtime,11);
	}
	
	@Test
	public void endGameTest() {
		r.bigpan = new JPanel();
		r.bigpan.setBounds(10, 13, 45, 64);
		r.endGame();
		assertEquals("Games running should be 0",r.getBigGame().getGamesRunning(),0);
	}

}
