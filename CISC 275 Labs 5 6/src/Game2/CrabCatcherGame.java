package Game2;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;

import Game3.Mussel;
import OverallGame.OverallGame;


/**
 * @author Dwegrzyn
 *
 */
public class CrabCatcherGame implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 200L;
	/**
	 * 
	 */
	//FIELDS
	private double time; 
	private double speed;
	private ArrayList<Animal> animals; 
	private ArrayList<ResultAnimation> resultAnims;
	private int score;
	private double gameLengthInMilliseconds = 30 * 1000; //how long is this game? (1s = 1000ms)
	private MouseAdapter mouseListener;
	private int maxAnimalsOnScreen = 16; 
	private boolean gameOver = false;
	private OverallGame bigGame;
	transient private JFrame frame;
	transient private JPanel panel;
	private Timer timer;
	transient private JPanel bigpan;
	private JLabel TS;
	transient private Image[] crabImages;
	transient private Image[] mittencrabImages;
	transient private Image[] fishImagesRight;
	transient private Image[] fishImagesLeft;
	transient private Image backgroundImage;
	transient private Image netImage;
	private static int mittencrabScoreEffect = 20;
	private static int crabScoreEffect = -15;
	private static int fishScoreEffect = -10;
	private int resultSize = 100; //default value

	//CONSTRUCTOR	
	/**
	 * @param speed - rate of gameplay
	 * @param animals - array of all animals in the mini game
	 * @param score - player's current score for this mini game
	 * @param lives -  number of lives remaining (default is 3)
	 * @param gameLength - time limit for the game
	 * @param mouseListener - handles mouse clicks, checks if user clicked animal
	 * @param gameOver - whether the game should be over (default is false)
	 * @param maxAnimalsOnScreen - the maximum number of animals that can appear onscreen
	 * @param bigGame - the overall game that this mini game is a part of
	 * @param frame - the frame the game is drawn in
	 */
	public CrabCatcherGame(double speed, ArrayList<Animal> animals,
			int score, int lives, double gameLength,
			MouseAdapter mouseListener, int maxAnimalsOnScreen,
			boolean gameOver, OverallGame bigGame, JFrame frame) {
		super();
		this.time = 0;
		this.animals = animals;
		this.resultAnims = new ArrayList<ResultAnimation>();
		this.score = score;
		this.mouseListener = mouseListener;
		this.gameOver = gameOver;
		this.bigGame = bigGame;
		this.resultSize = (int)(this.bigGame.frameHeight/9);
		this.frame = frame;
		loadAllImages();
	}

	
	
	//METHODS
	/**returns the sized image in the path images/filename
	 * scales width and height according to bigGame frame size
	 * @param filename the name of the file to be loaded
	 * @return 
	 */
	public Image loadImage(String filename) {
		Image image = null;
		try {
			image = ImageIO.read(new File("images/" + filename));
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		if (filename.equals("ocean_background.jpg")){
			image = image.getScaledInstance(bigGame.frameWidth, bigGame.frameHeight, 0);
		}
		else if (filename.equals("net.png")){
			int height = (int)(bigGame.frameHeight/3.6 + 10);
			int width = (int) (height*1.25);
			image = image.getScaledInstance(width, height, 0);
		}
		else{
			int height = (int)(bigGame.frameHeight/3.6);
			int width = (int) (height*1.25);
			image = image.getScaledInstance(width, height, 0);
			}
		return image;
	}
	
	/**
	 * loads and sets all game images
	 * initializes and fills animal animation arrays
	 */
	public void loadAllImages(){
		mittencrabImages = new Image[2];
		mittencrabImages[0] = loadImage("mittencrab1.png");	
		mittencrabImages[1] = loadImage("mittencrab2.png");	
		
		crabImages = new Image[2];
		crabImages[0] = loadImage("maincrab1.png");	
		crabImages[1] = loadImage("maincrab2.png");	
		
		fishImagesRight = new Image[2];
		fishImagesRight[0] = loadImage("fish.png");
		fishImagesRight[1] = loadImage("fish.png");
		
		
		fishImagesLeft = new Image[2];
		fishImagesLeft[0] = loadImage("fish_left.png");
		fishImagesLeft[1] = loadImage("fish_left.png");
		
		backgroundImage = loadImage("ocean_background.jpg");
		netImage = loadImage("net.png");
		
	}
	
	/**
	 * Updates the game's timed elements
	 * (checks for game over, ticks all animals, updates animations)
	 */
	public void updateGame(){
		//check if time is up -> game over
		if (time >= gameLengthInMilliseconds){
			endGame();
		}
		
		//tick all animals and reset offscreen animals
		for (int i = animals.size()-1; i >= 0; i--) {
		Animal current = animals.get(i);	
			current.onTick(this);
			if (current.isOffScreen()){
				reAddAnimal(current);
			}
		}
		
		//update anims
		Iterator<ResultAnimation> it = resultAnims.iterator();
		while(it.hasNext()){
			it.next().update((int)time);
		}
	}
	
	/**
	 * Sets the game's variables to defaults and begins the timer.
	 * Call this when the start button is pressed.
	 */
	public void startGame(){
		generateAnimals();
		System.out.println("--game length: " + this.gameLengthInMilliseconds);
		//initialize panel and set up frame
		initPanel();	
		bigpan=(JPanel) frame.getContentPane();
		frame.setContentPane(this.panel);
		frame.setVisible(true);
		timer.start();	
	}
	
	/**initializes the game's JPanel and layout
	 * establishes the paintComponent method to be later called in repaint()
	 * @return true when finished
	 */
	public boolean initPanel(){
		
		//layout and draw things
		panel = new JPanel(){
			@Override
			//note: paint is called in repaint()
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				//draw background
				g.drawImage(backgroundImage, 0, 0, null);
				//draw animals from newest to oldest (new animals will show behind ones)
				for (int i = animals.size()-1; i >= 0; i--) {
					Animal animal = animals.get(i);
						g.drawImage(animal.getImage(), animal.getXloc(), animal.getYloc(), null);
						//if caught, draw net on top
						if (animal.isCaught()){
							g.drawImage(netImage, animal.getXloc(), animal.getYloc(), null);
							reAddAnimal(animal);		
					}										
				}
				
				//Remove completed result animations
				Iterator<ResultAnimation> it = resultAnims.iterator();
				while(it.hasNext()){
					ResultAnimation current = it.next();
					if (current.isComplete()){it.remove();}
					else{
						g.drawImage(current.getCurrentImage(), current.getXloc(), current.getYloc(), null);
					}
				}
				
				//draw timer
				double x = 100 * getTime()/getGameLength();
				float green   = (float) (x > 50 ? 1-2 * (x-50)/100.0 : 1.0);
				float red = (float) (x > 50 ? 1.0 : 2 * x/100.0);
				Color timerColor = new Color(red, green, 0);
				g.setColor(timerColor);
				g.fillArc(0, 0, OverallGame.frameWidth/8, OverallGame.frameWidth/8, 90, 360-(int)(360.0*getTime()/getGameLength()));
				
			}
		};
		
		panel.setLayout(null);
		//return button
		JButton Button = new JButton("Return");
		Button.setBounds(0, 600, 100, 50);
		Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endGame();
			}});
		panel.add(Button);
		
		
		//Score
		TS = new JLabel("Score: "+this.score, SwingConstants.CENTER);
		TS.setOpaque(true);
		TS.setBackground(new Color(223, 196, 99));
		TS.setBounds((int)(OverallGame.frameWidth/7), 0, (int)(bigGame.frameWidth/7), (int)(bigGame.frameWidth/16));
		TS.setFont(new Font("Serif", Font.PLAIN, 30));
		
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		TS.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		panel.add(TS);
		
	
		//declare timer
		final int timerTimeInMilliSeconds = 33;
	    timer = new javax.swing.Timer(timerTimeInMilliSeconds, new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		time += timerTimeInMilliSeconds;
	    		updateGame();
	    		updatePanel();
			}
	    });
		
		return true;
	}
	
	/**called on each tick, this updates panel visuals (text and graphics)
	 * @return
	 */
	public boolean updatePanel(){
		//visual updates
		TS.setText("Score: "+this.score);	
		frame.repaint();	
		return true;		
	}
	
	/**
	 * Ends this mini game and saves score to big game.
	 */
	public void endGame(){
		//send score to send to big game
		bigGame.setOverallScore(bigGame.getOverallScore() + score);
		bigGame.getGameWindow().getCurrentScore().setText("Overall Score: " + bigGame.getOverallScore());
		//stop timer
		timer.stop();
		//set big game running to true
		bigGame.setGamesRunning(0);
		bigGame.getGamesComplete()[1] = true;
		//return to big game
		frame.setContentPane(bigpan);
		frame.getContentPane().setVisible(true);
	}
	
	/**
	 * Constructs the max animals to place on screen 
	 * sets properties randomly for each animal
	 */
	public void generateAnimals(){
		//constructs the max number of animals to place on screen
		animals = new ArrayList<Animal>();
		for (int i=0; i < maxAnimalsOnScreen; i++){			
			animals.add(makeRandomAnimal());
		}
	}
	
	
	/** Makes a random animal with default values (visible, offscreen, uncaught).
	 * Probability of animal type is preset
	 * @return a random animal
	 */
	public Animal makeRandomAnimal(){
		Random r = new Random();
		int duration = 7 - r.nextInt(5);
		boolean visible = true;
		
		int typenum = r.nextInt(100);
		String type = "crab";
		int effect = crabScoreEffect;
		Image[] img = crabImages;	
		
		if (typenum >= 0 && typenum <= 55){
			type = "mittencrab"; 
			effect = mittencrabScoreEffect; 
			img = mittencrabImages;
			}
		
		else if (typenum < 75){
			type = "fish"; 
			effect = fishScoreEffect;
			img = fishImagesRight;
			}
		
		Animal animal = new Animal(0, 0, type, effect, duration, visible);
		animal.setImages(img); //note: also sets image height and width
		animal.setRandomXDir();
		//make sure fish goes in correct direction
		if(animal.getXdir() == -1 && animal.getTypeOfAnimal().equals("fish")){
			animal.setImages(fishImagesLeft);
		}
		setOffScreenLoc(animal);
		return animal;
	}
	
	
	/** Add animal to animal array (for testing purposes only)
	 * @param animal - the animal to be added to the game's list
	 */
	public void addAnimal(Animal animal){
		//for testing purposesonly
		//adds animal to game's animal list
		animals.add(animal);
	}
	
	
	////////////////////////////////////////////////////////////////////
	/**Sets the animal beyond the left or right border according to its xdir
	 * @param newAnimal
	 * @return
	 */
	public Animal setOffScreenLoc(Animal newAnimal){
		Random r = new Random();
		//default = moving right, put on left border
		int xloc = 0 - newAnimal.imageWidth;
		int yloc = r.nextInt(bigGame.frameHeight - newAnimal.imageHeight);
		
		//if moving left, put on right border
		if (newAnimal.getXdir() == -1){
			xloc = bigGame.frameWidth;
		}
		
		newAnimal.setXloc(xloc);
		newAnimal.setYloc(yloc);
		return newAnimal;
	}
	
	/**Checks if the user clicked an animal and update game accordingly
	 * @param event the mouse event being processed
	 */
	public void onClick(MouseEvent event){
		//if getAnimalClicked() returns an Animal,
		//System.out.println("YOU CLICKED.");
		Animal animal = getAnimalClicked(event.getX(), event.getY());
		System.out.println("GETANIMALCLICKED RETURNED: " + animal);
		//if (animal != null && animal.isVisible()){ //*****changed this//
		if (animal!=null && !animal.isCaught()){
			//hide animal and add animal's scoreEffect to game score (not going below 0)
			System.out.println("!!!you clicked on a " + animal.getTypeOfAnimal());
			animal.setCaught(true);
			updateScore(animal.getScoreEffect());
			boolean positive = animal.getScoreEffect() > 0;
			this.resultAnims.add(new ResultAnimation(resultSize, positive, event.getX(), event.getY()));
			updatePanel(); //repaint the frame and display score change
			System.out.println("--> score changed by " + animal.getScoreEffect());	
			}
		
		else{
			System.out.println(".......you missed!");
			System.out.println(animals);
			System.out.println("click was at ("+ event.getX() + ", " + event.getY()+ ")");
		}
		
	}
	
	/**removes animal from list and re-adds it with default values in a new offscreen location
	 * @param animal - animal to be readded
	 */
	public void reAddAnimal(Animal animal){
		animal.setVisible(true);
		animals.remove(animal);
		animal.regenerateAnimal(bigGame.frameWidth, bigGame.frameHeight); //regenerate as a new random animal
		animal.setCaught(false);
		animal.setOffScreen(false);
		animal.setRandomXDir();
		if(animal.getTypeOfAnimal().equals("fish")){
			if(animal.getXdir() == 1){
				animal.setImages(fishImagesRight);
			}
			else{animal.setImages(fishImagesLeft);}
		}
		animal = setOffScreenLoc(animal);
		animals.add(animal);
	}
	
	
	/**Method for testing click events
	 * @param x - x location of click
	 * @param y - y location of click
	 */
	public void onClickTest(int x, int y){	
		//if getAnimalClicked() returns an Animal,
		//System.out.println("YOU CLICKED.");
		Animal animal = getAnimalClicked(x, y);
		if (animal != null && !animal.isCaught()){
			//hide animal and add animal's scoreEffect to game score (not going below 0)
			System.out.println("!!!you clicked on a " + animal.getTypeOfAnimal());
			animal.setCaught(true);
			updateScore(animal.getScoreEffect());
			boolean positive = animal.getScoreEffect() > 0;
			this.resultAnims.add(new ResultAnimation(resultSize, positive, x, y));
			System.out.println("--> score changed by " + animal.getScoreEffect());
			}
		else{
			System.out.println(".......you missed!");
			System.out.println(animals);
			System.out.println("click was at ("+ x + ", " + y + ")");
		}
	}
	
	/** returns animal at the given x y coordinates if there is one
	 * @param x the x coordinate of the mouse click
	 * @param y the y coordinate of the mouse click
	 * @return the animal clicked (null if no animal clicked)
	 */
	public Animal getAnimalClicked(int x, int y){
		//return the animal if user clicked animal, else return null;
		Animal clicked = null;
		//oldest Animals are drawn on top and in the front of the list
		for (int i=0; i < animals.size(); i++){
			Animal animal = animals.get(i);
			if(x <= animal.getXloc() + animal.getImageWidth() && x >= animal.getXloc()
					&& y <= animal.getYloc() + animal.getImageHeight()
					&& y >= animal.getYloc()){
				clicked = animal;
				//System.out.println("!!!you clicked on a " + animal.getTypeOfAnimal());
				break;
			}
		}
		return clicked;
	}

	
	/**
	 * updates score with given positive/negative effect, making sure score does not go below zero
	 * @param effect - the number to be added to the score
	 */
	public void updateScore(int effect){
		if (score + effect >= 0){
			score += effect;
		}
		else{
			setScore(0);
		}
	}
	
	
	@Override
	public String toString() {
		return "CrabCatcherGame [time=" + time + ", speed=" + speed
				+ ", animals=" + animals.toString() + ", score=" + score
				+ ", gameLength=" + gameLengthInMilliseconds
				+ ", maxAnimalsOnScreen=" + maxAnimalsOnScreen + ", gameOver="
				+ gameOver + ", bigGame=" + bigGame + ", timer=" + timer + "]";
	}
	
	
	
	//SERIALIZATION
	/**
	 * Method to serialize Crab Catcher Game
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
		CrabCatcherGame obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (CrabCatcherGame)ois.readObject();
			ois.close();
		}
		catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		catch (ClassNotFoundException e){
			System.out.println("Read Error: " + e.getMessage());
		}
		
		obj.loadAllImages();
		return obj;
	}
	
	
	//GETTERS & SETTERS
	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}


	public double getSpeed() {
		return speed;
	}


	public void setSpeed(double speed) {
		this.speed = speed;
	}


	public ArrayList<Animal> getAnimals() {
		return animals;
	}


	public void setAnimals(ArrayList<Animal> animals) {
		this.animals = animals;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}

	public double getGameLength() {
		return gameLengthInMilliseconds;
	}


	public void setGameLength(double gameLength) {
		this.gameLengthInMilliseconds = gameLength;
	}


	public MouseListener getMouseListener() {
		return mouseListener;
	}


	public void setMouseListener(MouseAdapter mouseListener) {
		this.mouseListener = mouseListener;
	}


	public int getMaxAnimalsOnScreen() {
		return maxAnimalsOnScreen;
	}


	public void setMaxAnimalsOnScreen(int maxAnimalsOnScreen) {
		this.maxAnimalsOnScreen = maxAnimalsOnScreen;
	}


	public boolean isGameOver() {
		return gameOver;
	}


	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}


	public OverallGame getBigGame() {
		return bigGame;
	}


	public void setBigGame(OverallGame bigGame) {
		this.bigGame = bigGame;
	}



	public JFrame getFrame() {
		return frame;
	}



	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}



	public ArrayList<ResultAnimation> getResultAnims() {
		return resultAnims;
	}



	public void setResultAnims(ArrayList<ResultAnimation> resultAnims) {
		this.resultAnims = resultAnims;
	}
	

}
