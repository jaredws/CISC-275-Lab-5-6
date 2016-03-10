package Game2;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResultAnimationTest {
	@Test
	public void updateTest(){
		ResultAnimation pos = new ResultAnimation(100, true, 0, 0);
		ResultAnimation neg = new ResultAnimation(100, false, 0, 0);
		//initial state
		assertEquals(pos.getPicNum(), 0);
		assertEquals(pos.getLoops(), 0);
		assertFalse(pos.isComplete());
		
		//after 1 ms
		pos.update(1);
		assertEquals(pos.getPicNum(), 0);
		assertEquals(pos.getLoops(), 0);
		assertFalse(pos.isComplete());
		
		//after 5 ms
		pos.update(5);
		assertEquals("pin num should increase", 1, pos.getPicNum());
		assertFalse(pos.isComplete());
		
		//after 10 ms
		pos.update(10);
		assertEquals("pic num should loop", 0, pos.getPicNum());
		assertEquals("loop should increase", 1, pos.getLoops());
		assertFalse(pos.isComplete());
		
		//after 15 ms
		pos.update(15);
		assertEquals("pic num should increase", 1, pos.getPicNum());
		assertEquals("loop should stay same", 1, pos.getLoops());
		assertFalse(pos.isComplete());
		
		//after 20 ms
		pos.update(20);
		assertEquals("pic num should loop", 0, pos.getPicNum());
		
		//complete 2nd loop
		pos.update(25);
		assertEquals("loop should increase", 2, pos.getLoops());
		
		//after 2nd loop
		pos.update(30);
		assertEquals("loop should increase", 3, pos.getLoops());
		assertTrue(pos.isComplete());	
	}
	
}
