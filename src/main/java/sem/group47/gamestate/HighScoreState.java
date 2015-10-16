package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import sem.group47.entity.PlayerSave;
import sem.group47.highScore.HighScoreManager;
import sem.group47.main.GamePanel;

/**
 * The Class HighScoreState, which extends the super class GameState.
 */
public class HighScoreState extends GameState {

	/** The current choice. */
	private int choice1, choice2, choice3 = 0;

	/** The font. */
	private Font font, font2;

	/** The hsm. */
	private HighScoreManager hsm;

	/** The i. */
	private int i = 0;

	/** The score. */
	private int score;

	/** The high score. */
	private String highScore;

	/** The c3. */
	private String c1, c2, c3;

	/** The alphabet1. */
	private char[] alphabet1 = "abcdefghijklmnopqrstuvwxyz".toUpperCase()
			.toCharArray();

	/** The alphabet2. */
	private char[] alphabet2 = "abcdefghijklmnopqrstuvwxyz".toUpperCase()
			.toCharArray();

	/** The alphabet3. */
	private char[] alphabet3 = "abcdefghijklmnopqrstuvwxyz".toUpperCase()
			.toCharArray();

	/**
	 * Instantiates a new menu state.
	 *
	 * @param gsm
	 *            the gamestatemanager
	 */
	public HighScoreState(final GameStateManager gsm) {
		score = PlayerSave.getScoreP1();
		hsm = new HighScoreManager();
		highScore = hsm.getHighscoreString();

		setGsm(gsm);

		try {
			font = new Font("Arial", Font.PLAIN, 40);
			font2 = new Font("Arial", Font.PLAIN, 30);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Init.
	 */
	@Override
	public void init() {
	}

	/**
	 * Update.
	 */
	@Override
	public void update() {
	}

	/**
	 * Draws everything of the menu screen.
	 *
	 * @param g
	 *            the g
	 */
	@Override
	public final void draw(final Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString("HIGH SCORES", 280, 50);

		g.setColor(Color.WHITE);
		g.setFont(font2);
		drawString(g, highScore, 280, 150);

		if (i == 0) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.WHITE);
		}

		c1 = "" + alphabet1[(((choice1 % 26) + 26) % 26)];
		g.drawString(c1, 330, 550);

		if (i == 1) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.WHITE);
		}
		c2 = "" + alphabet2[(((choice2 % 26) + 26) % 26)];
		g.drawString(c2, 350, 550);

		if (i == 2) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(Color.WHITE);
		}
		c3 = "" + alphabet3[(((choice3 % 26) + 26) % 26)];
		g.drawString(c3, 370, 550);

		g.setColor(Color.WHITE);
		g.drawString("" + PlayerSave.getScoreP1(), 450, 550);

	}

	/**
	 * Draws a String by splitting on the \n.
	 *
	 * @param g
	 *            the g
	 * @param text
	 *            the text
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	private void drawString(final Graphics2D g, final String text, final int x,
			int y) {
		for (String line : text.split("\n")) {
			y += g.getFontMetrics().getHeight();
			g.drawString(line, x, y);
		}
	}

	/**
	 * keyReleased.
	 *
	 * @param k
	 *            the k
	 */
	@Override
	public final void keyReleased(final int k) {
	}

	/**
	 * Key pressed.
	 *
	 * @param k
	 *            the k
	 */
	@Override
	public final void keyPressed(final int k) {
		if (k == KeyEvent.VK_ENTER) {
			hsm.addScore(c1 + c2 + c3, score);
			getGsm().setState(GameStateManager.GAMEOVERSTATE);
		}
		if (k == KeyEvent.VK_UP) {
			if (i == 0) {
				choice1++;
			} else if (i == 1) {
				choice2++;
			} else if (i == 2) {
				choice3++;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			if (i == 0) {
				choice1--;
			} else if (i == 1) {
				choice2--;
			} else if (i == 2) {
				choice3--;
			}
		}
		if (k == KeyEvent.VK_RIGHT) {
			i = (i + 1) % 3;
		}
		if (k == KeyEvent.VK_LEFT) {
			i = ((((i - 1) % 3) + 3) % 3);
		}

	}
}
