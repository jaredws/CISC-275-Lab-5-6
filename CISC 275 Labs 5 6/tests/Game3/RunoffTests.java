package Game3;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Game2.CrabCatcherGame;
import OverallGame.OverallGame;
import OverallGame.gameWindow;

public class RunoffTests {
	
	static OverallGame o;
	static Game3 g;
	static Runoff r;
	static Runoff r2;
	static Runoff r3;
	static ArrayList<Integer> a;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		g = new Game3(o);
		r = new Runoff(1,1000);
		r2 = new Runoff(1, 4, 21, 63, null, 10, null, false);
		r3 = new Runoff(1,1000);
		a = new ArrayList<Integer>();
		a.add(2);a.add(7);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		o = null;
		g = null;
		r = null;
		r2 = null;
	}
	

	/**
	 * Runoff are equal only if they have the same location
	 */
	@Test
	public void testGrowAndRemoveFront() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		g = new Game3(o);
		r = new Runoff(1,1000);
		r2 = new Runoff(1, 4, 21, 63, null, 10, null, false);
		r3 = new Runoff(1,1000);
		g.getTimer().stop();
		assertEquals(1,r.getLength());
		r.grow();
		assertEquals(2,r.getLength());
		r.grow();r.grow();r.grow();r.grow();r.grow();
		assertEquals(5, r.getLength());
		assertEquals(1000,r.getFront());
		r.removeFront();
		assertEquals(4,r.getLength());
		assertEquals(true,r.hasDied);
		assertEquals(1000+Game3.scalor,r.getFront());
		r.grow();
		assertEquals(4, r.getLength());
	}
	
	@Test
	public void setRowTest() {
		r.setRow(12);
		assertEquals("Row should be 12",r.getRow(),12);
	}
	
	@Test
	public void setStrengthTest() {
		r.setStrength(10);
		assertEquals("Strength should be 10",r.getStrength(),10);
	}
	
	@Test
	public void setHealthTest() {
		r.setHealth(a);
		assertTrue("Health should be [2,7]",r.getHealth().equals(a));
	}
	
	@Test
	public void equalsTest() {
		o = new OverallGame();
		o.setGameWindow(new	gameWindow(o));
		g = new Game3(o);
		r = new Runoff(1,1000);
		r2 = new Runoff(1, 4, 21, 63, null, 10, null, false);
		r3 = new Runoff(1,1000);
		assertFalse("r != r2",r.equals(r2));
		assertTrue("r == r3",r.equals(r3));
	}
	
	@Test
	public void toStringTest() {
		r.setRow(3);
		r.setFront(14);
		r.setStrength(53);
		r.setHealth(a);
		r.setTicksSinceMoved(33);
		assertTrue("toString should return 'Runoff [ Row: 3, Front Location: 14, Strength: 53, Health: [2, 7], TicksSinceMoved: 33]'",r.toString().equals("Runoff [ Row: 3, Front Location: 14, Strength: 53, Health: [2, 7], TicksSinceMoved: 33]"));
	}
	
	@Test
	public void getSerialversionuidTest() {
		assertEquals("Serialversionuid should be 303",Runoff.getSerialversionuid(),303);
	}

}