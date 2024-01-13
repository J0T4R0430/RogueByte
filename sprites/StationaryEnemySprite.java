import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class StationaryEnemySprite implements DisplayableSprite, EnemyInterface{

	private double centerX = 0;
	private double centerY = 0;
	private double height;
	private double width;
	private boolean dispose = false;
	private boolean right = false;
	private Image standingLeft, standingRight;
	private Random rand = new Random();
	private double distanceToByte;
	private final int DETECTIONDISTANCE = 1000000;


	public StationaryEnemySprite() {
		this.centerX = rand.nextInt(600) + 100;
		if(rand.nextBoolean()) this.centerX *= -1;
		this.centerY = rand.nextInt(600) + 100;
		if(rand.nextBoolean()) this.centerY *= -1;
		try {
			this.standingLeft = ImageIO.read(new File("res/StationaryEnemy/Left.png"));
			this.standingRight = ImageIO.read(new File("res/StationaryEnemy/Right.png"));
		} catch (IOException e) {}
		this.height = this.standingLeft.getHeight(null) * 3;
		this.width = this.standingLeft.getWidth(null) * 3;
	}

	@Override
	public Image getImage() {
		return this.right ? this.standingRight : this.standingLeft;
	}

	@Override
	public boolean getVisible() {
		return true;
	}

	@Override
	public double getMinX() {
		return this.centerX - this.width / 2;
	}

	@Override
	public double getMaxX() {
		return this.centerX + this.width / 2;
	}

	@Override
	public double getMinY() {
		return this.centerY - this.height / 2;
	}

	@Override
	public double getMaxY() {
		return this.centerY + this.height / 2;
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
	


	@Override
	public boolean getCloak() {
		return false;
	}
	
	@Override
	public void setDirection(boolean right) {
		this.right = right;
	}

	@Override
	public void setDistanceToTarget(double d) {
		this.distanceToByte = d;
	}
	

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		if(this.distanceToByte > this.DETECTIONDISTANCE) {
			if(this.rand.nextInt(200) == 19) {
				this.right = this.right? false : true;
			}
		}
		
	}

	@Override
	public WeaponSprite getWeapon() {
		return null;
	}





}
