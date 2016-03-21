package Game2;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import Game3.Mussel;

/**
 * @author Dwegrzyn
 * A class representing the animals that appear in the Crab Catcher game
 */
public class Animal implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 201L;
	//FIELDS
	protected int xloc;
	protected int yloc;
	protected String typeOfAnimal; //for now, "crab", "mittencrab", or "fish"
	protected int scoreEffect;
	protected double displayDuration; //length of time the animal should stay on screen (constant)
	protected double timeLeftOnScreen; //how long the animal has left on screen (decreases with time); default is displayDuration
	protected boolean visible;
	protected Image[] images; //image of animal
	protected int imageWidth = 250;
	protected int imageHeight = 200;
	protected boolean caught = false;
	private boolean offScreen = false;
	private int xdir = 1;
	private int ydir;
	private int step; //speed of animal
	private static int maxSpeed = 10;
	private int picNum = 0;
		
	/**All-parameter constructor for deserialization. Not used publicly.
	 * @param xloc x location of animal
	 * @param yloc y location of animal
	 * @param typeOfAnimal - mittencrab, fish, or crab
	 * @param scoreEffect - number to be added to score if this animal is clicked (can be pos or neg)
	 * @param displayDuration - time limit for animal to appear onscreen
	 * @param timeLeftOnScreen - how long animal has left on screen
	 * @param visible - whether animal should be drawn or not
	 * @param images - array of images for this animal's animation (must all be same size)
	 * @param imageWidth - width of images in array
	 * @param imageHeight - height of images in array
	 */
	private Animal(int xloc, int yloc, String typeOfAnimal, int scoreEffect,
			double displayDuration, double timeLeftOnScreen, boolean visible, Image[] images, int imageWidth, int imageHeight) {
		super();
		this.xloc = xloc;
		this.yloc = yloc;
		this.typeOfAnimal = typeOfAnimal;
		this.scoreEffect = scoreEffect;
		this.displayDuration = displayDuration;
		this.timeLeftOnScreen = timeLeftOnScreen;
		this.visible = visible;
		this.images = images;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	//
	//CONSTRUCTOR
	/**
	 * @param xloc the x coordinate of the animal
	 * @param yloc the y coordinate of the animal
	 * @param typeOfAnimal the type of animal ( "crab", "mittencrab", or "fish")
	 * @param scoreEffect the integer to be added to the score if this animal is caught (can be negative)
	 * @param displayDuration how long the animal should stay on screen
	 * @param visible is true if the animal is visible
	 */
	public Animal(int xloc, int yloc, String typeOfAnimal, int scoreEffect,
			double displayDuration, boolean visible) {
		this.xloc = xloc;
		this.yloc = yloc;
		this.typeOfAnimal = typeOfAnimal;
		this.scoreEffect = scoreEffect;
		this.displayDuration = displayDuration;
		this.timeLeftOnScreen = displayDuration;
		this.visible = visible;
		this.caught = false;
		Random r = new Random();
		this.step = (maxSpeed - r.nextInt(maxSpeed - 3));
		this.images = new Image[2];
	}
	
	//COPIER
	/**
	 * @return a copy of the animal that shares the major values:
	 * [location, type, scoreEffect, displayDuration, visibility, image]
	 */
	public Animal copy(){
		Animal copy = new Animal(xloc, yloc, typeOfAnimal, scoreEffect, displayDuration, visible);
		copy.setImages(images); //note: also sets image height and width
		return copy;
	}
	
	
	//SERIALIZATION
	/**Saves animal object to file.
	 * @param obj
	 * @param fileName
	 */
	public static void serialize(Object obj, String fileName) {
		try {
	        FileOutputStream fos = new FileOutputStream(fileName);
	        ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(obj);
	        fos.close();
		}
		catch (IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
	}
	
	/**
	 * Method to read and instantiate animal from file. The reverse of the serialize function
	 * @param fileName
	 * @return loaded animal object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(String fileName) {
		Animal obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Animal)ois.readObject();
			ois.close();
		}
		catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		catch (ClassNotFoundException e){
			System.out.println("Read Error: " + e.getMessage());
		}
		return obj;
	}
	
	
	//MAIN METHODS
	/**
	 * Call this method to regenerate animal after it is caught or time on screen expires
	 * makes the animal invisible, resets its timeLeftOnScreen, and sets offscreen location randomly
	 * @param xbound max x location
	 * @param ybound max y location
	 */
	public void regenerateAnimal(int xbound, int ybound){
		//gives animal a random location
		Random r = new Random();
		//USE SCREEN WIDTH
		xloc = r.nextInt(xbound - this.imageWidth);
		yloc = r.nextInt(ybound - this.imageHeight);
		visible = true;
		picNum = 0;
		//reset timeLeftOnScreen to display duration
		timeLeftOnScreen = displayDuration;
	}
	
	
	
	//MOVING METHODS
	/** updates animal's timed elements (movement, animation, and offScreen check)
	 * @param game - the game to update
	 */
	public void onTick(CrabCatcherGame game){
		this.move();
		this.updateAnimation((int)game.getTime());
		this.offScreen = offScreen(game.getBigGame().frameWidth, game.getBigGame().frameHeight);
	}
	
	/**for testing purposes only; 
	 * updates animal's timed elements 
	 * but only takes time and game dimensions instead of game object
	 * @param time
	 * @param width
	 * @param height
	 */
	public void onTickTest(int time, int width, int height){
		this.move();
		this.updateAnimation(time);
		this.offScreen = offScreen(width, height);
	}
	
	/** increases animal's location based on xdir(horizontal only)
	 */
	public void move(){
		xloc += xdir*step;
	}
	
	/**returns true if the Animal is off screen (accounting for imageWidth)
	 * @param frameWidth - width of game frame
	 * @param frameHeight - height of game frame
	 * @return
	 */
	public boolean offScreen(int frameWidth, int frameHeight){
		 return (this.xloc + this.imageWidth <= 0 || this.xloc >= frameWidth
				 || this.yloc >= frameHeight
				 || this.yloc + this.imageHeight <= 0);
	}


	
	/** updates PicNum (current animation frame)
	 * @param time - the current game time in milliseconds
	 */
	public void updateAnimation(int time){
		if(time % 5 == 0){
			picNum = (picNum + 1) % images.length;
		}
	}

	
	//GETTERS & SETTERS
	/**
	 * Get x Location
	 * @return x Location as an int
	 */
	public int getXloc() {
		return xloc;
	}

	@Override
	/**
	 * Overridden toString method 
	 * Animal crab [location=(x, y), scoreEffect, timeLeft, visibility]
	 */
	public String toString() {
		return "Animal " + typeOfAnimal + " [location=(" + xloc + ", " + yloc + ")" + ", scoreEffect=" + scoreEffect
				+ ", timeLeft=" + timeLeftOnScreen + ", visible="
				+ visible + "]";
	}

	/**
	 * sets xdir to 1 or -1 randomly
	 */
	public void setRandomXDir(){
		Random r = new Random();
		boolean left = r.nextBoolean();
		if(left){
			setXdir(1);
		}
		else{
			setXdir(-1);
		}
	}
	
	public void setXloc(int xloc) {
		this.xloc = xloc;
	}

	public int getYloc() {
		return yloc;
	}

	public void setYloc(int yloc) {
		this.yloc = yloc;
	}

	public String getTypeOfAnimal() {
		return typeOfAnimal;
	}

	public void setTypeOfAnimal(String typeOfAnimal) {
		this.typeOfAnimal = typeOfAnimal;
	}

	public int getScoreEffect() {
		return scoreEffect;
	}

	public void setScoreEffect(int scoreEffect) {
		this.scoreEffect = scoreEffect;
	}

	public double getDisplayDuration() {
		return displayDuration;
	}

	public void setDisplayDuration(double displayDuration) {
		this.displayDuration = displayDuration;
	}

	public double getTimeLeftOnScreen() {
		return timeLeftOnScreen;
	}

	public void setTimeLeftOnScreen(double timeLeftOnScreen) {
		this.timeLeftOnScreen = timeLeftOnScreen;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**returns the current image in this animal's animation
	 * @return the image at images[picnum]
	 */
	public Image getImage() {
		return images[picNum];
	}

	/**sets animal's image array and image height and width.
	 * assumes all images in the array to be of equal dimensions
	 * @param images - array to set
	 */
	public void setImages(Image[] images) {
		this.images = images;
		this.imageHeight = this.images[0].getHeight(null);
		this.imageWidth = this.images[0].getWidth(null);
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public boolean isCaught() {
		return caught;
	}

	public void setCaught(boolean caught) {
		this.caught = caught;
	}

	public boolean isOffScreen() {
		return offScreen;
	}

	public void setOffScreen(boolean offScreen) {
		this.offScreen = offScreen;
	}

	public int getXdir() {
		return xdir;
	}

	public void setXdir(int xdir) {
		this.xdir = xdir;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getPicNum() {
		return picNum;
	}

	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}

	public Image[] getImages() {
		return images;
	}
	

}
