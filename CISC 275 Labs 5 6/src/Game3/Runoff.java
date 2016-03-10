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
	public String toString(){
		return "Runoff [ Row: "+row+", Front Location: "+front+", Strength: "+strength+"Health: "+health+", TicksSinceMoved: "
				+ticksSinceMoved+"]";
	}
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getFront() {
		return front;
	}

	public void setFront(int front) {
		this.front = front;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public ArrayList<Integer> getHealth() {
		return health;
	}

	public void setHealth(ArrayList<Integer> health) {
		this.health = health;
	}

	public int getTicksSinceMoved() {
		return ticksSinceMoved;
	}

	public void setTicksSinceMoved(int ticksSinceMoved) {
		this.ticksSinceMoved = ticksSinceMoved;
	}

	public ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}

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
	 * @return
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
