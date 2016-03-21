package Game3;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;


/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Handles the runoff (enemies) for game 3
 */
public class Runoff implements java.io.Serializable{
	private static final long serialVersionUID = 303L;
	int row ;
	int length ;
	int front ; 
	int strength ;
	ArrayList<Integer> health ;
	int ticksSinceMoved ;
	ArrayList<Image> images;
	public boolean hasDied	=	false;
	
	/**
	 * Constructor
	 * Default strength 2
	 * Default health   10
	 * @param row - the row to place the runoff in
	 * @param col - the column to place the runoff in
	 */
	public Runoff(int row, int front) {
		this.row = row ;
		this.front = front ;
		this.length = 1 ;
		this.strength	=	1	;
		this.health		=	new ArrayList<Integer>()	; health.add(50);
		this.ticksSinceMoved = 0;
		this.images = new ArrayList<Image>();
		try {
			this.images.add(ImageIO.read(new File("images/runoff.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1));
			
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
	} ;
	
	/**
	 * Runoff are equal if they are in the same row and column
	 * @param other - the runoff to be compared to
	 */
	public boolean equals (Runoff other) {
		return (row == other.getRow() && front == other.getFront());
	}
	
	/**
	 * Adds a segment to the runoff if it is within a certain length and hasn't lost its head yet
	 */
	public void grow() {
		if (getLength() < 5 && getLength() !=0){
			if (getHealth().get(0) > 0 && hasDied == false) {
				setLength(getLength()+1) ;
				getHealth().add(150);
				try {
					this.images.add(ImageIO.read(new File("images/runoff.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1));
					
				} catch(IOException e) {
					System.out.println("Read Error: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * removes the front segment of the runoff and make sure it can't grow anymore
	 */
	public void removeFront() {
		getHealth().remove(0);
		setLength(getLength()-1);
		getImages().remove(0);
		setFront(getFront()+Game3.scalor);
		hasDied	=	true;
	}

	/**
	 * Getters and Setters
	 */
	/**
	 * Overridden toString
	 * Runoff [ Row: row, Front Location: front, Strength: strength, Health: health, TicksSinceMoved: ticksSinceMoved]
	 */
	public String toString(){
		return "Runoff [ Row: "+row+", Front Location: "+front+", Strength: "+strength+", Health: "+health+", TicksSinceMoved: "
				+ticksSinceMoved+"]";
	}
	/**
	 * Get the Row int
	 * @return row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * Set the Row int
	 * @param row
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * Get the Length int
	 * @return length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * Set the Length int
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * Get the Front int
	 * @return front
	 */
	public int getFront() {
		return front;
	}
	/**
	 * Set the Front int
	 * @param front
	 */
	public void setFront(int front) {
		this.front = front;
	}
	/**
	 * Get the Strength int
	 * @return strength
	 */
	public int getStrength() {
		return strength;
	}
	/**
	 * Set the Strength int
	 * @param strength
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	/**
	 * Get the Health ArrayList of Integers
	 * @return health
	 */
	public ArrayList<Integer> getHealth() {
		return health;
	}
	/**
	 * Set the Health ArrayList of Integers
	 * @param health
	 */
	public void setHealth(ArrayList<Integer> health) {
		this.health = health;
	}
	/**
	 * Get the Ticks Since Moved int
	 * @return ticksSinceMoved
	 */
	public int getTicksSinceMoved() {
		return ticksSinceMoved;
	}
	/**
	 * Set the Ticks Since Moved int
	 * @param ticksSinceMoved
	 */
	public void setTicksSinceMoved(int ticksSinceMoved) {
		this.ticksSinceMoved = ticksSinceMoved;
	}
	/**
	 * Get the ArrayList of Images
	 * @return images
	 */
	public ArrayList<Image> getImages() {
		return images;
	}
	/**
	 * Set ArrayList of Images
	 * @param images
	 */
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	/**
	 * Get the SerialVersionUID
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return deserialized Object
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserialize(String fileName) {
		Runoff obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Runoff)ois.readObject();
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
	 * Runoff constructor 
	 * @param row
	 * @param length
	 * @param front
	 * @param strength
	 * @param health
	 * @param ticksSinceMoved
	 * @param images
	 * @param hasDied
	 */
	public Runoff(int row, int length, int front, int strength, ArrayList<Integer> health, int ticksSinceMoved,
			ArrayList<Image> images, boolean hasDied) {
		super();
		this.row = row;
		this.length = length;
		this.front = front;
		this.strength = strength;
		this.health = health;
		this.ticksSinceMoved = ticksSinceMoved;
		this.images = images;
		this.hasDied = hasDied;
	}
	
	
	
}
