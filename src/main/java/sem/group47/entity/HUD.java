package sem.group47.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sem.group47.main.Drawable;
import sem.group47.main.GamePanel;

/**
 * The Class HUD, which is used to create a heads up display.
 */
public class HUD implements Drawable {

	/** The player. */
	private Player player1;

	/** The player2. */
	private Player player2;

	/** The image. */
	private BufferedImage image;

	/** The font. */
	private Font font;

	/**
	 * Constructor, which initiates the players, image and font.
	 *
	 * @param p1
	 *            the p1
	 * @param p2
	 *            the p2
	 */
	public HUD(final Player p1, final Player p2) {
		player1 = p1;
		player2 = p2;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(
					"/hud/Bubble_Heart.png"));
			font = new Font("Arial", Font.PLAIN, 28);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Draws the HUD for one player game and two player games.
	 *
	 * @param g
	 *            the Graphics2D object g
	 */
	public final void draw(final Graphics2D g) {
		g.setFont(font);
		for (int i = 0; i < player1.getLives() && i < 3; i++) {
			g.drawImage(image, i * 30, 0, null);
		}
		int undrawnLives = player1.getLives() - 3;
		if (undrawnLives > 0) {
			g.setColor(Color.WHITE);
			drawCenteredString("+" + undrawnLives, 50, 54, g);
		}
		g.setColor(Color.GREEN);
		drawCenteredString("1UP", 130, 26, g);
		g.setColor(Color.WHITE);
		drawCenteredString("" + player1.getExtraLive(), 130, 54, g);
		g.setColor(Color.RED);
		drawCenteredString("SCORE", 325, 26, g);
		g.setColor(Color.WHITE);
		drawCenteredString("" + player1.getScore(), 325, 54, g);

		if (player2 != null) {
			for (int i = 0; i < player2.getLives() && i < 3; i++) {
				g.drawImage(image, GamePanel.WIDTH - (i + 1) * 30, 0, null);
			}
			undrawnLives = player2.getLives() - 3;
			if (undrawnLives > 0) {
				g.setColor(Color.WHITE);
				drawCenteredString("+" + undrawnLives, GamePanel.WIDTH - 50,
						54, g);
			}
			g.setColor(Color.GREEN);
			drawCenteredString("1UP", GamePanel.WIDTH - 130, 26, g);
			g.setColor(Color.WHITE);
			drawCenteredString("" + player2.getExtraLive(),
					GamePanel.WIDTH - 130, 54, g);
			g.setColor(Color.RED);
			drawCenteredString("SCORE", GamePanel.WIDTH - 325, 26, g);
			g.setColor(Color.WHITE);
			drawCenteredString("" + player2.getScore(), GamePanel.WIDTH - 325,
					54, g);
		}
	}

	/**
	 * Helper function for drawing a string with center alignment.
	 *
	 * @param s
	 *            the string
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param g
	 *            graphics object
	 */
	public static void drawCenteredString(final String s, final int x,
			final int y, final Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		g.drawString(s, x - fm.stringWidth(s) / 2, y);
	}
}
