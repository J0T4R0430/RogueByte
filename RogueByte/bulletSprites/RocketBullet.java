import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.*;

public class RocketBullet extends BulletSprite{
	public RocketBullet(double startX, double startY, int angleInDegrees) {
		super(startX, startY, Math.toRadians(angleInDegrees), 50 , "res/Bullets/RocketBullet.png", angleInDegrees, 6000, 60);
		// TODO Auto-generated constructor stub
	}

}
