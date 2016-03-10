package Game2;
import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.Timer;

import javax.swing.JFrame;

import org.junit.Test;

import Game1.RipRapGame;
import Game3.Game3;
import OverallGame.OverallGame;
import OverallGame.gameWindow;


/**
 * @author Dwegrzyn
 *
 */
public class CrabCatcherGameTest {	
	
	JFrame frame = new JFrame();
	OverallGame bigGame = makeTestBigGame();
	
	/**makes an overall game for testing
	 * @return
	 */
	public OverallGame makeTestBigGame(){
		OverallGame bigGame = new OverallGame();
		frame.setBounds(100, 100, bigGame.frameWidth, bigGame.frameHeight);
		//frame.setVisible(false);
		return bigGame;
	}
	
	/**ends game without panel/visual settings
	 * @param g crab game to be ended
	 */
	public void fakeEndGame(CrabCatcherGame g){
		g.setGameOver(true);
		//send score to send to big game
		bigGame.setOverallScore(bigGame.getOverallScore() + g.getScore());
		//stop timer
		g.getTimer().stop();
		//set big game running to true and mark this game as complete
		bigGame.setGamesRunning(0);
		bigGame.getGamesComplete()[1] = true;
	}
	
	/**updates game without panel/visual settings
	 * @param g crab game to be updated
	 */
	public void fakeUpdateGame(CrabCatcherGame g){
		g.setTime(g.getTime()+1);
		if (g.getTime() >= g.getGameLength()){
    		//System.out.println("time is " + time + ">= " + gameLength);
			fakeEndGame(g);
		}
		//updates game's timed aspects - call animal.onTick() for all animals
		for (int i = g.getAnimals().size()-1; i >= 0; i--) {
			Animal each = g.getAnimals().get(i);
			if(each != null){each.onTickTest(100, g.getBigGame().frameWidth, g.getBigGame().frameHeight);}
			if(each.isOffScreen()){g.reAddAnimal(each);}
		}
		
		//update anims
		Iterator<ResultAnimation> it = g.getResultAnims().iterator();
		while(it.hasNext()){
			it.next().update((int)g.getTime());
		}
	}
	
	/**makes a crab game for testing with only an empty animals list and a timer
	 * @return
	 */
	public CrabCatcherGame makeTestCrabGame(){
		ArrayList<Animal> animals = new ArrayList<Animal>();
		CrabCatcherGame game = new CrabCatcherGame(0, animals, 0, 3, 10, null, 5, false, bigGame, frame);
		game.setTimer(new Timer(0, null));
		return game;
	}
	
	
	
	/**Test serialization and deserialization
	 * @throws IOException
	 */
	@Test
	public void SerializationTest() throws IOException {
		CrabCatcherGame testGame = makeTestCrabGame();
		testGame.setTime(10);
		testGame.setTimer(null);
		testGame.setScore(100);
		testGame.addAnimal(new Animal(5, 5, "crab", -10, 1, false));
		testGame.getResultAnims().add(new ResultAnimation(20, true, 0, 0));
		
		CrabCatcherGame.serialize(testGame, "game2TestOutput.ser");
		System.out.println("test game: " + testGame.toString());
		
		CrabCatcherGame loadedGame = (CrabCatcherGame)CrabCatcherGame.deserialize("game2TestOutput.ser");
		System.out.println("loaded game: " + loadedGame.toString());
		
		assertEquals(loadedGame.toString(), testGame.toString());
	}
	
	
	/**
	 * tests if tick-dependent conditions are updated in onTick()
	 */
	@Test
	public void tickTest(){
		makeTestBigGame();
		ArrayList<Animal> animals = new ArrayList<Animal>();
		CrabCatcherGame game = new CrabCatcherGame(0, animals, 0, 3, 10, null, 5, false, bigGame, frame);
		Animal crab = new Animal(0, 0, "crab", 5, 3, true);
		crab.setXdir(-1);
		crab.setXloc(-90);
		crab.setImageWidth(100);
		crab.setStep(5);
		game.addAnimal(crab);
		game.setTimer(new Timer(0, null));
		
		//check if game timer increases
		//check if crab location decreases
		fakeUpdateGame(game);
		assertEquals("crab should move 5 to the left", -95, game.getAnimals().get(0).getXloc());
		assertEquals("on tick: game time should increase from 0 to 1", 1, game.getTime(), 0);
		System.out.println("animal listttttttt ---- " + game.getAnimals());
			
		fakeUpdateGame(game);
		
		//check if offscreen crab is regenerated
		//(note: crab.isOffscreen will reset to false when it is regenerated, so test location instead)
		assertTrue("crab should move offscreen", game.getAnimals().get(0).getXloc() == -100 || game.getAnimals().get(0).getXloc() == bigGame.frameWidth);
		//check if offscreen crab moves back onscreen
		fakeUpdateGame(game);
		assertFalse(game.getAnimals().get(0).isOffScreen());
		assertTrue("crab should move onscreen", game.getAnimals().get(0).getXloc() > -100 || game.getAnimals().get(0).getXloc() < bigGame.frameWidth);		
	
		//check if gameOver is triggered by time = end time
		//check endGame() effects
		game.setGameOver(false);
		game.setScore(100);
		game.getBigGame().setOverallScore(100);
		game.setTime(game.getGameLength());
		
		fakeUpdateGame(game);
		assertTrue("on tick: time = gameLength should trigger gameOver", game.isGameOver());
		
		assertTrue(game.getBigGame().getGamesComplete()[1]);
		assertEquals(200, game.getBigGame().getOverallScore());
		assertEquals(0, game.getBigGame().getGamesRunning());
		
	}
		
	/**
	 * tests if game is created correctly
	 */
	@Test
	public void setupTest(){
		//ArrayList<Animal> animals = new ArrayList<Animal>();
		CrabCatcherGame game1 = makeTestCrabGame();	
		
		//check if generate animals generates 3 animals
		game1.generateAnimals();
		assertTrue("generateAnimals: 3 animals should exist", !game1.getAnimals().isEmpty());
		
		//check if animal images were assigned correctly
		for (int i=0; i < game1.getMaxAnimalsOnScreen(); i++){
			Animal testAnimal = game1.getAnimals().get(i);		
			assertTrue("animal image width should be initialized", testAnimal.getImageWidth() > 0);
			assertTrue(testAnimal.getImages()[0] != null);
			assertTrue(testAnimal.getImages()[0] != null);
		}
		

		
	}
	
	
	/**
	 * tests if mouse handler detects animal clicks and adjusts score accordingly
	 */
	@Test
	public void mouseInputTest(){
		System.out.println("starting mouseInputTest");
		CrabCatcherGame game = makeTestCrabGame();
		Animal crab = new Animal(100, 100, "crab", -5, 30, true);
		Animal fish = new Animal(500, 500, "fish", -3, 30, true);
		Animal mittencrab1 = new Animal(800, 800, "mittencrab", 5, 30, true);
		Animal mittencrab2 = new Animal(800, 800, "mittencrab", 5, 15, true); 
		game.setAnimals(new ArrayList<Animal>());
		//animal list goes from oldest (on top) to youngest (on bottom)
		game.addAnimal(crab);
		game.addAnimal(mittencrab2);//this crab will be drawn on top of mc1
		game.addAnimal(mittencrab1);
		game.addAnimal(fish);

		
		//check if getAnimalClicked returns correct animal
		assertEquals("get animal clicked: should be crab", crab, game.getAnimalClicked(100, 100));
		assertEquals("get animal clicked: should be fish", fish, game.getAnimalClicked(500, 500));
		System.out.println("clicking on 800, 800....");
		game.getAnimalClicked(800, 800);
		assertEquals("get animal clicked: should be mcrab ON TOP (mittencrab2)", mittencrab2, game.getAnimalClicked(800, 800));
		assertEquals("get animal clicked: should be nothing", null, game.getAnimalClicked(0, 0));
		
		
		//check if clicking animals effects score
		//mitten crab increase by 5
		game.onClickTest(800,800);
		assertEquals("on click mitten crab: game score should increase to 5", 5, game.getScore());
		
		//fish decrease by 3
		game.onClickTest(500 + fish.getImageWidth(), 500+fish.getImageHeight());
		assertEquals("on click fish: game score should decrease to 2", 2, game.getScore());
		
		//score should not be negative
		game.onClickTest(100,100);
		assertEquals("on click crab: game score should decrease to 0 (non-negative)", 0, game.getScore());
		
		//crab decrease by 5
		game.setScore(10);
		crab.setXloc(100);
		crab.setYloc(100);
		crab.setVisible(true);
		crab.setCaught(false);
		game.onClickTest(100 + crab.getImageWidth()/2, 100+crab.getImageHeight()/2);
		assertEquals("on click crab: game score should decrease by 5", 5, game.getScore());	
		assertTrue(crab.isCaught());
		
		//click nothing
		game.onClickTest(0,0);
		//assertEquals("on click nothing: game score should stay at 15", 15, game.getScore());	
		game.setAnimals(new ArrayList<Animal>());
		
		Animal problemCrab = new Animal(417, 251, "crab", -3, 10, true);
		game.addAnimal(problemCrab);
		problemCrab.setImageHeight(200);
		problemCrab.setImageWidth(250);
		//System.out.println("535,409 clicked a " + game.getAnimalClicked(535, 409));
		assertTrue(game.getAnimalClicked(535, 409).equals(problemCrab));
		//System.out.println("problem crab was " + problemCrab);
		
		Animal problemCrab2 = new Animal(381, 57, "crab", -3, 10, true);
		problemCrab2.setImageHeight(200);
		problemCrab2.setImageWidth(250);
		game.addAnimal(problemCrab2);
		//System.out.println("489,250 clicked a " + game.getAnimalClicked(489, 250));
		assertTrue(game.getAnimalClicked(489, 250).equals(problemCrab2));
		//System.out.println("problem crab2 was " + problemCrab2);
		
		Animal problemfish = new Animal(748, 306, "fish", -3, 10, true);
		problemfish.setImageHeight(200);
		problemfish.setImageWidth(250);
		game.addAnimal(problemfish);
		//System.out.println("878, 454 clicked a " + game.getAnimalClicked(878, 454));
		assertTrue(game.getAnimalClicked(878, 454).equals(problemfish));
		//System.out.println("problem fish was " + problemfish);
	}
	 
	
	/**
	 * test effects of endGame()
	 */
	@Test
	public void endGameEffectTest(){
		CrabCatcherGame game = makeTestCrabGame();
		bigGame.setGame2(game);
		bigGame.setOverallScore(0);
		game.setScore(100);
		fakeEndGame(game);
		assertEquals("big game score should increase to 100", 100, bigGame.getOverallScore());	
		assertEquals("big game game running should be 0", 0, bigGame.getGamesRunning());	
	}
	
	/**
	 * test sending an animal offscreen
	 */
	@Test
	public void setOffScreenLocTest(){
		CrabCatcherGame game = makeTestCrabGame();
		Animal crab = new Animal(100, 100, "crab", -5, 30, true);
		crab.setImageWidth(100);
		crab.setImageHeight(100);
		crab.setXdir(-1);
		game.addAnimal(crab);
		
		assertFalse(game.getAnimals().get(0).offScreen(bigGame.frameWidth, bigGame.frameHeight));
		game.setOffScreenLoc(game.getAnimals().get(0));
		
		assertTrue("if moving left should be sent to right border", game.getAnimals().get(0).getXloc() >= bigGame.frameWidth);
		
		game.getAnimals().get(0).setXdir(1);
		game.setOffScreenLoc(game.getAnimals().get(0));
		assertTrue("if moving right should be sent to left border", game.getAnimals().get(0).getXloc() <= -100);	
	}
	
	@Test
	public void updateScoreTest(){
		CrabCatcherGame game = makeTestCrabGame();
		game.setScore(100);
		game.updateScore(10);
		assertEquals(110, game.getScore());
		
		game.updateScore(-20);
		assertEquals(90, game.getScore());
		
		game.updateScore(-100);
		assertEquals("score should not go negative", 0, game.getScore());
		
		game.updateScore(-1);
		assertEquals("score should not go negative", 0, game.getScore());
	}
		

}
