package Game1;

import static org.junit.Assert.assertEquals;

import javax.swing.JPanel;

import org.junit.Test;

public class JumpingBarTest {
	/**Create a jumpingBar with both stop1 and stop2 0
	 * 
	 */
	@Test
	public void TestJumpingBar(){
		JumpingBar testJumpingBar1 = new JumpingBar(0,0, null);
		Crab c = new Crab(0,0,100);
		assertEquals(testJumpingBar1.getCurrentValue(),0);// Inital currentValue should be 0
		testJumpingBar1.setSpeed(0);// set the speed by 0
		assertEquals(testJumpingBar1.getSpeed(),0);
		JPanel panel=new JPanel();
		testJumpingBar1.makeLabels(panel);
		testJumpingBar1.clicked();
		testJumpingBar1.barloc=1;
		testJumpingBar1.stop1=2;
		c.clicked(0);

		
	

	}

	/**create a JumpingBar with stop1 be 8 and stop2 be 5
	 * 
	 */
	@Test
	public void TestJumpingBar2(){
		JumpingBar testJumpingBar2 = new JumpingBar(8,5, null);
		assertEquals(testJumpingBar2.getCurrentValue(),0);// Inital currentValue should be 0
		testJumpingBar2.setSpeed(1);//set speed by 1, then currentValue will be changed by 1 each second
		assertEquals(testJumpingBar2.getSpeed(),1);
		
		
	}
}
