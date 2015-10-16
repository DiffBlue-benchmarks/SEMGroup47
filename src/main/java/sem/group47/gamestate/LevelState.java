package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.HUD;
import sem.group47.entity.Player;
import sem.group47.entity.PlayerSave;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.Level1Enemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.enemies.ProjectileEnemy;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.Fruit;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.main.GamePanel;
import sem.group47.main.Log;
import sem.group47.tilemap.Background;
import sem.group47.tilemap.TileMap;

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

	/** The players. **/
	private Player player1;

	/** The player 2, only set when multiplayer is true. **/
	private Player player2;

	/** The enemies. */
	private ArrayList<Enemy> enemies;

	/** The hud. */
	private HUD hud;

	/** The tile map. */
	private TileMap tileMap;

	/** The Background. */
	private Background bg;

	/** List of pickupobjects in the level. **/
	private ArrayList<PickupObject> pickups;

	/** Magiron. */
	private Magiron aaron;
	/** Time in seconds before Aaron appears. **/
	private static int AARON_APPEAR_DELAY = 45;

	/** Level step count. */
	private long levelStepCount;

	/**
	 * Instantiates a new level1 state.
	 *
	 * @param gsm
	 *            the gamestatemanager.
	 */
	public LevelState(final GameStateManager gsm) {
		setGsm(gsm);
	}

	/**
	 * Init.
	 */
	@Override
	public final void init() {
		PlayerSave.init();
		multiplayer = PlayerSave.getMultiplayerEnabled();
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tiles/Bubble_Tile.gif");
		level = 0;
		setupLevel(level);
		paused = false;
		bg = new Background();
	}

	/**
	 * Sets up a certain level.
	 *
	 * @param level
	 *            number of level to be set
	 */
	private void setupLevel(int level) {

		if (level >= levelFileNames.length) {
			level = 0;
		}
		this.level = level;
		tileMap.loadMap("/maps/" + levelFileNames[level]);

		clearComponents();

		addComponent(tileMap);

		pickups = new ArrayList<PickupObject>();
		int tileSize = tileMap.getTileSize();

		player1 = new Player(tileMap);
		player1.setPosition(tileSize * (2d + .5d) + 5,
				tileSize * (tileMap.getNumRows() - 2 + .5d));
		player1.setLives(PlayerSave.getLivesP1());

		player1.setExtraLive(PlayerSave.getExtraLiveP1());
		player1.setScore(PlayerSave.getScoreP1());

		addComponent(player1);

		if (multiplayer) {
			setPlayer2(new Player(tileMap));
			getPlayer2().setPosition(
					tileSize * (tileMap.getNumCols() - 3 + .5d) - 5,
					tileSize * (tileMap.getNumRows() - 2 + .5d));
			getPlayer2().setLives(PlayerSave.getLivesP2());
			getPlayer2().setExtraLive(PlayerSave.getExtraLiveP2());
			getPlayer2().setScore(PlayerSave.getScoreP2());
			getPlayer2().setFacingRight(false);
			addComponent(getPlayer2());
		}
		hud = new HUD(player1, getPlayer2());
		addComponent(hud);

		populateEnemies();
		populatePowerups();
		AudioPlayer.stopAll();
		AudioPlayer.loop(musicFileNames[level]);

		levelStepCount = 0;
	}

	/**
	 * populate the game with enemies.
	 */
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		ArrayList<int[]> points = tileMap.getEnemyStartLocations();
		Enemy enemy;
		int j = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			switch (points.get(i)[2]) {
			case Enemy.LEVEL1_ENEMY:
				enemy = new Level1Enemy(tileMap);
				break;
			case Enemy.PROJECTILE_ENEMEY:
				enemy = new ProjectileEnemy(tileMap);
				break;
			default:
				enemy = new Level1Enemy(tileMap);
			}
			enemy.setPosition((points.get(i)[0] + .5d) * 30,
					(points.get(i)[1] + 1) * 30 - .5d * enemy.getCHeight());
			enemies.add(enemy);
			addComponent(enemy);
			j = i;
		}

		aaron = new Magiron(tileMap);
		aaron.setPosition(GamePanel.WIDTH / 2, -150);
		addComponent(aaron);
	}

	/**
	 * loads the powerups.
	 */
	private void populatePowerups() {
		PickupObject po = new MovementSpeedPowerup(tileMap);
		po.setPosition(100, 100);
		pickups.add(po);
		addComponent(po);
		po = new BubbleSizePowerup(tileMap);
		po.setPosition(tileMap.getWidth() - 100, 100);
		pickups.add(po);
		addComponent(po);
		po = new BubbleSpeedPowerup(tileMap);
		po.setPosition(tileMap.getWidth() / 2, 100);
		pickups.add(po);
		addComponent(po);
	}

	/**
	 * Update the player and enemies.
	 */
	@Override
	public final void update() {
		if (!paused) {
			if (player1.getLives() > 0) {
				player1.update();
				directEnemyCollision(player1);
				player1.indirectEnemyCollision(enemies);
			} else {
				removeComponent(player1);
			}
			if (multiplayer) {
				if (getPlayer2().getLives() > 0) {
					getPlayer2().update();
					directEnemyCollision(getPlayer2());
					getPlayer2().indirectEnemyCollision(enemies);
				} else {
					removeComponent(getPlayer2());
				}
			}

			if (levelStepCount == GamePanel.FPS * AARON_APPEAR_DELAY) {
				targetAaron();
			}
			aaron.update();
			lostCheck();

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update();
				if (enemies.get(i).projectileCollision(player1)) {
					player1.kill();
				}
				if (multiplayer
						&& enemies.get(i).projectileCollision(getPlayer2())) {
					getPlayer2().kill();
				}
			}

			for (int i = 0; i < pickups.size(); i++) {
				if (pickups.get(i).checkCollision(player1)
						|| (multiplayer && pickups.get(i).checkCollision(
								getPlayer2()))) {
					AudioPlayer.play("extraLife");
					removeComponent(pickups.get(i));
					pickups.remove(i);

					i--;
				} else {
					pickups.get(i).update();
				}
			}

			nextLevelCheck();
			levelStepCount++;
		}
	}

	/**
	 * checks if the player is dead.
	 */
	private void lostCheck() {
		if (player1.getLives() <= 0) {
			removeComponent(player1);
			if (!multiplayer || getPlayer2().getLives() <= 0) {

				PlayerSave.setScoreP1(player1.getScore());
				if (multiplayer) {
					PlayerSave.setScoreP2(getPlayer2().getScore());
				}

				getGsm().setState(GameStateManager.HIGHSCORESTATE);
			}
		} else if (multiplayer && getPlayer2().getLives() <= 0) {
			removeComponent(getPlayer2());
		}
	}

	/**
	 * Next level.
	 */
	public final void nextLevelCheck() {
		if (enemies.size() == 0) {
			PlayerSave.setLivesP1(player1.getLives());
			if (level == 0) {
				PlayerSave.setScoreP1(player1.getScore() + 100);
			} else if (level == 1) {
				PlayerSave.setScoreP1(player1.getScore() + 200);
			} else if (level == 2) {
				PlayerSave.setScoreP1(player1.getScore() + 300);
			} else if (level == 3) {
				PlayerSave.setScoreP1(player1.getScore() + 400);
			}
			PlayerSave.setExtraLiveP1(player1.getExtraLive());

			if (multiplayer) {
				PlayerSave.setLivesP2(getPlayer2().getLives());
				if (level == 0) {
					PlayerSave.setScoreP2(getPlayer2().getScore() + 100);
				} else if (level == 1) {
					PlayerSave.setScoreP2(getPlayer2().getScore() + 200);
				} else if (level == 2) {
					PlayerSave.setScoreP2(getPlayer2().getScore() + 300);
				} else if (level == 3) {
					PlayerSave.setScoreP2(getPlayer2().getScore() + 400);
				}
				PlayerSave.setExtraLiveP2(getPlayer2().getExtraLive());
			}
			setupLevel(level + 1);
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
			gr.fillRect(0, 0, tileMap.getWidth(), tileMap.getHeight());
			gr.setColor(Color.WHITE);
			gr.drawString("PAUSED", 680, 26);
		}
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
			player1.setLeft(true);
			return;
		case KeyEvent.VK_RIGHT:
			player1.setRight(true);
			return;
		case KeyEvent.VK_UP:
			player1.setUp(true);
			return;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			player1.setDown(true);
			return;
		case KeyEvent.VK_A:
			if (multiplayer) {
				getPlayer2().setLeft(true);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				getPlayer2().setRight(true);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				getPlayer2().setUp(true);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				getPlayer2().setDown(true);
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
			player1.setLeft(false);
			return;
		case KeyEvent.VK_RIGHT:
			player1.setRight(false);
			return;
		case KeyEvent.VK_UP:
			player1.setUp(false);
			return;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_SPACE:
			player1.setDown(false);
			return;
		case KeyEvent.VK_A:
			if (multiplayer) {
				getPlayer2().setLeft(false);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				getPlayer2().setRight(false);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				getPlayer2().setUp(false);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				getPlayer2().setDown(false);
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

	/**
	 * Target aaron.
	 */
	private void targetAaron() {
		if (multiplayer) {
			if (player1.getLives() > 0) {
				if (Math.random() > .5d || getPlayer2().getLives() <= 0) {
					aaron.setTarget(player1);
				} else {
					aaron.setTarget(getPlayer2());
				}
			} else {
				aaron.setTarget(getPlayer2());
			}
		} else {
			aaron.setTarget(player1);
		}
	}

	/**
	 * checks what happens when the player directly collides with an enemy.
	 *
	 * @param player
	 *            the Player object to check collisions with
	 */
	public final void directEnemyCollision(final Player player) {
		if (player.intersects(aaron)) {
			player.kill();
			targetAaron();
		}

		for (int i = 0; i < enemies.size(); i++) {

			if (player.intersects(enemies.get(i))) {
				if (enemies.get(i).isCaught()) {
					Fruit fr = new Fruit(tileMap);

					if (enemies.get(i).getXpos() > 400) {
						fr.setPosition(enemies.get(i).getXpos() - 100, enemies
								.get(i).getYpos());
					} else {
						fr.setPosition(enemies.get(i).getXpos() + 100, enemies
								.get(i).getYpos());
					}
					pickups.add(fr);
					addComponent(fr);

					player.setScore(enemies.get(i).getScorePoints());
					removeComponent(enemies.get(i));
					enemies.remove(i);

					Log.info("Player Action",
							"Player collision with Caught Enemy");

				} else if (player.getLives() > 1) {
					player.hit(1);
					Log.info("Player Action", "Player collision with Enemy");

				} else {
					AudioPlayer.play("crash");
					player.hit(1);
					Log.info("Player Action", "Player collision with Enemy");
				}
			}
		}

	}

	/**
	 * Gets the player2.
	 *
	 * @return the player2
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * Sets the player2.
	 *
	 * @param pPlayer2
	 *            the new player2
	 */
	public void setPlayer2(Player pPlayer2) {
		this.player2 = pPlayer2;
	}

	/**
	 * Gets the player1.
	 *
	 * @return the player1
	 */
	public Player getPlayer1() {
		return player2;
	}

	/**
	 * Sets the player1.
	 *
	 * @param pPlayer2
	 *            the new player1
	 */
	public void setPlayer1(Player pPlayer2) {
		this.player2 = pPlayer2;
	}

}
