package Game3;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TileTest {

	Tile t;
	Tile t2;
	
	@Before
	public void setUp() throws Exception {
		t = new Tile(12,18);
		Image img = ImageIO.read(new File("images/sand.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		t2 = new Tile(10,20,img);
	}

	@After
	public void tearDown() throws Exception {
		t = null;
		t2 = null;
	}

	@Test
	public void setRowTest() {
		t.setRow(20);
		assertEquals("Row should be 20",t.getRow(),20);
	}
	
	@Test
	public void setColTest() {
		t.setCol(30);
		assertEquals("Column should be 30",t.getCol(),30);
	}
	
	@Test
	public void toStringTest() {
		assertTrue("toString should return 'Row: 12 \nCol:  18'",t.toString().equals("Row: 12\nCol:  18"));
	}
	
	@Test
	public void setImageTest() {
		Image img2 = null;
		try {
			img2 = ImageIO.read(new File("images/net.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		t2.setImage(img2);
		assertTrue(t2.getImage().equals(img2));
	}

}
