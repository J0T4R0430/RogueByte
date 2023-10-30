import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class ByteSprite implements DisplayableSprite, MovableSprite{
	
	
	private double centerX = 0;
	private double centerY = 0;
	private double velocityX = 0;
	private double velocityY = 0;
	private double height;
	private double width;
	private boolean dispose = false;
	private boolean right = true;
	private final int A = 65, W = 87, S = 83, D = 68, L = 76, SHIFT = 16, DASH_DISTANCE = 50;
	private final int MOVEMENTSPEED = 4;
	private int deltaX = 0, deltaY = 0;
	private Image[] imageLeft = new Image[4], imageRight = new Image[4];
	private Image standingLeft, standingRight, cloaked, portal;
	private int movementCounter = 0;
	private boolean moving;
	private boolean cloak = false;
	private int dashCD = 0, cloakCD = 0, inCloakTime;
	private double distanceToClosestEnemy;
	private final int DETECTIONDISTANCE = 1000000;
	private WeaponSprite weapon = null;
	
	private int portalTicks = 0;




	public ByteSprite(int health){
		//weapon
		this.weapon = new WeaponSprite();

		// image
		for(int i = 1; i <= 4; i++) {
			try {
				this.imageLeft[i-1] = ImageIO.read(new File("res/ByteMovement/Left" + i + ".png"));
			} catch (IOException e) {}
			try {
				this.imageRight[i-1] = ImageIO.read(new File("res/ByteMovement/Right" + i + ".png"));
			} catch (IOException e) {}	
			}
		
		try {
			this.standingLeft = ImageIO.read(new File("res/ByteMovement/Left1.png"));
			this.standingRight = ImageIO.read(new File("res/ByteMovement/Right1.png"));
			this.height = standingLeft.getHeight(null) * 5;
			this.width = standingLeft.getWidth(null) * 5;
			this.cloaked = ImageIO.read(new File("res/ByteMovement/Cone-Cloak.png"));
			this.portal = ImageIO.read(new File("res/ByteMovement/Portal.png"));
		} catch (IOException e) {}
		
		try {
			this.standingRight = ImageIO.read(new File("res/ByteMovement/Right1.png"));
			
		} catch (IOException e) {}
		//
	}
	
	
	public DisplayableSprite getWeapon() {
		return this.weapon;
	}

	@Override
	public void setCenterX(double centerX) {
		this.centerX = centerX;
		
	}

	@Override
	public void setCenterY(double centerY) {
		this.centerY = centerY;
		
	}

	@Override
	public void setVelocityX(double pixelsPerSecond) {
		this.velocityX = pixelsPerSecond;
		
	}

	@Override
	public void setVelocityY(double pixelsPerSecond) {
		this.velocityY = pixelsPerSecond;

		
	}

	@Override
	public Image getImage() {
		if(this.portalTicks > 0) {
			return portal;
		}
		
		
		
		if(this.cloak) {
			return this.cloaked;
		}
		else if(this.right) {
			if(this.moving) {
				return this.imageRight[(int) (this.movementCounter / 5) % 4];
			}else {
				return this.standingRight;
			}
		}else {
			if(this.moving) {
				return this.imageLeft[(int) (this.movementCounter / 5) % 4];
			}else {
				return this.standingLeft;
			}
		}
	}

	@Override
	public boolean getVisible() {
 
		return true;
	}

	@Override
	public double getMinX() {
 
		return this.getCenterX() - this.getWidth() / 2;
	}

	@Override
	public double getMaxX() {
 
		return this.getCenterX() + this.getWidth() / 2;
	}

	@Override
	public double getMinY() {
 
		return this.getCenterY() - this.getHeight() / 2;
	}

	@Override
	public double getMaxY() {
 
		return this.getCenterY() + this.getHeight() / 2;
	}

	@Override
	public double getHeight() {
 
		return this.height;
	}

	@Override
	public double getWidth() {
 
		return this.width;
	}

	@Override
	public double getCenterX() {
 
		return this.centerX;
	}

	@Override
	public double getCenterY() {
 
		return this.centerY;
	}

	@Override
	public boolean getDispose() {
 
		return this.dispose;
	}

	@Override
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
		
	}
	
	public boolean getCloak() {
		return this.cloak;
	}
	
	@Override
	public void setDirection(boolean right) {
		this.right = right;
	}

	@Override
	public void setDistanceToTarget(double d) {
		this.distanceToClosestEnemy = d;
	}




	@Override
	public void update(Universe universe, KeyboardInput k, long actual_delta_time) {
		//cloak
		if(k.keyDownOnce(L) && this.cloakCD <= 0 && this.inCloakTime <= 0) {
			this.cloak = true;
			this.inCloakTime = 5000;
		}
		if(this.inCloakTime <= 0 && this.cloakCD <= 0) {
			this.cloak = false;
			this.cloakCD = 0;
		}
		//direction detection

		if(this.distanceToClosestEnemy > this.DETECTIONDISTANCE) {
			if(k.keyDown(D)) this.right = true;
			else if(k.keyDown(A)) this.right = false;
		}
		
		//movement
		this.deltaX = 0; this.deltaY = 0; this.moving = false;
		if(k.keyDown(A)) {
			this.deltaX -= this.MOVEMENTSPEED;
		}
		if(k.keyDown(D)) {
			this.deltaX += this.MOVEMENTSPEED;
		}
		if(k.keyDown(W)) {
			this.deltaY -= this.MOVEMENTSPEED;
		}
		if(k.keyDown(S)) {
			this.deltaY += this.MOVEMENTSPEED;
		}
		this.moving = (this.deltaY != 0 || this.deltaX != 0);
		
		//Dash
		if(this.dashCD <= 0) {
			if(k.keyDownOnce(SHIFT)) {
				this.cloak = false;
				if(this.deltaX == 0 && this.deltaY == 0) {
					if(this.right) {
						this.deltaX = this.MOVEMENTSPEED * this.DASH_DISTANCE;
					}else {
						this.deltaX = - this.MOVEMENTSPEED * this.DASH_DISTANCE;
					}
				}else {
					this.deltaX *= DASH_DISTANCE; this.deltaY *= DASH_DISTANCE;

				}
				this.dashCD = 2000;
				this.portalTicks = 200;
			}
		}
		
		if(!this.cloak && this.inCloakTime > 0) {
			this.cloakCD = 10000;
			this.inCloakTime = 0;
		}
		
		
		if(!this.cloak) {
			this.centerX += this.deltaX;
			this.centerY += this.deltaY;
		}else {
			this.centerX += this.deltaX * 0.2;
			this.centerY += this.deltaY * 0.2;
		}

		
		if(this.moving) {
			this.movementCounter ++;
		}else {
			this.movementCounter = 0;
		}
		
		
		
		//cooldowns
		this.cloakCD -= Math.min(actual_delta_time, this.cloakCD);
		this.dashCD -= Math.min(actual_delta_time, this.dashCD);
		this.inCloakTime -= Math.min(actual_delta_time, this.inCloakTime);
		this.portalTicks -= Math.min(actual_delta_time, this.portalTicks);
	}








}
