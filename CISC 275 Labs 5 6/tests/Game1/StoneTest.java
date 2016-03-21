package Game1;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StoneTest {
	Stone s;
	
	@Before
	public void setUp() throws Exception {
		s = new Stone(1,1,1);
	}

	@After
	public void tearDown() throws Exception {
		s = null;
	}

	@Test
	public void kickedTest() {
		s.orig_speed = -1;
		s.kicked();
		assertEquals("Speed should be 12",s.speed,12);
		assertEquals("acc should be 0",s.acc,0);
	}
	
	@Test
	public void updateTest() {
		s.label = new JLabel("test");
		s.tick = 1;
		s.update();
		assertEquals("tick should be 0",s.tick,0);
		assertEquals("Speed should equal Original Speed",s.speed,s.orig_speed);
		s.speed = 100;
		s.update();
		assertEquals("X position should be 101",(int)s.getPosition().getX(),101);
	}

}
