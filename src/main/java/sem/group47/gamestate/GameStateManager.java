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

	/** The number of gamestates. */
	public static final int NUMGAMESTATES = 5;

	/** The Constant MENUSTATE. */
	public static final int MENUSTATE = 0;

	/** The Constant LEVEL1STATE. */
	public static final int LEVEL1STATE = 1;

	/** The Constant LEVEL2STATE. */
	public static final int LEVEL2STATE = 2;

	/** The Constant GAMEOVER. */
	public static final int GAMEOVERSTATE = 3;

	/** The Constant HELPSTATE. */
	public static final int HELPSTATE = 4;

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
		} else if (state == LEVEL1STATE) {
			gameStates[state] = new Level1State(this);
		} else if (state == LEVEL2STATE) {
			gameStates[state] = new Level2State(this);
		} else if (state == GAMEOVERSTATE) {
			gameStates[state] = new GameOverState(this);
		} else if (state == HELPSTATE) {
			gameStates[state] = new HelpState(this);
		}
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
	public final void setState(final int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/**
	 * Updates the current state.
	 */
	public final void update() {

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
