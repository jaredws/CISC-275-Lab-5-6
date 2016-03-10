package Game1;

import static org.junit.Assert.*;

import java.awt.Point;

import javax.swing.JPanel;

import org.junit.Test;

public class MovingObjectTest {

	/**
	 * Test the constructor
	 */
	@Test
	public void testMovingObject() {
		MovingObject m=new MovingObject(0, 0, 100);
		assertEquals(new Point(0,0),m.getPosition());
		assertEquals(100,m.getSize());
		JPanel p=new JPanel();
		assertTrue(m.addItem(p, "images/sun1.png"));
		
	}
	/**
	 * Test the crab
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testCrab(){
		Crab c=new Crab(0, 0, 100);
		JPanel p=new JPanel();
		assertTrue(c.addItem(p, "images/sun1.png"));
		assertEquals(c.getMode(),1);
		assertEquals(c.getSize(),100);
		c.clicked(1);
		c.update();
	}
	@Test
	public void testCloud(){
		Cloud c=new Cloud(100, 100, 100);
		JPanel p=new JPanel();
		assertTrue(c.addItem(p, "images/sun1.png"));
		assertEquals(c.getSize(),100);
		for(int i=0;i<100;i++){
		c.update();}
	}
	@Test
	public void testStone(){
		Stone c=new Stone(100, 100, 100);
		JPanel p=new JPanel();
		assertTrue(c.addItem(p, "images/sun1.png"));
		assertEquals(c.getSize(),100);
		c.update();
		assertEquals(c.getPosition().x,94);
		c.kicked();
		c.update();
	}
	@Test
	public void testSun(){
		Sun c=new Sun(100);
		JPanel p=new JPanel();
		p.setBounds(100, 100, 100, 100);
		assertTrue(c.addItem(p));
		assertEquals(c.getSize(),100);
		c.update();
		assertEquals(c.getPosition().x,0);
		
	}
	
	
}
