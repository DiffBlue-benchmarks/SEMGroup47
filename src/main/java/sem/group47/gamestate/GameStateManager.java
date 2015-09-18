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

	/** The pauseState. */
	private PauseState pauseState;

	/** Paused. */
	private boolean paused;

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

		gameStates = new ArrayList<GameState>();
		currentState = MENUSTATE;

		pauseState = new PauseState(this);
		paused = false;

		gameStates.add(new MenuState(this));
		gameStates.add(new Level1State(this));
		gameStates.add(new Level2State(this));
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
	public final void setState(final int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}

	/**
	 * Updates the current state.
	 */
	public final void update() {
		if (paused) {
			pauseState.update();
			return;
		}
		gameStates.get(currentState).update();
	}

	/**
	 * Draws the current state.
	 *
	 * @param g
	 *            the g
	 */
	public final void draw(final java.awt.Graphics2D g) {
		if (paused) {
			pauseState.draw(g);
			return;
		}
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
