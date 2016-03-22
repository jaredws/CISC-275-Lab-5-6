package Game3;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PlantTests {
	/**
	 * Since all things that can go wrong with this code are in loadPlantImages
	 * and those errors are caught with Exceptions, this class doesn't need to be
	 * tested. All other methods are trivial
	 */
	
	static Plant p;
	static Plant p2;
	static Plant p3;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		p = new Plant(1, 1, "test");
		p2 = new Plant(3, 4, 6, 7, 8, 20, "Weak", null);
		p3 = new Plant(1,1,"test");
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		p = null;
		p2 = null;
		p3 = null;
	}
	
	@Test
	public void setRowTest() {
		p.setRow(3);
		assertEquals("Row should be 3",p.getRow(),3);
	}
	
	@Test
	public void setColTest() {
		p.setCol(2);
		assertEquals("Column should be 2",p.getCol(),2);
	}
	
	@Test
	public void setStrengthTest() {
		p.setStrength(10);
		assertEquals("Strength should be 10",p.getStrength(),10);
	}
	
	@Test
	public void setHealthTest() {
		p.setHealth(50);
		assertEquals("Health should be 50",p.getHealth(),50);
	}
	
	@Test
	public void setTypeTest() {
		p.setType("Some Type");
		assertTrue("Type should be 'Some Type'",p.getType().equals("Some Type"));
	}
	
	@Test
	public void toStringTest() {
		p.setRow(5);
		p.setCol(6);
		p.setStrength(15);
		p.setHealth(33);
		p.setType("Strong");
		assertTrue("toString should return 'Plants [ Row: 5, Col: 6, Strength: 15, Health: 33, Type: Strong]'",p.toString().equals("Plants [ Row: 5, Col: 6, Strength: 15, Health: 33, Type: Strong]"));
	}
	
	@Test
	public void equalsTest() {
		p = new Plant(1, 1, "test");
		assertFalse("p != p2",p.equals(p2));
		assertTrue("p == p3",p.equals(p3));
	}
	

}
