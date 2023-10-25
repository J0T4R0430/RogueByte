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

	private final double VELOCITY = 200;	
	
//	//require a separate list for sprites to be removed to avoid a concurence exception
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();

	
	public MovableSpriteUniverse () {
	
	this.setXCenter(0);
	this.setYCenter(0);
//create all of the sprites here
	Byte = new ByteSprite(0);
	sprites.add(Byte);

	
	
	
	this.stationaryEnemyCount = rand.nextInt(8) + 2;
	for(int i = 0; i < this.stationaryEnemyCount; i++) {
		StationaryEnemy = new StationaryEnemySprite();
		sprites.add(StationaryEnemy);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	MovableSprite movable = (MovableSprite)Byte;
	movable.setCenterX(0);
	movable.setCenterY(0);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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

		
		
		
		
		
		
		
		
		
		
		
		
		
		//do all of the bullet calculation in here, and the cooldowns
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public String toString() {
		return this.status;
	}	

}
