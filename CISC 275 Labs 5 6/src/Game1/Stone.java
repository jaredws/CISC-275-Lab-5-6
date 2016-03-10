package Game1;
/**
 * @author Brendan, Danielle, David, Huayu and Zhanglong
 * @version 0.1
 * @since   2015-11-02
 * Subclass of MovingObect, Stone in the game
 */
public class Stone extends MovingObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 106L;
	int mode;
	int speed,acc;
	int bx,by;
	int tick;
	int orig_speed;
	
	//constructor
	/**
	 * @param x  x-coordinate
	 * @param y  y-coordinate
	 * @param size  stone size
	 */
	public Stone(int x, int y, int size) {
		super(x, y, size);
		// TODO Auto-generated constructor stub
		mode=0;
		speed=-6;
		orig_speed=-6;
		bx=x;
		by=y;
		tick=0;
	}
	
	
	/**  action if kicked change the speed and acceleration
	 * 
	 */
	public void kicked(){
		this.speed=(int) (Math.sqrt(orig_speed*-1)*12);
		this.acc=1*orig_speed/-6;
	}
	
	
	/* 
	 * @see Game1.MovingObject#update()
	 * update function for stone
	 * 
	 */
	public void update(){
		if(tick>0)tick-=1;
		if(this.speed+this.getPosition().x<-this.getSize()/2){
			this.getPosition().setLocation(bx+this.getSize()/2, by*0.6);
			orig_speed-=1;
			speed=orig_speed;
		}
		
		else{
			this.getPosition().setLocation(this.getPosition().x+speed,by*0.6+(by*0.4-this.getSize())*(bx-this.getPosition().x)/bx);
		}
		this.getLabel().setBounds(this.getPosition().x, this.getPosition().y, 
				this.getLabel().getWidth(), this.getLabel().getHeight());
		if(speed>orig_speed){
			this.speed-=acc;
		}
		else{
			speed=orig_speed;
		}
		
	}
	

}
