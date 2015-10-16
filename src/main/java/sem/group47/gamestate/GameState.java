package sem.group47.gamestate;

import sem.group47.main.DrawComposite;
import sem.group47.main.Drawable;

/**
 * The Class GameState. Acts as a Superclass for the separate gamestates e.g.
 * MenuState.
 */
public abstract class GameState extends DrawComposite {

	/** The gamestate manager. */
	private GameStateManager gsm;

	/**
	 * Init.
	 */
	public void init() {};

	/**
	 * Update.
	 */
	public void update() {};

	/**
	 * Draw.
	 *
	 * @param g
	 *            the g
	 */
	public void draw(java.awt.Graphics2D g) {};

	/**
	 * Key pressed.
	 *
	 * @param k
	 *            the k
	 */
	public void keyPressed(int k) {};

	/**
	 * Key released.
	 *
	 * @param k
	 *            the k
	 */
	public void keyReleased(int k) {};

	/**
	 * getGsm.
	 * 
	 * @return gsm
	 */
	public final GameStateManager getGsm() {
		return gsm;
	}

	/**
	 * setGsm.
	 * 
	 * @param pGsm
	 *            gsm
	 */
	public final void setGsm(final GameStateManager pGsm) {
		this.gsm = pGsm;
	}

}