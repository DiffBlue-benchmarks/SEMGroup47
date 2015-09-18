package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import sem.group47.main.GamePanel;

/**
 * The Class PauseState.
 */
public class PauseState extends GameState {

	/** The font. */
	private Font font;

	/**
	 * Instantiates a new pause state.
	 *
	 * @param gsm
	 *            the gsm
	 */
	public PauseState(final GameStateManager gsm) {
		super();
		font = new Font("Century Gothic", Font.PLAIN, 40);

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
		// TODO get out of pause menu
	}

	/**
	 * Draw.
	 */
	@Override
	public final void draw(final Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", 90, 90);
	}

	/**
	 * KeyPressed.
	 */
	@Override
	public void keyPressed(final int k) {
		// if (k == KeyEvent.VK_ESCAPE) {
		// gsm.setPaused(false);
		//
		// }

	}

	/**
	 * KeyReleased.
	 */
	@Override
	public void keyReleased(final int k) {
		// TODO Auto-generated method stub

	}

}
