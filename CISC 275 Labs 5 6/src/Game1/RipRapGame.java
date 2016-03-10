package Game1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.plaf.ProgressBarUI;

import Game2.CrabCatcherGame;
import OverallGame.OverallGame;

import java.util.Random;


/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Main class of game2
 */

public class RipRapGame implements java.io.Serializable{
	 

	

	public Crab getCrab() {return crab;}
	public void setCrab(Crab crab) {this.crab = crab;}
	public JumpingBar getJumpingBar() {	return jumpingBar;}
	public void setJumpingBar(JumpingBar jumpingBar) {this.jumpingBar = jumpingBar;}
	public Sun getSun() {return sun;}
	public void setSun(Sun sun) {this.sun = sun;}
	public int getScore(){return score;}
	public int getTime() {return currtime;}
	public OverallGame getBigGame() {	return bigGame;}
	public void setBigGame(OverallGame bigGame) {	this.bigGame= bigGame;}
	public String toString()
    {
        return "[RipRapGame: score=" + score + 
            " time=" + time +
            " starttime=" + starttime +
            " crab=" + crab +
            " JumpingBar="+ jumpingBar +
            " objects="+ objects +
            "scalor" + scalor+
            "nx2" + mark+
            "nx"+nx +
            "x"+x +
            "sun"+sun +
            "bigGame"+ bigGame +
            "stone"+stone +
            "panel"+panel +
            "TS"+TS +
            "]";
        
    }  
	
	int score;//current game score
	int time,currtime;//total time and current time
	long starttime;//time when game start
	Crab crab;
	JumpingBar jumpingBar;
	ArrayList<MovingObject> objects;
	public  static final int scalor = (OverallGame.frameHeight < OverallGame.frameWidth) ? OverallGame.frameHeight/8 : OverallGame.frameWidth/8;
	int nx2,mark,nx,x;
	Sun sun;
	private OverallGame bigGame;
	Stone stone;
	JPanel panel;
	javax.swing.Timer timer;
	JPanel bigpan;
	JFrame frame;
	JLabel TS;//time&score
	//Constructor
	/**
	 * @param time time limitation
	 * @param bigGame the overall lGame that consist of this game
	 * @param frame frame of the game window
	 */
	public RipRapGame(int time,OverallGame bigGame,JFrame frame) {
		this.time = time+10;
		this.bigGame = bigGame;
		this.starttime=System.currentTimeMillis();
		this.frame=frame;
		this.jumpingBar=new JumpingBar(20, 30, this);
		this.objects=new ArrayList<MovingObject>();
		this.nx2 = (int) (this.frame.getWidth()*0.9);
		this.mark = (int) (this.frame.getWidth()*0.9);
		initPanel();
	}

	/**
	 * Add points while game goes on
	 * lose points if fail to climb over a obstacle
	 * @param s score that is added to the score
	 */
	public void addScore(int s){
		score+=s;
	}


	
	

	/**
	 * initialize the game with RipRap wall and obstacles randomly displaced

	 * @return
	 */
	public boolean initGame(){
		stone=new Stone(this.panel.getWidth(),this.panel.getHeight(),this.panel.getWidth()/15);
		stone.addItem(panel, "images/rock.png");
		objects.add(stone);
		crab=new Crab((int)(this.panel.getWidth()*0.2),(int)(this.panel.getHeight()*0.8),this.panel.getWidth()/13);
		crab.addItem(panel, "images/maincrab1.png");
		objects.add(crab);
		for(int i=1;i<4;i++){
			Cloud cloud = new Cloud(this.panel.getWidth()/10,this.panel.getWidth(),(int)(this.panel.getHeight()*0.2));
			cloud.addItem(panel, "images/cloud"+i+".png");
			objects.add(cloud);
		}
		Sun sun=new Sun(200);
		sun.addItem(panel);
		objects.add(sun);
		return true;
	}
	/**
	 * init the jpanel
	 * @return true
	 */
	public boolean initPanel(){
		try {
			final BufferedImage image = ImageIO.read(new File("images/rockwall.jpg"));
			final BufferedImage a = ImageIO.read(new File("images/game1background.jpg"));
			final Image b=a.getSubimage(0,0,a.getWidth(), a.getHeight()-200).getScaledInstance(this.frame.getContentPane().getHeight()*1280/520, this.frame.getContentPane().getHeight(), 1);
			panel=new JPanel(){
	            @Override
	            
	            protected void paintComponent(Graphics g) {
	            	super.paintComponent(g);
	            	// create the transform, note that the transformations happen
	                // in reversed order (so check them backwards)
	                AffineTransform at = new AffineTransform();

	                // 4. translate it to the center of the component
	                at.translate(getWidth()/2, crab.getPosition().getY()*0.8+image.getHeight());

	                // 3. do the actual rotation
	                at.rotate(Math.PI/-19);

	                // 2. just a scale because this image is big
	                at.scale(1.2, 1.2);

	                // 1. translate the object so that you rotate it around the 
	                //    center (easier :))
	                at.translate(-image.getWidth()/2, -image.getHeight()/2);

	                // draw the image
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.drawImage(b, mark-nx2,0, null);
	                g2d.drawImage(b, mark-nx2+b.getWidth(null),0, null);
	                g2d.drawImage(image, at, null);
	                
	                double t=(System.currentTimeMillis()-starttime)/1000.;
	                double x = 100 * t/time;
					float green   = (float) (x > 50 ? 1-2 * (x-50)/100.0 : 1.0);
					float red = (float) (x > 50 ? 1.0 : 2 * x/100.0);
					Color timerColor = new Color(red, green, 0);
					g2d.setColor(timerColor);
					g2d.fillArc(scalor/1, scalor/2, scalor/1, scalor/1, 90, (int)(360-360*t/time));
					//g2d.fillArc(scalor/1, scalor/2, scalor, scalor, 90, 360-360*(300-(int)(getTime()*10.0))/300);
					
	            };
			};
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		
		panel.setLayout(null);
		panel.setBackground(new Color(135, 206, 235));
		panel.setOpaque(true);
		JButton Button = new JButton("Return");
		Button.setBounds(0, 600, 100, 50);
		Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endGame();
			}});
		panel.add(Button);
		

		int timerTimeInMilliSeconds = 20;
	    timer = new javax.swing.Timer(timerTimeInMilliSeconds, new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		updateTime();
	    		updatePanel();
	    		jumpingBar.update(panel);
	    		updateMap();
	    		nx2+=1;
	    		
	    		if(getTime()<=0){
	    			endGame();
	    		}		
			}
	    });

		return true;
	}
	/**
	 * Run this for the beginning
	 * @return ture
	 */
	public boolean firstRunPanel(){
		TS = new JLabel("Score:"+this.score);
		//Time:"+this.currtime+"    
		TS.setBounds(0,10,frame.getWidth(),30);
		TS.setFont(new Font("Serif", Font.PLAIN, 30));
		panel.add(TS);
		jumpingBar.makeLabels(panel);
		initGame();

		
		return true;
	}
	/**
	 * update the score;
	 * @return
	 */
	public boolean updatePanel(){
		TS.setText("Score:"+this.score);
		//"Time:"+this.currtime+"    
		return true;
		
	}
	/**
	 * Map updates while playing
	 * Time period of calling this function will vary based on difficulty
	 * Will also affect the size of Obstacles and quantity.
	 */
	public void updateMap(){
		for(MovingObject m:objects){
			m.update();
		}
	}
	
	
	

	


	
	
	/**
	 * run the game
	 */
	public void run() {
		
		bigpan=(JPanel) frame.getContentPane();
		this.panel.setSize(frame.getContentPane().getSize());
		frame.setContentPane(this.panel);
		firstRunPanel();
		timer.start();
		
	}
	/**
	 * Time updates based on real time spending
	 */
	public void updateTime(){
		long t=System.currentTimeMillis();
		this.currtime=(int) (this.time+(this.starttime-t)/1000);
	}
	
	
	
	/**
	 * Ends game and sends score to big game.
	 */
	public void endGame(){
		timer.stop();
		frame.setContentPane(bigpan);
		frame.getContentPane().setVisible(true);
		this.bigGame.setOverallScore(this.bigGame.getOverallScore()+this.score);
		this.bigGame.getGameWindow().getCurrentScore().setText("Overall Score: "+ this.bigGame.getOverallScore());
		this.getBigGame().setGamesRunning(0);
		this.getBigGame().getGamesComplete()[0]=true;
		
	}
	
	/**
	 * Method to serialize OverallGame, which contains the other games as params
	 * So this output will contain the serialized version of every object
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
	 * Method to read a game state from file and instantiate it. The reverse of the serialize function
	 * @param fileName
	 * @return
	 
	 */
	public static Object deserialize(String fileName) {
		OverallGame obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (OverallGame)ois.readObject();
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
	
	
	

}
