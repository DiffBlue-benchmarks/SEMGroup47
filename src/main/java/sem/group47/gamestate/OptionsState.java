package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import sem.group47.audio.AudioPlayer;
import sem.group47.main.GamePanel;

/**
 * The Class OptionsState, which extends the super class GameState.
 */
public class OptionsState extends GameState {

	/** The current choice. */
	private int currentChoice = 0;

	/** The font. */
	private Font font, font2;

	/** The options. */
	private String[] options = { "On", "Off" };

	/**
	 * Instantiates a new options state.
	 *
	 * @param gsm
	 *            the gamestatemanager
	 */
	public OptionsState(final GameStateManager gsm) {

		setGsm(gsm);

		try {
			font = new Font("Arial", Font.PLAIN, 30);
			font2 = new Font("Arial", Font.PLAIN, 40);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Draws everything of the options screen.
	 */
	@Override
	public final void draw(final Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		int x = GamePanel.WIDTH - 300;

		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Audio settings", 100, 100);

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
	 * Select between different options.
	 */
	private void select() {
		if (currentChoice == 0) {
			if (AudioPlayer.isMute() == true) {
				AudioPlayer.setMute(false);
			}
			getGsm().setState(GameStateManager.MENUSTATE);
		}
		if (currentChoice == 1) {
			if (AudioPlayer.isMute() == false) {
				AudioPlayer.setMute(true);
				AudioPlayer.stop("menu");
			}
			getGsm().setState(GameStateManager.MENUSTATE);
		}

	}

	/**
	 * Lets you scroll through options with up and down keys.
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
