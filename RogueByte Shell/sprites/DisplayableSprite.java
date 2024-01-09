import java.awt.Image;

public interface DisplayableSprite {

	public abstract Image getImage();
	
	public boolean getVisible();

	public double getMinX();

	public double getMaxX();

	public double getMinY();

	public double getMaxY();

	public double getHeight();

	public double getWidth();

	public double getCenterX();

	public double getCenterY();
	
	public boolean getDispose();
	

	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time);	
	
	public boolean getCloak();
	
	public void setDirection(boolean right);
	
	public void setDistanceToTarget(double d);
	
	public void setDispose(boolean dispose);
	
	public void updateHealth(int i);
	
	public DisplayableSprite getWeapon();

	public DisplayableSprite getHealthBar();
	
}
