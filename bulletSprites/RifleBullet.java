import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class RifleBullet extends BulletSprite{

	public RifleBullet(double startX, double startY, int angleInDegrees) {
		super(startX, startY, Math.toRadians(angleInDegrees), 80 , "res/Bullets/RifleBullet.png", angleInDegrees,2000, 17);
		// TODO Auto-generated constructor stub
	}
	
	

}
