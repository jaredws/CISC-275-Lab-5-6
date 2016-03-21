package Game2;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResultAnimation implements Serializable{
	private static final long serialVersionUID = 202L;	
	transient private Image[] images;
	private int size;
	private boolean positiveResult; //whether the result is positive or negative
	private String filePrefix;
	private int picNum = 0;
	//private int timeInMilliseconds = 400;
	//private int interval = 2;
	private int loops = 0;
	private boolean complete = false;
	private int xloc = 0;
	private int yloc = 0;
	
	
	/**constructor - loads images
	 * @param size dimensions of the square result animation in pixels
	 * @param positive - true if result is good, false if bad
	 * @param x - x location of result
	 * @param y - y location of result
	 */
	public ResultAnimation(int size, boolean positive, int x, int y) {
		this.positiveResult = positive;
		this.size = size;
		this.images = new Image[2];
		this.xloc = x;
		this.yloc = y;
		
		if (positiveResult){
			this.filePrefix = "anim_pos_result";
		}
		else{this.filePrefix = "anim_neg_result";}
		
		try {
			
			Image i1 = ImageIO.read(new File("images/" + filePrefix + "1.png")).getScaledInstance(this.getSize(), this.getSize(), 1);
			Image i2 = ImageIO.read(new File("images/" + filePrefix + "2.png")).getScaledInstance(this.getSize(), this.getSize(), 1);			
			images[0]=i1;
			images[1]=i2;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	/**updates the picnum (frame) of the animation in intervals of 5 milliseconds
	 * @param time - the current time in milliseconds
	 */
	public void update(int time){
		if(time % 5 == 0){
			if (getPicNum() + 1 >= images.length){loops++;}
			if (loops > 2){complete = true; 
			//System.out.println("animation finished");
				}
			setPicNum((getPicNum() + 1) % images.length);
		}
	}
		
	
	//SERIALIZATION
	/**Saves resultanim object to file.
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
	 * Method to read and instantiate resultanim from file. The reverse of the serialize function
	 * @param fileName
	 * @return loaded animal object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(String fileName) {
		ResultAnimation obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (ResultAnimation)ois.readObject();
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
	
	
	
	
	//GETTERS & SETTERS
	/**
	 * Get the Current Image
	 * @return images[PictureNumber] the image in at the current Picture number in the array of Images
	 */
	public Image getCurrentImage(){
		return images[getPicNum()];
	}
	/**
	 * Get the array of Images
	 * @return images an array of Images
	 */
	public Image[] getImages() {
		return images;
	}
	/**
	 * SEt the array of Images
	 * @param images an array of Images
	 */
	public void setImages(Image[] images) {
		this.images = images;
	}
	/**
	 * Get the size of the Animation
	 * @return size as an int
	 */
	public int getSize() {
		return size;
	}
	/**
	 * Set the size of the Animation
	 * @param size as an int
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * Return whether the Animation has completed true/false
	 * @return complete boolean
	 */
	public boolean isComplete() {
		return complete;
	}
	/**
	 * Set the complete status true/false
	 * @param complete boolean
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	/**
	 * Get the x location as an int
	 * @return xlocation
	 */
	public int getXloc() {
		return xloc;
	}
	/**
	 * Get the y location as an int
	 * @return ylocation
	 */
	public int getYloc() {
		return yloc;
	}
	/**
	 * Get the current index of the picture in the array of images, an int
	 * @return picNum
	 */
	public int getPicNum() {
		return picNum;
	}
	/**
	 * Set the current index of the picture in the array of images, an int
	 * @param picNum
	 */
	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}
	/**
	 * Get the number of loops through the array of images as an int
	 * @return loops
	 */
	public int getLoops() {
		return loops;
	}
	/**
	 * Set the number of loops through the array of images, as an int
	 * @param loops
	 */
	public void setLoops(int loops) {
		this.loops = loops;
	}
	
	
}
