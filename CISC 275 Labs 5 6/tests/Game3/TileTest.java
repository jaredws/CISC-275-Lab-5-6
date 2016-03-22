package Game3;

import static org.junit.Assert.*;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import OverallGame.OverallGame;

public class TileTest {

	static Tile t;
	static Tile t2;
	static OverallGame o;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		t = new Tile(12,18);
		Image img = ImageIO.read(new File("images/sand.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		t2 = new Tile(10,20,img);
		o = new OverallGame();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		t = null;
		t2 = null;
		o = null;
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
		t = new Tile(12,18);
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
