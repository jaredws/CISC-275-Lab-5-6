package Game2;

import static org.junit.Assert.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ResultAnimationTest {
	
	static ResultAnimation ra;
	static Image img;
	
	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		ra = new ResultAnimation(50, true, 15, 20);
		img = ImageIO.read(new File("images/coin.png"));
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		ra = null;
	}
	
	@Test
	public void updateTest(){
		ResultAnimation pos = new ResultAnimation(100, true, 0, 0);
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
	
	@Test
	public void setSizeTest() {
		ra.setSize(40);
		assertEquals("Size should be 40",ra.getSize(),40);
	}
	
	@Test
	public void setCompleteTest() {
		ra.setComplete(false);
		assertFalse("Complete should be false",ra.isComplete());
	}
	
	@Test
	public void getXlocTest() {
		assertEquals("X location should be 15",ra.getXloc(),15);
	}
	
	@Test
	public void getYlocTest() {
		assertEquals("Y location should be 20",ra.getYloc(),20);
	}
	
	@Test
	public void setLoopsTest() {
		ra.setLoops(13);
		assertEquals("# of Loops should be 13",ra.getLoops(),13);
	}
	
	@Test
	public void setImagesTest() {
		Image[] i = new Image[1];
		i[0] = img;
		ra.setImages(i);
		assertEquals("Image should have the same hashCode as img",ra.getImages()[0].hashCode(),img.hashCode());
		ra.setPicNum(0);
		assertEquals("Image should have the same hashCode as img",ra.getCurrentImage().hashCode(),img.hashCode());
	}
	
}
