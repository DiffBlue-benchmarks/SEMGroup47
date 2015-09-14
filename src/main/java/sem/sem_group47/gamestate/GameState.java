package sem.sem_group47.gamestate;

/**
 * The Class GameState. Acts as a Superclass for the separate gamestates e.g.
 * MenuState.
 */
public abstract class GameState {

	/** The gamestate manager. */
	protected GameStateManager gsm;

	/**
	 * Init
	 */
	public abstract void init();

	/**
	 * Update.
	 */
	public abstract void update();

	/**
	 * Draw.
	 *
	 * @param g
	 *            the g
	 */
	public abstract void draw(java.awt.Graphics2D g);

	/**
	 * Key pressed.
	 *
	 * @param k
	 *            the k
	 */
	public abstract void keyPressed(int k);

	/**
	 * Key released.
	 *
	 * @param k
	 *            the k
	 */
	public abstract void keyReleased(int k);

}