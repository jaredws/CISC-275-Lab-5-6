package Game3;
import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class MusselTests {
	
	Mussel m;
	Mussel m2;
	static Image img;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		try {
			img = ImageIO.read(new File("images/mussel.pnp"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Before
	public void setUp() {
		m = new Mussel(10,15);
		m2 = new Mussel(5,15,2,img);
		
	}
	
	@After
	public void tearDown() {
		m = null;
	}
	/**
	 * Mussels should grow until they reach the final stage (set at 100)
	 */
	@Test
	public void testGrow() {
		assertEquals(m.getStage(), 0) ;
		m.grow() ;
		assertEquals(m.getStage(), 1) ;
		m.grow() ;
		assertEquals(m.getStage(), 2) ;
		m.grow() ;
		assertEquals(m.getStage(), 3) ;
		m.grow() ;
		for (int i = 0 ; i< 100 ; i++) {
			m.grow();
		}
		assertEquals(m.getStage(), 100) ;
	}
	
	public void testDraw() {
		System.out.println(m.getMusselDrawing());
		assertEquals(m.getMusselDrawing(), null);
	}
	
	@Test
	public void setXLocTest() {
		m.setXLoc(15);
		assertEquals("X location should be 10",m.getXLoc(),15);
	}
	
	@Test
	public void setYLocTest() {
		m.setYLoc(10);
		assertEquals("Y location should be 15",m.getYLoc(),10);
	}
	
	@Test
	public void toStringTest() {
		assertTrue("Mussels [ Xloc: 10, Yloc: 15, Stage: 0]",m.toString().equals("Mussels [ Xloc: 10, Yloc: 15, Stage: 0]"));
	}

}