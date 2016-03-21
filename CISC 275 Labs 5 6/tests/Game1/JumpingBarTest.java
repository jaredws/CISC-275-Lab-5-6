package Game1;

import static org.junit.Assert.assertEquals;

import javax.swing.JPanel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JumpingBarTest {
	
	static JumpingBar j;
	static JumpingBar j2;
	static Crab c;
	
	@BeforeClass
	public static void setUpBeforeClass() {
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
}
