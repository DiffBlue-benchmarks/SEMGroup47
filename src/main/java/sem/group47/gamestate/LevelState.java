package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.HUD;
import sem.group47.entity.PlayerSave;
import sem.group47.main.BasicLevelFactory;
import sem.group47.main.GamePanel;
import sem.group47.main.Level;
import sem.group47.main.Log;
import sem.group47.tilemap.Background;

/**
 * The Class Level1State.
 */
public class LevelState extends GameState {

	/** level file names. **/
	private String[] levelFileNames = new String[] { "level1.map",
			"level2.map", "level3.map", "level4.map" };

	/** file names of music. **/
	private String[] musicFileNames = new String[] { "level1", "level2",
			"level3", "level4" };

	/** Current level. **/
	private int level;

	/** Whether multiplayer is on. **/
	private boolean multiplayer;

	/**
	 * Paused flag.
	 **/
	private boolean paused;

	/** The hud. */
	private HUD hud;

	/** The Background. */
	private Background bg;

	/** the current Level. */
	private Level currentLevel;

	/** the level Factory used to make levels. */
	private BasicLevelFactory levelFactory;

	/**
	 * Instantiates a new level1 state.
	 *
	 * @param gsm
	 *            the gamestatemanager.
	 */
	public LevelState(final GameStateManager gsm) {
		setGsm(gsm);
		levelFactory = new BasicLevelFactory();
	}

	/**
	 * Init.
	 */
	@Override
	public final void init() {
		PlayerSave.init();
		multiplayer = PlayerSave.getMultiplayerEnabled();
		level = 0;
		setupLevel(level, multiplayer);
		paused = false;
		bg = new Background();
	}

	/**
	 * Sets up a certain level.
	 *
	 * @param plevel
	 *            number of level to be set
	 */
	private void setupLevel(int plevel, final boolean multiplayer) {

		if (currentLevel != null) {
			removeComponent(currentLevel);
			removeComponent(hud);
		}
		if (plevel >= levelFileNames.length) {
			plevel = 0;
		}
		this.level = plevel;
		currentLevel = levelFactory.makeLevel(levelFileNames[level],
				multiplayer);
		addComponent(currentLevel);

		hud = new HUD(currentLevel.getPlayer1(), currentLevel.getPlayer2());
		addComponent(hud);
		AudioPlayer.stopAll();
		AudioPlayer.loop(musicFileNames[level]);
	}

	/**
	 * Update the player and enemies.
	 */
	@Override
	public final void update() {
		if (!paused) {
			currentLevel.update();
			lostCheck();
			nextLevelCheck();
		}
	}

	/**
	 * checks if the player is dead.
	 */
	private void lostCheck() {
		if (currentLevel.hasLost()) {
			getGsm().setState(GameStateManager.HIGHSCORESTATE);
		}
	}

	/**
	 * Next level.
	 */
	public final void nextLevelCheck() {
		if (currentLevel.hasWon()) {
			PlayerSave.setLivesP1(currentLevel.getPlayer1().getLives());
			if (level == 0) {
				PlayerSave
						.setScoreP1(currentLevel.getPlayer1().getScore() + 100);
			} else if (level == 1) {
				PlayerSave
						.setScoreP1(currentLevel.getPlayer1().getScore() + 200);
			} else if (level == 2) {
				PlayerSave
						.setScoreP1(currentLevel.getPlayer1().getScore() + 300);
			} else if (level == 3) {
				PlayerSave
						.setScoreP1(currentLevel.getPlayer1().getScore() + 400);
			}
			PlayerSave.setExtraLiveP1(currentLevel.getPlayer1().getExtraLive());

			if (multiplayer) {
				PlayerSave.setLivesP2(currentLevel.getPlayer2().getLives());
				if (level == 0) {
					PlayerSave
							.setScoreP2(currentLevel.getPlayer2().getScore() + 100);
				} else if (level == 1) {
					PlayerSave
							.setScoreP2(currentLevel.getPlayer2().getScore() + 200);
				} else if (level == 2) {
					PlayerSave
							.setScoreP2(currentLevel.getPlayer2().getScore() + 300);
				} else if (level == 3) {
					PlayerSave
							.setScoreP2(currentLevel.getPlayer2().getScore() + 400);
				}
				PlayerSave.setExtraLiveP2(currentLevel.getPlayer2()
						.getExtraLive());
			}
			setupLevel(level + 1, multiplayer);
			Log.info("Player Action", "Player reached next level");
		}
	}

	/**
	 * Draw everything of level 1.
	 *
	 * @param gr
	 *            the gr
	 */
	@Override
	public final void draw(final Graphics2D gr) {
		gr.setColor(Color.BLACK);
		gr.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		bg.draw(gr);

		drawComponents(gr);

		if (paused) {
			gr.setColor(new Color(0, 0, 0, 180));
			gr.fillRect(0, 0, currentLevel.getTileMap().getWidth(),
					currentLevel.getTileMap().getHeight());
			gr.setColor(Color.WHITE);
			gr.drawString("PAUSED", 680, 26);
		}
	}

	/**
	 * Returns the current Level object.
	 * 
	 * @return - the current Level object.
	 */
	public final Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * keyPressed.
	 *
	 * @param k
	 *            the k
	 */
	@Override
	public final void keyPressed(final int k) {
		switch (k) {
		case KeyEvent.VK_LEFT:
			currentLevel.getPlayer1().setLeft(true);
			return;
		case KeyEvent.VK_RIGHT:
			currentLevel.getPlayer1().setRight(true);
			return;
		case KeyEvent.VK_UP:
			currentLevel.getPlayer1().setUp(true);
			return;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			currentLevel.getPlayer1().setDown(true);
			return;
		case KeyEvent.VK_A:
			if (multiplayer) {
				currentLevel.getPlayer2().setLeft(true);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				currentLevel.getPlayer2().setRight(true);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				currentLevel.getPlayer2().setUp(true);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				currentLevel.getPlayer2().setDown(true);
			}
			return;
		default:
			return;
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
		switch (k) {
		case KeyEvent.VK_LEFT:
			currentLevel.getPlayer1().setLeft(false);
			return;
		case KeyEvent.VK_RIGHT:
			currentLevel.getPlayer1().setRight(false);
			return;
		case KeyEvent.VK_UP:
			currentLevel.getPlayer1().setUp(false);
			return;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			currentLevel.getPlayer1().setDown(false);
			return;
		case KeyEvent.VK_A:
			if (multiplayer) {
				currentLevel.getPlayer2().setLeft(false);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				currentLevel.getPlayer2().setRight(false);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				currentLevel.getPlayer2().setUp(false);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				currentLevel.getPlayer2().setDown(false);
			}
			return;
		case KeyEvent.VK_ESCAPE:
			if (paused) {
				paused = false;
				AudioPlayer.resume(musicFileNames[level]);
			} else {
				paused = true;
				AudioPlayer.stop(musicFileNames[level]);
			}
			return;
		default:
			return;
		}
	}
}
