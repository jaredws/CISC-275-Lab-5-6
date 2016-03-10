package Game3;
import static org.junit.Assert.*;

import org.junit.Test;


public class MusselTests {
	
	/**
	 * Mussels should grow until they reach the final stage (set at 100)
	 */
	@Test
	
	public void testGrow() {
		Mussel testMussel = new Mussel(1,1) ;
		assertEquals(testMussel.getStage(), 0) ;
		testMussel.grow() ;
		assertEquals(testMussel.getStage(), 1) ;
		testMussel.grow() ;
		assertEquals(testMussel.getStage(), 2) ;
		testMussel.grow() ;
		assertEquals(testMussel.getStage(), 3) ;
		testMussel.grow() ;
		for (int i = 0 ; i< 100 ; i++) {
			testMussel.grow();
		}
		assertEquals(testMussel.getStage(), 100) ;
	}
	
	public void testDraw() {
		Mussel testMussel = new Mussel(1,1) ;
		System.out.println(testMussel.getMusselDrawing());
		assertEquals(testMussel.getMusselDrawing(), null);
	}

}