package sem.group47.gamestate;

import java.util.ArrayList;

/**
 * The Class GameStateManager.
 */
public class GameStateManager {

	/** The list of game states. */
	private ArrayList<GameState> gameStates;

	/** The current state. */
	private int currentState;

	/** Paused. */
	private boolean paused;

	/** The Constant MENUSTATE. */
	public static final int MENUSTATE = 0;

	/** The Constant LEVELSTATE. */
	public static final int LEVELSTATE = 1;

	/** The Constant GAMEOVER. */
	public static final int GAMEOVERSTATE = 2;

	/** The Constant HELPSTATE. */
	public static final int HELPSTATE = 3;

	/**
	 * Instantiates a new game state manager.
	 */
	public GameStateManager() {

		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;

		gameStates.add(new MenuState(this));
		gameStates.add(new LevelState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new HelpState(this));

	}

	/**
	 * setPaused.
	 * 
	 * @param pPaused
	 *            pause
	 */
	public final void setPaused(final boolean pPaused) {
		paused = pPaused;
	}

	/**
	 * Sets the current state.
	 *
	 * @param state
	 *            the new state
	 */
	public final synchronized void setState(final int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}

	/**
	 * Updates the current state.
	 */
	public final synchronized void update() {
		gameStates.get(currentState).update();
	}

	/**
	 * Draws the current state.
	 *
	 * @param g
	 *            the g
	 */
	public final void draw(final java.awt.Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}

	/**
	 * Key pressed.
	 *
	 * @param k
	 *            the k
	 */
	public final void keyPressed(final int k) {
		gameStates.get(currentState).keyPressed(k);
	}

	/**
	 * Key released.
	 *
	 * @param k
	 *            the k
	 */
	public final void keyReleased(final int k) {
		gameStates.get(currentState).keyReleased(k);
	}

	/**
	 * Returns current gameState.
	 * 
	 * @return current gameState
	 */
	public final int getCurrentState() {
		return currentState;
	}
}
