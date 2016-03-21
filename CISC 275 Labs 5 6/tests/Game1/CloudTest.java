package Game1;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JLabel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CloudTest {

	static Cloud c;
	static Point p;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		c = new Cloud(1, 2, 3);
		p = new Point();
		p.setLocation(3, 2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		c = null;
	}
	
	
	@Test
	public void updateTest() {
		c.label = new JLabel("test");
		c.label.setBounds(2, 5, 50, 45);
		c.setPosition(p);
		c.speed = -10;
		c.update();
		assertEquals("X position should be 2",(int)c.getPosition().getX(),2);
		c.speed = 10;
		c.update();
		assertEquals("X position should be 0",(int)c.getPosition().getX(),0);
		c.speed = 0;
		c.update();
		assertEquals("X position should be 0",(int)c.getPosition().getX(),0);
	}

}
