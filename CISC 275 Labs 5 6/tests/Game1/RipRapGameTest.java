package Game1;

import static org.junit.Assert.*;

import javax.swing.JFrame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import OverallGame.OverallGame;

public class RipRapGameTest {
	RipRapGame r;
	OverallGame o;
	@Before
	public void setUp() throws Exception {
		OverallGame o = new OverallGame();
		JFrame j = new JFrame();
		r = new RipRapGame(1,o,j);
	}

	@After
	public void tearDown() throws Exception {
		o = null;
		r = null;
	}

	@Test
	public void addScoreTest() {
		r.score = 10;
		r.addScore(5);
		//assertEquals("Score should be 15",r.score,15);
		System.out.print(r.score);
	}

}
