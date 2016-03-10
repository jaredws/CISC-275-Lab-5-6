package Game1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CrabTest {
	static Crab c;
	@Before
	public void setUp() throws Exception {
		c = new Crab(1,1,1);
	}

	@After
	public void tearDown() throws Exception {
		c = null;
	}

	@Test
	public void getModeTest() {
		c.mode = 2;
		assertEquals("Mode should be 2",c.getMode(),2);
	}
	
	@Test
	public void setModeTest() {
		c.setMode(3);
		assertEquals("Mode should be set to 3",c.getMode(),3);
	}

}
