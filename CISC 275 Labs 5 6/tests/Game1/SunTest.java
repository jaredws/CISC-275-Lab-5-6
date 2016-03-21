package Game1;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SunTest {

	static Sun s;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		s = new Sun(3);
		s.sun = new JLabel("test");
		s.sun.setBounds(3, 2, 23, 32);
		s.point = new Point();
		s.point.setLocation(3, 4);
		s.angle = 10.0;
		s.tick = 30;
		s.r = 5;
		s.storeangle = 5.5;
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		s = null;
	}
	
	@Test
	public void updateTest() {
		s.update();
		assertEquals("tick should be 0",s.tick,0);
		assertTrue("angle should be 10.1",s.angle==10.1);
		s.tick = 19;
		s.sun.setBounds(30, 2, 23, 32);
		s.update();
		assertEquals("tick should be 20",s.tick,20);
		assertTrue("angle should be 5.5",s.angle==5.5);
	}
	
	@Test
	public void addItemTest() {
		JPanel jp = new JPanel();
		jp.add(s.sun);
		assertTrue("addItem should return true",s.addItem(jp));
	}

}
