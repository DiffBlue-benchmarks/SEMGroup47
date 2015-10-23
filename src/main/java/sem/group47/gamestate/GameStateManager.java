package sem.group47.gamestate;

import sem.group47.audio.AudioPlayer;

/**
 * The Class GameStateManager.
 */
public final class GameStateManager {

	/** The list of game states. */
	private GameState[] gameStates;

	/** Unique Instance of GamestateManager. */
	private static GameStateManager uniqueInstance;

	/** The current state. */
	private int currentState;

	/** The number of gamestates. */
	public static final int NUMGAMESTATES = 6;

	/** The Constant MENUSTATE. */
	public static final int MENUSTATE = 0;

	/** The Constant LEVELSTATE. */
	public static final int LEVELSTATE = 1;

	/** The Constant GAMEOVER. */
	public static final int GAMEOVERSTATE = 2;

	/** The Constant HELPSTATE. */
	public static final int HELPSTATE = 3;

	/** The Constant OPTIONSSTATE. */
	public static final int OPTIONSSTATE = 4;

	/** The Constant HIGHSCORESTATE. */
	public static final int HIGHSCORESTATE = 5;

	/**
	 * Instantiates a new game state manager.
	 */
	private GameStateManager() {
		AudioPlayer.init();
		try {
			AudioPlayer.load("/music/menu.mp3", "menu");
			AudioPlayer.load("/music/level1.mp3", "level1");
			AudioPlayer.load("/music/level2.mp3", "level2");
			AudioPlayer.load("/music/level3.mp3", "level3");
			AudioPlayer.load("/music/level4.mp3", "level4");

			AudioPlayer.load("/music/gameover.wav", "gameover");

			AudioPlayer.load("/sfx/jump.wav", "jump");
			AudioPlayer.load("/sfx/fire_bubble.wav", "fire");
			AudioPlayer.load("/sfx/extra_life.wav", "extraLife");
			AudioPlayer.load("/sfx/bubble_pop.wav", "bubblePop");
			AudioPlayer.load("/sfx/player_death.wav", "dead");
			AudioPlayer.load("/sfx/crash.wav", "crash");

		} catch (Exception e) {
			e.printStackTrace();
		}
		gameStates = new GameState[NUMGAMESTATES];

		currentState = MENUSTATE;
		loadState(currentState);
	}

	/**
	 * Makes sure GameStateManager is a Singleton, and can't have more than one
	 * instance running.
	 *
	 * @return instance.
	 */
	public static synchronized GameStateManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new GameStateManager();
		}
		return uniqueInstance;
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
		} else if (state == OPTIONSSTATE) {
			gameStates[state] = new OptionsState(this);
		} else if (state == HIGHSCORESTATE) {
			gameStates[state] = new HighScoreState(this);
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
	public synchronized void setState(final int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	/**
	 * Updates the current state.
	 */
	public synchronized void update() {

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
	public void draw(final java.awt.Graphics2D g) {
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
	public void keyPressed(final int k) {
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
	public void keyReleased(final int k) {
		if (gameStates[currentState] != null) {
			gameStates[currentState].keyReleased(k);
		}
	}

	/**
	 * Returns current gameState.
	 *
	 * @return current gameState
	 */
	public int getCurrentState() {
		return currentState;
	}
}
