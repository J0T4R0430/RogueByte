import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class StationaryEnemySprite implements DisplayableSprite {

	private double centerX = 0;
	private double centerY = 0;
	private double height;
	private double width;
	private boolean dispose = false;
	private Image standingLeft, standingRight;
	private Random rand = new Random();

	public StationaryEnemySprite() {
		this.centerX = rand.nextInt(1000);
		if(rand.nextBoolean()) this.centerX *= -1;
		this.centerY = rand.nextInt(1000);
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
		return this.standingLeft;
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
		return this.centerY;
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
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		

	}

}
