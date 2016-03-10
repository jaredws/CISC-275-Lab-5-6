package Game2;

import static org.junit.Assert.*;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import junit.framework.TestCase;

public class AnimalTest {
	Animal crab = new Animal(10, 15, "crab", -5,
			10, true);
	Animal fish = new Animal(20, 15, "fish", -3, 10, true);

	
	@Test
	public void offScreenDetectionTest(){
		Animal mittencrab = new Animal(100, 10, "mittencrab", -3, 10, true);
		mittencrab.setImageHeight(200);
		mittencrab.setImageWidth(250);
		
		//crab still on 200x200 screen, but off 100x100 screen
		assertFalse(mittencrab.offScreen(200, 200));
		assertTrue(mittencrab.offScreen(100, 100));
		
		//0,0
		mittencrab.setXloc(0);
		mittencrab.setYloc(0);
		assertFalse("0,0 is still on screen", mittencrab.offScreen(200, 200));
		
		//-5,0
		mittencrab.setXloc(-5);
		assertFalse("-5,0 is still partly on screen", mittencrab.offScreen(200, 200));
		
		//195,0
		mittencrab.setXloc(195);
		assertFalse("195,0 is still partly on screen", mittencrab.offScreen(200, 200));
		
		//195, 195
		mittencrab.setYloc(195);
		assertFalse("195,195 is still partly on screen", mittencrab.offScreen(200, 200));
		
		//left bound
		mittencrab.setXloc(-250);
		assertTrue(mittencrab.offScreen(200, 200));
		
		//right bound
		mittencrab.setXloc(200);
		assertTrue(mittencrab.offScreen(200, 200));
		
		//upper bound
		mittencrab.setYloc(-200);
		assertTrue(mittencrab.offScreen(200, 200));
		
		//lower bound
		mittencrab.setYloc(200);
		assertTrue(mittencrab.offScreen(200, 200));
		
	}
	
	@Test
	public void regenerateTest(){
		int oldx = crab.getXloc();
		int oldy = crab.getYloc();
		crab.setTimeLeftOnScreen(0);
		
		crab.regenerateAnimal(800,800);
		assertEquals("time on screen should reset", 0, crab.getTimeLeftOnScreen(), crab.getDisplayDuration());
		assertFalse("location should change", crab.getXloc() == oldx && crab.getYloc() == oldy);
		assertTrue("crab should be visible", crab.isVisible());
		assertEquals("picnum should reset", 0, crab.getPicNum());
	}
	
	@Test
	public void copyTest(){
		//make a fish with the 2 fish images
		Animal fish = new Animal(20, 15, "fish", -3, 10, true);		
		Image image = null;
		Image image2 = null;
		try {
			image = ImageIO.read(new File("images/fish.png"));
			image2 = ImageIO.read(new File("images/fish_left.png"));
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		
		image = image.getScaledInstance(250, 200, 0);
		image2 = image2.getScaledInstance(250, 200, 0);
		Image[] images = new Image[2];
		images[0] = image;
		images[1] = image2;
		
		fish.setImages(images);
		
		//make & test the copy of fish
		Animal copy = fish.copy();		
		
		assertTrue(copy.getXloc() == fish.getXloc());
		assertTrue(copy.getYloc() == fish.getYloc());
		assertTrue(copy.getTypeOfAnimal() == fish.getTypeOfAnimal());
		assertTrue(copy.getScoreEffect() == fish.getScoreEffect());
		assertTrue(copy.isVisible() == fish.isVisible());
		assertTrue(copy.getDisplayDuration() == fish.getDisplayDuration());
		assertTrue(copy.getImageHeight() == fish.getImageHeight());
		assertTrue(copy.getImageWidth() == fish.getImageWidth());
		
	}
	
	@Test
	public void onTickAndMoveAndImagesTest(){
		//Set up the crab
		crab.setXloc(0);
		crab.setYloc(0);
		crab.setXdir(1);
		crab.setStep(5);
		Image image1 = null;
		Image image2 = null;
		try {
			image1 = ImageIO.read(new File("images/maincrab1.png"));
			image2 = ImageIO.read(new File("images/maincrab2.png"));
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		
		image1 = image1.getScaledInstance(100, 200, 0);
		image2 = image2.getScaledInstance(100, 200, 0);
		Image[] images = new Image[2];
		images[0] = image1;
		images[1] = image2;		
	
		crab.setImages(images);
		
		//Test Set Images
		assertTrue("setImages() should set height", crab.getImageHeight() == 200);
		assertTrue("setImages() should set width", crab.getImageWidth() == 100);
		
		//Test Get Image
		assertEquals("getImage() should access array[picNum]", image1, crab.getImage());
		
		//Test Move
		crab.move();
		
		assertEquals("crab xloc should increase by 5", 5, crab.getXloc());
		assertEquals("crab should only move horizontally", 0, crab.getYloc());
		
		//Test OnTick updates for Movement and PicNum
		crab.onTickTest(3, 800, 800);
		
		assertEquals("crab xloc should increase by 5", 10, crab.getXloc());
		assertEquals("crab should only move horizontally", 0, crab.getYloc());
		assertEquals("crab picnum should not yet increase", 0, crab.getPicNum());
		
		crab.onTickTest(100, 800, 800);
		assertEquals("crab picnum should increase", 1, crab.getPicNum());
		assertEquals("getImage() should access array[picNum]", crab.getImage(), image2);
		
		//Test OnTick updates for OnScreen
		crab.setXloc(-95);
		crab.setYloc(0);
		assertFalse("crab still on screen", crab.isOffScreen());
		crab.setXdir(-1);
		crab.onTickTest(100, 800, 800);
		
		assertEquals("crab xloc should decrease by 5", -100, crab.getXloc());
		assertEquals("crab should only move horizontally", 0, crab.getYloc());
		assertTrue("crab should have moved offscreen", crab.isOffScreen());	
		assertEquals("crab picnum should start over", 0, crab.getPicNum());	
		assertEquals("getImage() should access array[picNum]", crab.getImage(), image1);
	}
	
	@Test
	public void updateAnimTest(){
		crab.setPicNum(0);
		//note: images.length = 2
		crab.updateAnimation(7);
		assertEquals("crab picnum not change", 0, crab.getPicNum());
		
		crab.updateAnimation(10);
		assertEquals("crab picnum should increase", 1, crab.getPicNum());
		
		crab.updateAnimation(15);
		assertEquals("crab picnum should start over", 0, crab.getPicNum());	
		
	}
	
	@Test
	public void randomXDirTest(){
		crab.setXdir(0);
		
		crab.setRandomXDir();
		assertTrue(crab.getXdir() == -1 || crab.getXdir() == 1);
		
		crab.setRandomXDir();
		assertTrue(crab.getXdir() == -1 || crab.getXdir() == 1);
	}
	

}
