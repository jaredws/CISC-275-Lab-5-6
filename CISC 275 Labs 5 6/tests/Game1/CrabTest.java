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
	
	@Test
	public void setSpeedTest() {
		c.setSpeed(5);
		assertEquals("Speed should be set to 5",(int)c.speed,5);
	}
	
	@Test
	public void clickedTest() {
		c.n = -1;
		c.clicked(1);
		assertEquals("Speed should be 7",(int)c.speed,7);
		assertEquals("n should be 0",c.n,0);
		
		c.n=2;
		c.clicked(2);
		assertEquals("n should be 0",c.n,2);
		//Nothing Happens
	}
	
	/*@Test
	public void updateTest3() {
		//3rd Branch
		c.currheight = 3;c.setSpeed(5);
		c.update();
		assertTrue("Speed should be 0",c.speed==0);
		assertTrue("currHeight should be 0",c.currheight==0);
	}*/

}
