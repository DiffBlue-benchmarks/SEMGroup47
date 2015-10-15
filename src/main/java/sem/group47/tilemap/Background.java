package sem.group47.tilemap;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.main.GamePanel;

public class Background {

	/** The background image. */
	private BufferedImage image;

	/**
	 * Instantiates a new background.
	 * 
	 * @param s
	 *            the location of the resource.
	 */
	public Background(String s) {
		try {
			image = ImageIO.read(
			 getClass().getResourceAsStream(s)
			);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** Draw the background.
	 * 
	 * @param g
	 * 			the graphics to draw;
	 *  */
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 60, GamePanel.WIDTH, GamePanel.HEIGHT-60, null);
	}


}
