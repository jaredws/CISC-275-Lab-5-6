package Game1;

import static org.junit.Assert.*;

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
		assertTrue("Speed should be 12",s.speed==12);
		assertTrue("acc should be 0",s.acc==0);
	}
	
	/*@Test
	public void updateTest() {
		
	}*/

}
