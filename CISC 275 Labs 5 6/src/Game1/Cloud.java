package Game1;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Subclass of MovingObect, Cloud in the game
 */
public class Cloud extends MovingObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	int speed,bx,by;
	public Cloud(int size, int bx,int by) {
		super((int)(bx*Math.random()),(int)(by*Math.random()), size);
		this.bx=bx;
		this.by=by;
		this.speed=(int) (((Math.random()*3)+1)*Math.pow(-1, (int) (Math.random()*2)));
		// TODO Auto-generated constructor stub
	}
	/* (non-Javadoc)
	 * @see Game1.MovingObject#update()
	 */
	public void update(){
		if(this.speed+this.getPosition().x<-this.getSize()/2){
			this.getPosition().setLocation(bx+this.getSize()/2, this.getPosition().y);
		}
		else if(this.speed+this.getPosition().x>bx+this.getSize()/2){
			this.getPosition().setLocation(0-this.getSize()/2, this.getPosition().y);
		}
		else{
			this.getPosition().setLocation(this.getPosition().x+speed,this.getPosition().y);
		}
		this.getLabel().setBounds(this.getPosition().x, this.getPosition().y, 
				this.getLabel().getWidth(), this.getLabel().getHeight());
		
	}
	

}
