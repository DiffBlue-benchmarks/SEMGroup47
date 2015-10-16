package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.main.GamePanel;

/**
 * The Class HelpState, which extends the super class GameState.
 */
public class HelpState extends GameState {

	/** The current choice. */
	private int currentChoice = 0;

	/** The font. */
	private Font font, font2;

	/** The options. */
	private String[] options = { "Return mkay", "Start mkay" };

	/** The Background. */
	private String bg = "/backgrounds/Mr_Mackey.png";

	/** The image. */
	private BufferedImage image;

	/**
	 * Instantiates a new menu state.
	 *
	 * @param gsm
	 *            the gamestatemanager
	 */
	public HelpState(final GameStateManager gsm) {

		setGsm(gsm);

		try {
			image = ImageIO.read(getClass().getResourceAsStream(bg));
			font = new Font("Arial", Font.PLAIN, 30);
			font2 = new Font("Arial", Font.PLAIN, 40);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Draws everything of the menu screen.
	 */
	@Override
	public final void draw(final Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		int x = GamePanel.WIDTH - 300;
		int y = (GamePanel.HEIGHT - image.getHeight(null)) / 5;
		g.drawImage(image, x, y, null);

		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("PRESS UP TO JUMP", 100, 100);
		g.drawString("PRESS DOWN TO SHOOT", 100, 200);
		g.drawString("PRESS LEFT TO GO LEFT", 100, 300);
		g.drawString("PRESS RIGHT TO GO RIGHT", 100, 400);

		g.setFont(font2);
		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}

			g.drawString(options[i], 320, 500 + i * 50);
		}

	}

	/**
	 * Select between different menu options.
	 */
	private void select() {
		if (currentChoice == 0) {
			getGsm().setState(GameStateManager.MENUSTATE);
		}
		if (currentChoice == 1) {
			getGsm().setState(GameStateManager.LEVELSTATE);
		}

	}

	/**
	 * Lets you scroll through menu options with up and down keys.
	 */
	@Override
	public final void keyPressed(final int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}
}
