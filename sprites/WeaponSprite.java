import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;


public class WeaponSprite implements DisplayableSprite, MovableSprite{
	private double centerX = 0;
	private double centerY = 0;
	private double velocityX = 0;
	private double velocityY = 0;
	private double height;
	private double width;
	private boolean dispose = false;
	private Image image, imageR;
	private Image[] images = new Image[8], fullImages = new Image[360];
	private double angle = 0, fullAngle = 0, angleInRadians = 0;
	private final int A = 65, W = 87, S = 83, D = 68;
	private boolean r = false, u = false, inRange = false; 
	private double x = 0, y = 0;
	

	
	public WeaponSprite() {
		try {
			this.image = ImageIO.read(new File("res/Weapons/Rifle.png"));
			this.imageR = ImageIO.read(new File("res/Weapons/RifleR.png"));
			this.height = this.image.getHeight(null) * 3;
			this.width = this.height;
		} catch (IOException e) {}
		for(int i = 0; i < 8; i++) {
			if(i >= 4) {
				images[i] = ImageRotator.rotate(imageR, (i-4)*45);
			}else {
				images[i] = ImageRotator.rotate(image, i*45);
			}
		}
		for(int i = 0; i < 360; i++) {
			if(i >= 180) {
				fullImages[i] = ImageRotator.rotate(imageR, (i - 180));
			}else {
				fullImages[i] = ImageRotator.rotate(image, i);
			}
		}
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
		if(this.inRange) {
			return this.fullImages[(int) fullAngle];
		}else {
			return this.images[(int) angle];
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
	public boolean getCloak() {
		return false;
	}

	@Override
	public void setDirection(boolean inrange) {
		this.inRange = inrange;
		
	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
		
	}

	@Override
	public void update(Universe universe, KeyboardInput k, long actual_delta_time) {
		this.setCenterX(universe.getPlayer1().getCenterX());	
		this.setCenterY(universe.getPlayer1().getCenterY()+10);
		
		if(k.keyDown(W) && k.keyDown(D)) {
			this.angle = 1;
		}else if(k.keyDown(D) && k.keyDown(S)) {
			this.angle = 3;
		}else if(k.keyDown(S) && k.keyDown(A)) {
			this.angle = 5;
		}else if(k.keyDown(A) && k.keyDown(W)) {
			this.angle = 7;
		}else if(k.keyDown(W)) {
			this.angle = 0;
		}else if(k.keyDown(D)) {
			this.angle = 2;
		}else if(k.keyDown(S)) {
			this.angle = 4;
		}else if(k.keyDown(A)) {
			this.angle = 6;
		}
		
	}

	@Override
	public void setDistanceToTarget(double d) {
		int dd = (int) d;
		this.u = (dd % 10 == 0); dd /= 10;
		this.r = (dd % 10 == 1); dd /= 10;
		this.y = dd % 1000; dd /= 1000;
		this.x = dd % 1000;
		
		this.angleInRadians = Math.atan(x / y);
		this.fullAngle = Math.toDegrees(this.angleInRadians);
		
		if(!u) this.fullAngle = 180 - this.fullAngle;
		if(!r) this.fullAngle = 360 - this.fullAngle;
		
		if(this.fullAngle < 0) this.angle += 360;
		this.fullAngle %= 360;
		
		
	}

	@Override
	public DisplayableSprite getWeapon() {
		// TODO Auto-generated method stub
		return null;
	}

}
