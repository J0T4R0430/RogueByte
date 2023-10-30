import java.util.*;

public class MovableSpriteUniverse implements Universe {

	private boolean complete = false;	
	private Background background = null;	
	private DisplayableSprite Byte = null;
	private DisplayableSprite StationaryEnemy = null;
	private DisplayableSprite MovableEnemy = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private long elapsedTime = 0;
	private String status = "";
	private Random rand = new Random();
	private int stationaryEnemyCount = 0;

	private final int DETECTIONDISTANCE = 1000000;
	private final double VELOCITY = 200;	
	
//	//require a separate list for sprites to be removed to avoid a concurence exception
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();

	
	public MovableSpriteUniverse () {
	
	this.setXCenter(0);
	this.setYCenter(0);
//create all of the sprites here
	Byte = new ByteSprite(0);
	sprites.add(Byte);
	sprites.add(Byte.getWeapon());
	
	MovableSprite movable = (MovableSprite)Byte;
	movable.setCenterX(0);
	movable.setCenterY(0);
	
	

	
	
	
	this.stationaryEnemyCount = rand.nextInt(3) + 3;
	for(int i = 0; i < this.stationaryEnemyCount; i++) {
		StationaryEnemy = new StationaryEnemySprite();
		sprites.add(StationaryEnemy);
	}
	
//	StationaryEnemy = new StationaryEnemySprite();
//	sprites.add(StationaryEnemy);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//
	
}
	
	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return this.Byte.getCenterX();
	}

	public double getYCenter() {
		return this.Byte.getCenterY();
	}
	
	public void setXCenter(double xCenter) {
	}

	public void setYCenter(double yCenter) {
	}
	
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
	}

	public ArrayList<Background> getBackgrounds() {
		return null;
	}

	public DisplayableSprite getPlayer1() {
		return Byte;
	}

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}
	
//	public boolean centerOnPlayer() {
//		return true;
//	}		
	
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			
			sprite.update(this, keyboard, actual_delta_time);
    	}    	

		
		//directions

		double d = 0; double min = Integer.MAX_VALUE; 
		double cX = this.getPlayer1().getCenterX(), cY = this.getPlayer1().getCenterY(); 
		double maxX = 0, maxY = 0;
		for(DisplayableSprite s : this.getSprites()) {
			if(s instanceof StationaryEnemySprite || s instanceof MovableEnemySprite) {
				d = Math.pow(cX - s.getCenterX(), 2) + 
						Math.pow(cY - s.getCenterY(), 2);
				s.setDistanceToTarget(d);
				if(d < this.DETECTIONDISTANCE) {
					s.setDirection(cX > s.getCenterX());
				}
				if(d < min) {
					min = d; maxX = s.getCenterX(); maxY = s.getCenterY();
				}
			}
		}
		this.getPlayer1().setDistanceToTarget(min);
		
		
		
		
		if(min < this.DETECTIONDISTANCE)	{
			this.getPlayer1().setDirection(maxX > cX);
			this.getPlayer1().getWeapon().setDirection(true);
			
			String fp = String.format("%03d%03d", (int)Math.abs(maxX -cX), (int)Math.abs(maxY - cY - 10));
			
			double encoded = 0;
			if(maxX > cX && maxY > cY) {
				encoded = 11;
			}else if(maxX > cX && maxY < cY)
			{
				encoded = 10;
			}else if(maxX < cX && maxY < cY) {
				encoded = 0;
			}else {
				encoded = 1;
			}
			this.getPlayer1().getWeapon().setDistanceToTarget(Integer.parseInt(fp) * 100 + encoded);
		}else {
			this.getPlayer1().getWeapon().setDirection(false);
		}


		
		
		
		
		
		
		
		
		
		
		
		//do all of the bullet calculation in here, and the cooldowns
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public String toString() {
		return this.status;
	}	

}
