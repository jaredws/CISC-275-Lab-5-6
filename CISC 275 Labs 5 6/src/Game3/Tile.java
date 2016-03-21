package Game3;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

public class Tile {
	int 	row ;
	int 	col ;
	Image   image;
	
	
	/**
	 * Tile constructor
	 * @param row
	 * @param col
	 */
	public Tile(int row, int col) {
		super();
		this.row = row;
		this.col = col;
		this.image = null;
		try {
			this.image = ImageIO.read(new File("images/sand.png")).getScaledInstance(Game3.scalor, Game3.scalor, 1);
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
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
	 * Get the Column int
	 * @return col
	 */
	public int getCol() {
		return col;
	}
	/**
	 * Set the Column int
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}
	/**
	 * Get the current Image 
	 * @return image
	 */
	public Image getImage() {
		return image;
	}
	/**
	 * Set the current Image
	 * @param image
	 */
	public void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * Prints a representation of a tile for the purpose of serializable
	 */
	public String toString() {
		return "Row: " + getRow() + "\nCol:  " + getCol();
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
		Tile obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Tile)ois.readObject();
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
	public Tile(int row, int col, Image image) {
		super();
		this.row = row;
		this.col = col;
		this.image = image;
	}
	
	
}
