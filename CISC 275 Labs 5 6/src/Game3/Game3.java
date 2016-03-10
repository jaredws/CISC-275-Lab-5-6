package Game3;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

import OverallGame.OverallGame;

/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * The Game Handler for the final game
 * This will handle all logic, Input and Graphics for the final game
 */
public class Game3 implements java.io.Serializable{
	private static final long serialVersionUID = 300L;
	public  static final int scalor = (OverallGame.frameHeight < OverallGame.frameWidth) ? OverallGame.frameHeight/8 : OverallGame.frameWidth/8;
	public 	static final int xOffset= (OverallGame.frameWidth/3);
	public 	static final int yOffset= (OverallGame.frameHeight/6);
	private double 	time 	;
	private int 	score	;
	private int 	money   ;
	private	int		tickCount;
	private ArrayList<Plant> 	plants ;
	private ArrayList<Runoff> 	enemies ;
	private ArrayList<Mussel> 	mussels ;
	private ArrayList<Tile> 	tiles ;
	private boolean gameRunning ;
	private boolean gameOver   ;
	private OverallGame bigGame;
	private JPanel gamePanel ;
	private JPanel bigGamePanel;
	private JFrame gameFrame;
	private JLabel timeAndScore;
	private long startTime ;
	private Timer timer;
	private static Image background;
	private JPopupMenu menu;
	private JProgressBar timeBar;
	private ArrayList<JLabel> coins;
	private JLabel	totalCoin;
	private JLabel pipes;
	public	Image	endImage;
	private JMenuItem grass;
	private JMenuItem mangrove;
	private ActionListener grassListen;
	private ActionListener mangroveListen;
	
	
	
	/**
	 * Game Constructor
	 * Produces the initial game state 
	 * (time = 40.0, score = 0, money = 900, no plants or runoff, 4 Mussels in pseudorandom positions)
	 * @param bigGame - The handler for the entire game
	 */
	public Game3(OverallGame bigGame) {
		this.time	=	40.0	;
		this.score	=	0	;
		this.money	=	900	;
		this.plants	=		new ArrayList<Plant>();
		this.enemies	=	new ArrayList<Runoff>();
		this.mussels	=	new ArrayList<Mussel>();
		this.tiles	= 		new ArrayList<Tile>() ; 
		for (int i = 0 ; i < 28 ; i++) {
			this.tiles.add(new Tile(i/7, i%7));
		}
		this.gameRunning	=	true;
		this.gameOver	=	false;
		this.bigGame	=	bigGame;
		this.gameFrame 		=	bigGame.getGameWindow().getFrame();
		addMussel();	addMussel();
		addMussel();	addMussel();
		this.startTime 	= System.currentTimeMillis();
		this.tickCount = 0;
		initPanel(gameFrame);
		timer.start();
		this.menu = new JPopupMenu();
		this.grass = new JMenuItem(new ImageIcon("images/GrassIcon.png"));
		this.mangrove = new JMenuItem(new ImageIcon("images/mangroveIcon.png"));
		this.totalCoin = null;
		this.pipes = null;
		this.grassListen = null;
		this.mangroveListen = null;
		Game3.background = null;
		this.coins = new ArrayList<JLabel>();
		addMoney(0);
		try {
			Game3.background = ImageIO.read(new File("images/game3Background.png")).getScaledInstance(gameFrame.getWidth(), gameFrame.getHeight(), 1);
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		try {
			this.endImage = ImageIO.read(new File("images/Ending.png")).getScaledInstance(gameFrame.getWidth(), gameFrame.getHeight(), 1);
		} catch(IOException e) {
			System.out.println("Read Error: " + e.getMessage());
		}
		
		
	}

	/**
	 * Creates the game panel and sets up the timer
	 * Won't be tested as this is basically the view method plus a constructor
	 * @param frame
	 */
	public void initPanel(JFrame frame) {
		this.bigGamePanel	= (JPanel)frame.getContentPane();
		this.gamePanel		= new JPanel() {
			private static final long serialVersionUID = 310L;
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Game3.getBackground(), 0, 0, null);
				for (Mussel current : getMussels()) {
					g.drawImage(current.getMusselDrawing(), current.getXLoc(), current.getYLoc(), null);
				}
				for (Tile current : getTiles()) {
					if (current instanceof Plant) {
						g.drawImage(current.getImage(), current.getCol()*(scalor) + xOffset, current.getRow()*(scalor) + yOffset, null);
					}
					else  {
						g.drawImage(current.getImage(), current.getCol()*(scalor) + xOffset, current.getRow()*(scalor) + yOffset, null);
					}
				}
				for (Runoff current : getEnemies()) {
					for(int i = 0 ; i < current.getImages().size(); i++) {
						g.drawImage(current.getImages().get(i), current.getFront() + scalor*i, current.getRow()*(scalor) + yOffset, null);
					}
				}
				double x = 100.0 * ((400.0-10.0*getTime())/400.0);
				float green   = (float) (x > 50 ? 1-2 * (x-50)/100.0 : 1.0);
				if (green<0) {green=0;}
				float red = (float) (x > 50 ? 1.0 : 2 * x/100.0);
				Color timerColor = new Color(red, green, 0);
				g.setColor(timerColor);
				g.fillArc(scalor/4, scalor/4, scalor, scalor, 90, 360-360*(400-(int)(getTime()*10.0))/400);
				if (getTime() < 0) {
					timeAndScore.setVisible(false);
					if(totalCoin != null) {
						totalCoin.setVisible(false);
					}
					if (pipes != null) {
						pipes.setVisible(false);
					}
					g.drawImage(endImage, 0, 0, OverallGame.frameWidth, OverallGame.frameHeight, getGameFrame());
				}
			}
		};
		gamePanel.setLayout(null);
		//return button
		JButton Button = new JButton("Return");
		Button.setBounds(OverallGame.frameWidth-scalor, 0, scalor, scalor);
		Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				endGame("highScores.txt");
			}});
		
		
		
		gamePanel.add(Button);
		timeAndScore = new JLabel("Score:"+getScore());
		timeAndScore.setBounds(4*scalor/3,0,2*scalor,scalor);
		timeAndScore.setFont(new Font("Serif", Font.PLAIN, 30));
		timeAndScore.setBackground(new Color(223, 196, 99));
		Border raisedbevel = BorderFactory.createRaisedBevelBorder();
		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		timeAndScore.setBorder(BorderFactory.createCompoundBorder(raisedbevel, loweredbevel));
		timeAndScore.setOpaque(true);
		gamePanel.add(timeAndScore);
		final int timerInterval = 33;
		
		for(int i=1;i<5;i++){
		ImageIcon pipeic = new ImageIcon("images/Pipe.png");
		pipes = new JLabel(pipeic);
		pipes.setBounds(OverallGame.frameWidth-pipeic.getIconWidth(),i*scalor, pipeic.getIconWidth() ,pipeic.getIconHeight());
		gamePanel.add(pipes);}
		
		timer = new Timer(timerInterval, new ActionListener(){
	    	public void actionPerformed(ActionEvent e) {
	    		setTime(getTime() - (double)(timerInterval)/1000);
	    		update();
	    		if (getTime() > 0) {
		    		moveRunoff();
		    		setTickCount((getTickCount()+1)%500);
	    		}
			}
	    });
		
		
		gameFrame.setContentPane(gamePanel);
		addRunoff();
	}
	
	/**
	 * Updates the game state and checks for player input
	 * This includes the time remaining, character actions and movement, and updating the score and money
	 */
	public void update() {
		if (getTime() > 0) {
			if (getTime() % 10 < 0.1) {
				addScore(10);
			}
			for (Mussel current : getMussels()) {
				current.grow();
			}
			if (getTickCount() % 10 == 0) {
				timeAndScore.setText("Score:"+getScore());
				
				
			}
			if (getTickCount() % 6 == 0) {
				Random rand = new Random();
				if (rand.nextInt(10) > 8) {
					addRunoff();
				}
			}
		}
		gamePanel.repaint();
		gameFrame.setVisible(true);
		if(getTime()<=-5){
			endGame("highScores.txt");
		}
		
	    
		
	}
	
	/**
	 * The player can click on the button to exit the game OR
	 * Click on a tile and the method will prompt the player for which plant they want to place
	 * If the player clicks on a plant to buy, this will prompt the player to place the plant
	 * If the player clicks on a Mussel, the mussel is harvested for money and a new mussel appears
	 * If the player clicks on the exit button, the game will pause and prompt them to exit
	 * WITHOUT sending the score or setting the game complete boolean to true
	 * @param e - The location and nature of the user's click
	 */
	public void onClick(MouseEvent e) {
		System.out.println("yooooo");
		if (timer.isRunning() == false) {
			timer.start();
		}
		int xLoc = e.getX();
		int yLoc = e.getY();
		Mussel removal = null;
		if (xLoc < xOffset) {
			for (Mussel current : getMussels()) {
				if (current.getStage() ==  100) {
					if ((xLoc > current.getXLoc() && xLoc < current.getXLoc() + (18/5)*scalor) &&
						(yLoc > current.getYLoc() && yLoc < current.getYLoc() + 2*scalor)) {
						addScore(50); addMoney(100);
						removal = current;
					}
				}
			}
			if (removal != null) {
				addMussel();
				getMussels().remove(removal);
			}
		}
		else if (getMoney() >= 100 && xLoc < xOffset + 7*scalor && yLoc > yOffset && yLoc < yOffset + 4*scalor){
			menuRegen();
			menu.setVisible(true);
			final int row = (yLoc -	yOffset)	/scalor ;
			final int col = (xLoc - xOffset)	/scalor	;
			System.out.println("Row: " + row + "  Col: " + col);
			timer.stop();
			menu.setLocation(xLoc, yLoc);
			menu.add(grass);
			menu.add(mangrove);
			menu.show(e.getComponent(), xLoc, yLoc);
			if(grassListen == null) {
				grass.removeActionListener(grassListen);
			}
			grassListen	=	new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(getTiles().get(7*row+col)instanceof Plant){
						grass.removeActionListener(this);
					}
					else {
						if(getMoney()>=100){
							addMoney(-100);
							addPlant(row,col,"Grass");
							timer.start();}
						else{ timer.start();}
					}
				}
			};
			grass.addActionListener(grassListen);
			if (mangroveListen != null) {
				mangrove.removeActionListener(mangroveListen);
			}
			mangroveListen	=	new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (getTiles().get(7*row+col)instanceof Plant) {
						mangrove.removeActionListener(this);
					}
					else {
						if(getMoney()>=200){
							addMoney(-200);
							addPlant(row,col,"Mangrove");
							timer.start();}
						else{ timer.start();}
					}
				}
			};	
		}
		else {
			timer.start();
			menu.setVisible(false);
		}
		mangrove.addActionListener(mangroveListen);
		
	}
	/**
	 * Regenerates the Menu for buying plants once a new click has been made
	 * wUsed to handle errors received previously involving planting
	 * plants in every clicked location 
	 */
	public  void menuRegen() {
		menu.remove(grass);
		menu.remove(mangrove);
		grass = new JMenuItem(new ImageIcon("images/GrassIcon.png"));
		mangrove = new JMenuItem(new ImageIcon("images/mangroveIcon.png"));
		menu.add(grass);
		menu.add(mangrove);
		
	}
	/**
	 * Adds a plant to the current game from a player choice in a menu screen
	 * Plants can only be bought if the player has enough money
	 * the args will be passed by the useMenu method
	 * @param row - the row of the plant to be made
	 * @param col - the col of the plant to be made
	 * @param type - the type of plant to be placed
	 */
	public void addPlant(int row, int col, String type) {
		Plant plant = new Plant(row, col, type);
		getPlants().add(plant);
		getTiles().set(7*row+col, plant);
	}
	
	/**
	 *  Adds some runoff to the game at a random point
	 *  this is called periodically based on difficulty
	 *  update will tell the function where to place the runoff
	 *  @param row - the row of the runoff to be made
	 *  @param col - the column of the runoff to be made
	 */
	public void addRunoff() {
		if	(getEnemies().size()	<	4)	{
			Random rand = new Random() ;
			int row = rand.nextInt(4);
			for (int i = 0 ; i < getEnemies().size() ; i++) {
				if (row == getEnemies().get(i).getRow()) {
					row = rand.nextInt(4);
					i = -1;
				}
			}
			enemies.add(new Runoff(row, 6*scalor + xOffset)) ;
		}
		
	}
	
	
	/**
	 * Moves each runoff if some time has passed (3s right now)
	 * If the runoff reaches the end it kills a mussel
	 * If the runoff reaches a plant it fights the plant
	 */
	public void moveRunoff() {
		Iterator<Runoff> it = getEnemies().iterator();
		Runoff removal = null;
		while (it.hasNext()) {
			Runoff current = it.next() ;
			int col = (current.getFront() - xOffset)/scalor ;
			int row = current.getRow() ;
			if (current.getFront() > xOffset)
				if (getTiles().get(7*row+col) instanceof Plant) {
					Plant plant = (Plant)getTiles().get(7*row+col);
					battle(plant, current);
					if (current.getHealth().size() == 0) {
						removal = current;
					}
				}
				else {
					if (col != (current.getFront()-1 - xOffset)/scalor) {
						current.grow();
					}
					current.setFront(current.getFront()-1);
				}
			else {
				if (getMussels().size() > 0) {
					getMussels().remove(getMussels().size()-1);
				}
				removal = current;
			}
			current.setTicksSinceMoved(current.getTicksSinceMoved() + 1);
		}
		if (removal != null) {
			getEnemies().remove(removal);
		}
		
	}

	/**
	 * Computes a fight between a plant and some runoff (Health - Strength)
	 * If the runoff dies it gives the player score and money
	 * If the runoff dies it removes the runoff from the lane
	 * @param plant - the plant object that the runoff ran into
	 * @param runoff- the runoff object that ran into the plant
	 */
	public void battle(Plant plant, Runoff runoff) {
		plant.setHealth(plant.getHealth() - runoff.getStrength());
		runoff.getHealth().set(0, runoff.getHealth().get(0) - plant.getStrength());
		if (runoff.getHealth().size() >0) {
			if(runoff.getHealth().get(0) <= 0) {
				runoff.removeFront();
			}
		}
		if (plant.getHealth() <= 0) {
			getPlants().remove(plant);
			getTiles().set(plant.getRow()*7 +plant.getCol(), new Tile(plant.getRow(), plant.getCol()));
		}
	}
	
	
	/**
	 * Creates a new mussel and adds it to the game in a random, non colliding position
	 */
	public void addMussel() {
		Random rand = new Random();
		int tooManyTries = 0;
		int xLoc	=	rand.nextInt(xOffset	-	(18/5)*scalor);
		int yLoc	=	rand.nextInt(OverallGame.frameHeight-yOffset*3/4-2*scalor) + yOffset*3/4;
		Rectangle newMussel = new Rectangle(xLoc,yLoc,(9/5)*scalor,scalor);
		Rectangle curMussel = new Rectangle(0, 0,(9/5)*scalor,scalor);
		for (int i = 0 ; i < getMussels().size();) {
			Mussel current = getMussels().get(i);
			newMussel.setBounds(xLoc,yLoc,(9/5)*scalor,scalor);
			curMussel.setBounds(current.getXLoc(), current.getYLoc(),(9/5)*scalor,scalor);
			if (newMussel.intersects(curMussel)) {
				xLoc = rand.nextInt(xOffset	-	(18/5)*scalor);
				yLoc = rand.nextInt(OverallGame.frameHeight-yOffset*3/4-2*scalor) + yOffset*3/4;
				i=0;
				tooManyTries++;
			}
			else {i++;}
			if (tooManyTries > 50) {
				i = getMussels().size();
			}
		}
		if (tooManyTries < 50) {
			getMussels().add(new Mussel(xLoc, yLoc));
		}
	}
	
	/**
	 * Adds the given amount to the player's money
	 * Used for testing and for mussel collection
	 * @param amount - the amount of money to be added
	 */
	public void addMoney(int amount) {
		this.money += amount;
		if (getMoney() <= 1000) {
			if(totalCoin != null) {
				totalCoin.setVisible(false);
				totalCoin = null;
			}
			while (getMoney() / 100 > coins.size()) {
				ImageIcon imi = new ImageIcon("images/coin.png");
				JLabel jl = new JLabel(imi);
				coins.add(jl);
				jl.setBounds(xOffset+(coins.indexOf(jl))*scalor/3, 0, scalor/3, scalor/3);
				gamePanel.add(jl);
			}
			while (getMoney() / 100 < coins.size() && coins.size() != 0) {
				gamePanel.remove(coins.get(coins.size()-1));
				coins.remove(coins.size() - 1);
			}
		}
		else if (coins.size() == 10){
			if (coins.size() != 0) {
				for (JLabel current : coins) {
					gamePanel.remove(current);
					current.setVisible(false);
				}
				coins.removeAll(coins);
			}
			ImageIcon imi = new ImageIcon("images/coin.png");
			JLabel jl = new JLabel(imi);
			coins.add(jl);
			jl.setBounds(xOffset+(coins.indexOf(jl))*scalor/3, 0, scalor/3, scalor/3);
			gamePanel.add(jl);
			totalCoin = new JLabel(" X" + getMoney()/100);
			totalCoin.setBounds(((xOffset+scalor/3)+scalor/3),0,scalor,scalor/3);
			totalCoin.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
			gamePanel.add(totalCoin);
			
		}
		else {
			if (totalCoin == null) {
				ImageIcon imi = new ImageIcon("images/coin.png");
				JLabel jl = new JLabel(imi);
				coins.add(jl);
				jl.setBounds(xOffset+(coins.indexOf(jl))*scalor/3, 0, scalor/3, scalor/3);
				gamePanel.add(jl);
				
			}
			else {
				totalCoin.setText(" X" + getMoney()/100);
			}
		}
	}
	
	/**
	 * If the game ends, passes the score to the overall score
	 * And sets the overall shell to the running state and calls the update method on the overall shell
	 * @param highScoreLoc - file name of high score
	 */
	public void endGame(String highScoreLoc) {
		getBigGame().setOverallScore(getBigGame().getOverallScore() + getScore());
		getBigGame().setGamesRunning(0);
		getBigGame().getGamesComplete()[2]	=	true;
		timer.stop();
		getBigGame().getGameWindow().getCurrentScore().setText("Overall Score: " + bigGame.getOverallScore());
		getBigGame().updateHighScores(highScoreLoc);
		gameFrame.setContentPane(bigGamePanel);
		gameFrame.getContentPane().setVisible(true);
	}
	
	/**
	 * Adds a score to the player's score
	 * @param score - the score to be added
	 */
	public void addScore(int score) {
		this.score += score;
		timeAndScore.setText("Score:"+getScore());
	}
	
	/**
	 * This method is used for testing the onClick method without
	 * simulating clicks. the method is a duplicate of onclick so that method 
	 * won't have to be tested
	 * @param y
	 * @param x
	 */
	public void onClickForTesting(int y, int x) {
		if (timer.isRunning() == false) {
			timer.start();
		}
		int xLoc = x;
		int yLoc = y;
		Mussel removal = null;
		if (xLoc < xOffset) {
			for (Mussel current : getMussels()) {
				if (current.getStage() ==  100) {
					if ((xLoc > current.getXLoc() && xLoc < current.getXLoc() + (18/5)*scalor) &&
						(yLoc > current.getYLoc() && yLoc < current.getYLoc() + 2*scalor)) {
						addScore(50); addMoney(100);
						removal = current;
					}
				}
			}
			if (removal != null) {
				addMussel();
				getMussels().remove(removal);
			}
		}
		else if (getMoney() >= 100 && xLoc < xOffset + 7*scalor && yLoc > yOffset && yLoc < yOffset + 4*scalor){
			menuRegen();
			menu.setVisible(true);
			final int row = (yLoc -	yOffset)	/scalor ;
			final int col = (xLoc - xOffset)	/scalor	;
			System.out.println("Row: " + row + "  Col: " + col);
			timer.stop();
			menu.setLocation(xLoc, yLoc);
			menu.show(getGameFrame(), xLoc, yLoc);
			System.out.println(menu.isVisible());
			System.out.println("x: " + menu.getLocation().getX() + " y: " + menu.getLocation().getY());
			if(grassListen == null) {
				grass.removeActionListener(grassListen);
			}
			grassListen	=	new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(getTiles().get(7*row+col)instanceof Plant){
						grass.removeActionListener(this);
					}
					else {
						if(getMoney()>=100){
							addMoney(-100);
							addPlant(row,col,"Grass");
							timer.start();}
						else{ timer.start();}
					}
				}
			};
			grass.addActionListener(grassListen);
			if (mangroveListen != null) {
				mangrove.removeActionListener(mangroveListen);
			}
			mangroveListen	=	new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (getTiles().get(7*row+col)instanceof Plant) {
						mangrove.removeActionListener(this);
					}
					else {
						if(getMoney()>=200){
							addMoney(-200);
							addPlant(row,col,"Mangrove");
							timer.start();}
						else{ timer.start();}
					}
				}
			};	
		}
		else {
			timer.start();
			menu.setVisible(false);
		}
		mangrove.addActionListener(mangroveListen);
		
	}
	
	/**
	 * Getters And Setters and toString
	 */


	public String toString(){
		return "Game3 [ Time: "+time+", Score: "+score+", Money: "+money+"\n"+plants.toString()+"\n"
				+enemies.toString()+"\n"+mussels.toString()+"\n"+", Game Over: "+gameOver+", Big Game: "+bigGame
				+", Timer"+timer+"]";
	}


	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getTickCount() {
		return tickCount;
	}

	public void setTickCount(int tickCount) {
		this.tickCount = tickCount;
	}

	public ArrayList<Plant> getPlants() {
		return plants;
	}

	public void setPlants(ArrayList<Plant> plants) {
		this.plants = plants;
	}

	public ArrayList<Runoff> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Runoff> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Mussel> getMussels() {
		return mussels;
	}

	public void setMussels(ArrayList<Mussel> mussels) {
		this.mussels = mussels;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public void setTiles(ArrayList<Tile> tiles) {
		this.tiles = tiles;
	}

	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
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

	public JPanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(JPanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public JPanel getBigGamePanel() {
		return bigGamePanel;
	}

	public void setBigGamePanel(JPanel bigGamePanel) {
		this.bigGamePanel = bigGamePanel;
	}

	public JFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(JFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public JLabel getTimeAndScore() {
		return timeAndScore;
	}

	public void setTimeAndScore(JLabel timeAndScore) {
		this.timeAndScore = timeAndScore;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public static Image getBackground() {
		return background;
	}

	public static void setBackground(Image background) {
		Game3.background = background;
	}

	public JPopupMenu getMenu() {
		return menu;
	}

	public void setMenu(JPopupMenu menu) {
		this.menu = menu;
	}

	public JProgressBar getTimeBar() {
		return timeBar;
	}

	public void setTimeBar(JProgressBar timeBar) {
		this.timeBar = timeBar;
	}

	public ArrayList<JLabel> getCoins() {
		return coins;
	}

	public void setCoins(ArrayList<JLabel> coins) {
		this.coins = coins;
	}

	public JLabel getTotalCoin() {
		return totalCoin;
	}

	public void setTotalCoin(JLabel totalCoin) {
		this.totalCoin = totalCoin;
	}


	public JLabel getPipes() {
		return pipes;
	}

	public void setPipes(JLabel pipes) {
		this.pipes = pipes;
	}


	public JMenuItem getGrass() {
		return grass;
	}

	public JMenuItem getMangrove() {
		return mangrove;
	}


	public ActionListener getGrassListen() {
		return grassListen;
	}

	public void setGrassListen(ActionListener grassListen) {
		this.grassListen = grassListen;
	}

	public ActionListener getMangroveListen() {
		return mangroveListen;
	}

	public void setMangroveListen(ActionListener mangroveListen) {
		this.mangroveListen = mangroveListen;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Generic Constructor For use with reinitializing with serializeable
	 * @param time
	 * @param score;
	 * @param money;
	 * @param tickCount;
	 * @param plants;
	 * @param enemies;
	 * @param mussels;
	 * @param tiles;
	 * @param gameRunning;
	 * @param gameOver;
	 * @param bigGame;
	 * @param gamePanel;
	 * @param bigGamePanel;
	 * @param gameFrame;
	 * @param timeAndScore;
	 * @param startTime;
	 * @param timer;
	 * @param menu;
	 * @param timeBar;
	 * @param coins;
	 * @param totalCoin;
	 * @param pipes;
	 * @param endImage;
	 * @param grass;
	 * @param mangrove;
	 * @param grassListen;
	 * @param mangroveListen;
	 */
	public Game3(double time, int score, int money, int tickCount, ArrayList<Plant> plants, ArrayList<Runoff> enemies,
			ArrayList<Mussel> mussels, ArrayList<Tile> tiles, boolean gameRunning, boolean gameOver,
			OverallGame bigGame, JPanel gamePanel, JPanel bigGamePanel, JFrame gameFrame, JLabel timeAndScore,
			long startTime, Timer timer, JPopupMenu menu, JProgressBar timeBar, ArrayList<JLabel> coins,
			JLabel totalCoin, JLabel pipes, Image endImage, JMenuItem grass, JMenuItem mangrove,
			ActionListener grassListen, ActionListener mangroveListen) {
		super();
		this.time = time;
		this.score = score;
		this.money = money;
		this.tickCount = tickCount;
		this.plants = plants;
		this.enemies = enemies;
		this.mussels = mussels;
		this.tiles = tiles;
		this.gameRunning = gameRunning;
		this.gameOver = gameOver;
		this.bigGame = bigGame;
		this.gamePanel = gamePanel;
		this.bigGamePanel = bigGamePanel;
		this.gameFrame = gameFrame;
		this.timeAndScore = timeAndScore;
		this.startTime = startTime;
		this.timer = timer;
		this.menu = menu;
		this.timeBar = timeBar;
		this.coins = coins;
		this.totalCoin = totalCoin;
		this.pipes = pipes;
		this.endImage = endImage;
		this.grass = grass;
		this.mangrove = mangrove;
		this.grassListen = grassListen;
		this.mangroveListen = mangroveListen;
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
		Game3 obj = null ;
		try {	
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (Game3)ois.readObject();
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