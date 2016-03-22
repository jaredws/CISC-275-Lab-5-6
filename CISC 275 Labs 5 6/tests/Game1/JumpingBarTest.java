package Game1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import javax.swing.JPanel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import OverallGame.OverallGame;
import OverallGame.gameWindow;

public class JumpingBarTest {
	
	static JumpingBar j;
	static JumpingBar j2;
	static JumpingBar j3;
	static Crab c;
	static RipRapGame r;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		OverallGame o = new OverallGame();
		gameWindow gw = new gameWindow(o);
		o.setGameWindow(gw);
		r = new RipRapGame(12,o,gw.getFrame());
		j3 = new JumpingBar(1,2,r);
		j = new JumpingBar(0,0, null);
		JPanel panel=new JPanel();
		j.makeLabels(panel);
		j2 = new JumpingBar(8,5, null);
		c = new Crab(0,0,100);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		j = null;
		j2 = null;
		c = null;
	}
	
	/**Create a jumpingBar with both stop1 and stop2 0
	 * 
	 */
	@Test
	public void TestJumpingBar(){
		assertEquals(j.getCurrentValue(),0);// Inital currentValue should be 0
		j.setSpeed(0);// set the speed by 0
		assertEquals(j.getSpeed(),0);
		j.clicked();
		j.barloc=1;
		j.stop1=2;
		c.clicked(0);
	}

	/**create a JumpingBar with stop1 be 8 and stop2 be 5
	 * 
	 */
	@Test
	public void TestJumpingBar2(){
		assertEquals(j2.getCurrentValue(),0);// Inital currentValue should be 0
		j2.setSpeed(1);//set speed by 1, then currentValue will be changed by 1 each second
		assertEquals(j2.getSpeed(),1);
	}
	
	@Test
	public void clickedTest1() {
		j3.barloc = 10;
		j3.stop1 = 15;
		j3.clicked();
		assertEquals("barloc should be 100",j3.barloc,100);
	}
	@Test
	public void clickedTest2() {
		j3.barloc = 10;
		j3.stop1 = 8;
		j3.stop2 = 7;
		j3.game.score = 100;
		j3.clicked();
		assertEquals("Score should be 200",j3.game.score,200);
	}
	
	@Test
	public void updateTest() {
		JPanel jp = new JPanel();
		Stone s = new Stone(2, 3, 4);
		j3.game = r;
		j3.game.stone = s;
		j3.game.crab = c;
		j3.makeLabels(jp);
		j3.game.score = 105;
		j3.barloc = 150;
		j3.update(jp);
		assertEquals("Score should be 5",j3.game.score,5);
		j3.passed=true;
		j3.game.score=0;
		j3.barloc=90;
		j3.update(jp);
		Stone s2 = new Stone(10000000,3,4);
		j3.game.stone = s2;
		j3.update(jp);
		assertFalse("passed should be false",j3.passed);
		Stone s3 = new Stone(100,3,4);
		j3.game.stone = s3;
		j3.update(jp);
		assertEquals("barloc should be 19",j3.barloc,19);
		
	}
}
