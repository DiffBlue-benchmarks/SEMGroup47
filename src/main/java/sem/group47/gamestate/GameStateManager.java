package sem.group47.gamestate;

import sem.group47.audio.AudioPlayer;

// TODO: Auto-generated Javadoc
/**
 * The Class GameStateManager.
 */
public class GameStateManager {

	/** The list of game states. */
	private GameState[] gameStates;

	/** The current state. */
	private int currentState;

	/** Paused. */
	private boolean paused;

	/** The number of gamestates. */
	public static final int NUMGAMESTATES = 4;

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
		AudioPlayer.init();
		gameStates = new GameState[NUMGAMESTATES];

		currentState = MENUSTATE;
		loadState(currentState);
	}

	/**
	 * Load state.
	 *
	 * @param state
	 *            the state
	 */
	private void loadState(final int state) {
		if (state == MENUSTATE) {
			gameStates[state] = new MenuState(this);
		} else if (state == LEVELSTATE) {
			gameStates[state] = new LevelState(this);
		} else if (state == GAMEOVERSTATE) {
			gameStates[state] = new GameOverState(this);
		} else if (state == HELPSTATE) {
			gameStates[state] = new HelpState(this);
		}
		gameStates[state].init();
	}

	/**
	 * Unload state.
	 *
	 * @param state
	 *            the state
	 */
	private void unloadState(final int state) {
		gameStates[state] = null;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public final synchronized void setState(final int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/**
	 * Updates the current state.
	 */
	public synchronized final void update() {

		if (gameStates[currentState] != null) {
			gameStates[currentState].update();
		}
	}

	/**
	 * Draws the current state.
	 *
	 * @param g
	 *            the g
	 */
	public final void draw(final java.awt.Graphics2D g) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].draw(g);
		}
	}

	/**
	 * Key pressed.
	 *
	 * @param k
	 *            the k
	 */
	public final void keyPressed(final int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyPressed(k);
		}
	}

	/**
	 * Key released.
	 *
	 * @param k
	 *            the k
	 */
	public final void keyReleased(final int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(k);
		}
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
