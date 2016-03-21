package Game3;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Handles the mussels (lives and money boosters) for game 3
 */
public class Mussel implements java.io.Serializable{
	private static final long serialVersionUID = 301L;
	private int xloc ;
	private int yloc ;
	private int stage;
	private  Image musselDrawing;
	
	
	/**
	 * Constructor for Mussels, all mussels start at stage 0
	 * @param xloc
	 * @param yloc
	 */
	public Mussel(int xloc, int yloc) {
		this.xloc	=	xloc	;
		this.yloc	=	yloc	;
		this.stage	=	0		;
		try {
			this.musselDrawing = ImageIO.read(new File("images/mussel.png"));
		}
		catch (IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
	}
	
	
	
	/**
	 * Mussels will grow if a certain time has passed since its last growth
	 * If it's at the final growth stage, does nothing
	 */
	public void grow() {
		if (getStage() < 100) {
			setStage(getStage() + 1);
		}
	}
	
	/**
	 * Getters and Setters
	 */
	/**
	 * Get the Stage as an int
	 * @return stage
	 */
	public int getStage() {
		return stage;
	}
	/**
	 * Get the x Location as an int
	 * @return xlocation
	 */
	public int getXLoc() {
		return xloc;
	}
	/**
	 * Get the y location as an int
	 * @return ylocation
	 */
	public int getYLoc() {
		return yloc;
	}
	/**
	 * Get the Mussel Image
	 * @return musselDrawing formatted
	 */
	public Image getMusselDrawing() {
		return musselDrawing.getScaledInstance((9/5)*Game3.scalor*(getStage() + 1)/101 + 1, Game3.scalor*(getStage() + 1)/101 + 1, 1);
	}
	/**
	 * Set the Stage int
	 * @param stage
	 */
	public void setStage(int stage) {
		this.stage = stage;
	}
	/**
	 * Set the x Location
	 * @param xLoc
	 */
	public void setXLoc(int xLoc) {
		this.xloc = xLoc;
	}
	/**
	 * Set the y Location
	 * @param yLoc
	 */
	public void setYLoc(int yLoc) {
		this.yloc = yLoc;
	}
	/**
	 * Set the Mussel Image
	 * @param newDrawing
	 */
	public void setMusselDrawing(Image newDrawing) {
		this.musselDrawing = newDrawing;
	}
	/**
	 * Overridden toString
	 * MUssels [Xloc: x, Yloc: y, Stage: stage]
	 */
	public String toString(){
		return "Mussels [ Xloc: "+xloc+", Yloc: "+yloc+", Stage: "+stage +"]";
	}
	
	/**
	 * Method to serialize OverallGame, which contains the other games as params
	 * So this output will contain the serialized version of every object
	 * @param obj
	 * @param fileName
	 * @throws IOException
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
	 * Method to read a game state from file and instantiate it. The reverse of the serialize function
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(String fileName) {
		Mussel obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Mussel)ois.readObject();
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
	/**
	 * This method is for creating mussels from a serialized version of mussels
	 * @param xloc
	 * @param yloc
	 * @param stage
	 * @param musselDrawing
	 */
	public Mussel(int xloc, int yloc, int stage, Image musselDrawing) {
		this.xloc = xloc;
		this.yloc = yloc;
		this.stage = stage;
		this.musselDrawing = musselDrawing;
	}
}
