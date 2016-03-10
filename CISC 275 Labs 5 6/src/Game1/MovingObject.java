package Game1;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Class of all moving object;
 */
public class MovingObject implements Serializable{

private static final long serialVersionUID = 104L;
private Point position;
private int size,w,h;
protected JLabel label;

/**Constructor
 * @param x Position in x
 * @param y Position in y
 * @param size Size of the object
 */
public MovingObject(int x,int y, int size) {
	this.position = new Point(x,y);
	this.size = size;
}

public JLabel getLabel() {
	return label;
}
/**
 * Update the location of Objects
 */
public void update(){
	
}

/**
 * Add the moving object to the  
 * @param p JPanel of the game window
 * @param filename the name of picture
 */
public boolean addItem(JPanel p,String filename){
	w=p.getWidth();
	h=p.getHeight();
	label = new JLabel();
	BufferedImage image;
	ImageIcon icon=new ImageIcon();
	try {
		image = ImageIO.read(new File(filename));
		icon = new ImageIcon(image.getScaledInstance(size*image.getWidth()/image.getHeight(), size, 1));
		
	} catch(IOException e) {
		System.out.println("Read Error: " + e.getMessage());
	}
	label.setIcon(icon);
	label.setBounds(position.x, position.y, icon.getIconWidth(),size);
	
	p.add(label);
	return true;
}
/** getter for position
 * @return return the position
 */
public Point getPosition(){
	return position;
}

public void setPosition(Point position) {
	this.position = position;
}
/**
 * @return size of the object
 */
public int getSize(){
	return size;
}
/**
 * change the position of object
 * @param x location of x
 * @param y location of y
 */
public void move(int x,int y){
	this.position.move(x, y);
}

}
