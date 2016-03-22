package Game1;

import static org.junit.Assert.*;
import javax.swing.JLabel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class StoneTest {
	static Stone s;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		s = new Stone(1,1,1);
	}

	@AfterClass
	public static void tearDownBeforeClass() throws Exception {
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
		s = new Stone(1,1,1);
		s.label = new JLabel("test");
		s.tick = 1;
		s.orig_speed = 20;
		s.update();
		assertEquals("tick should be 0",s.tick,0);
		assertEquals("Speed should equal Original Speed",s.speed,s.orig_speed);
		s.speed = 100;
		s.update();
		assertEquals("X position should be 101",(int)s.getPosition().getX(),101);
	}

}
