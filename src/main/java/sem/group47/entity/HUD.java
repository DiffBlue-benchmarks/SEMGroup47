package sem.group47.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Class HUD
 */
public class HUD {

	/** The player. */
	private Player player;

	/** The image. */
	private BufferedImage image;

	/** The font. */
	private Font font;

	/**
	 * Instantiates a new hud.
	 *
	 * @param p
	 *            the p
	 */
	public HUD(Player p) {
		this.player = p;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(
					"/hud/Bubble_Heart.png"));
			font = new Font("Arial", Font.PLAIN, 28);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Draw the HUD
	 *
	 * @param g
	 *            the g
	 */
	public void draw(Graphics2D g) {

		// Draw the number of lives the player has
		for (int i = 0; i < player.getLives(); i++) {
			g.drawImage(image, i * 30, 0, null);
		}

		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString("1UP", 130, 26);
		g.setColor(Color.WHITE);
		g.drawString("" + player.getExtraLive(), 130, 54);
		g.setColor(Color.RED);
		g.drawString("HIGH SCORE", 325, 26);
		g.setColor(Color.WHITE);
		g.drawString("" + player.getScore(), 325, 54);

	}
}
