import java.util.*;

public class MovableSpriteUniverse implements Universe {

	private boolean complete = false;	
	private ArrayList<Background> backgrounds = null;
	private Background background = null;	
	private DisplayableSprite Byte = null;
	private DisplayableSprite StationaryEnemy = null;
	private DisplayableSprite MovableEnemy = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<DisplayableSprite> enemies = new ArrayList<DisplayableSprite>();
	private long elapsedTime = 0;
	private String status = "";
	private Random rand = new Random();
	private int stationaryEnemyCount = 0;
	private int ByteHealth = 19;

	private final int DETECTIONDISTANCE = 1000000;
	private final double VELOCITY = 200;	
	
	private int spawnTime = 5000;
	private int enemyNumber = 6;
	private int spawnTimer = this.spawnTime;
	
//	//require a separate list for sprites to be removed to avoid a concurence exception
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();

	
	public MovableSpriteUniverse () {
	
	this.setXCenter(0);
	this.setYCenter(0);
	//backgraounds
	background = new MappedBackground();
	ArrayList<DisplayableSprite> barriers = ((MappedBackground)background).getBarriers();
	backgrounds =new ArrayList<Background>();
	backgrounds.add(background);
//create all of the sprites here
	Byte = new ByteSprite(this.ByteHealth, this);
	sprites.add(Byte);
	sprites.add(Byte.getWeapon());
	sprites.add(Byte.getHealthBar());
	
	MovableSprite movable = (MovableSprite)Byte;
	movable.setCenterX(1600);
	movable.setCenterY(1300);
	sprites.addAll(barriers);
	
	

	
	this.spawn(this.enemyNumber, this.enemyNumber / 2);
		
//	StationaryEnemy = new StationaryEnemySprite();
//	sprites.add(StationaryEnemy);
	
	
	
	
	//menus
	MenuSprite CDBar = new CDBar();
	sprites.add(CDBar);
	MenuSprite HPBar = new HPBar();
	sprites.add(HPBar);
	
	
	
	
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
		return backgrounds;
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
			if(sprite.getDispose()) {
				sprites.remove(i);
			}
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
		
		//menu items
//		this.Byte.updateHealth(this.ByteHealth);
		

		
		//do all of the bullet calculation in here, and the cooldowns
		
		for(DisplayableSprite s : this.sprites) {
			if(s instanceof BulletSprite) {
				for(DisplayableSprite e : this.enemies) {
					if(CollisionDetection.overlaps(s, e)) {
						e.updateHealth(-Integer.parseInt(s.toString()));
						System.out.println(s.toString());
					}
				}
			}else if(s instanceof EnemyBulletSprite) {
				if(CollisionDetection.overlaps(s, this.getPlayer1())) {
					this.getPlayer1().updateHealth(-1);
					s.setDispose(true);
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		this.spawnTimer -= actual_delta_time;
		if(this.spawnTimer <= 0) {
			this.spawnTime = this.getNextSpawnTime(this.spawnTime);
			this.spawnTimer = this.spawnTime;
			this.enemyNumber *= 1.8;
			this.spawn(this.enemyNumber, this.enemyNumber/2);
			this.getPlayer1().updateHealth(19);
		}
		
		
	}
	
	public String toString() {
		return this.status;
	}	
	
	public int getNextSpawnTime(int i) {
		return (int) (i * 1.5);
	}
	public void spawn(int max, int min) {
		this.stationaryEnemyCount = rand.nextInt(max - min) + min;
		for(int i = 0; i < this.stationaryEnemyCount; i++) {
			StationaryEnemy = new StationaryEnemySprite();
			sprites.add(StationaryEnemy);
			enemies.add(StationaryEnemy);
		}
		
	}
	


}
