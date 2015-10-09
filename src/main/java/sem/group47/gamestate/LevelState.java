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
import sem.group47.entity.enemies.ProjectileEnemy;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.main.GamePanel;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class Level1State.
 */
public class LevelState extends GameState {

	/** level file names. **/
	private String[] levelFileNames = new String[] { "level1.map",
			"level2.map", "level3.map", "level4.map" };

	/** file names of music **/
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
	public Player player1;

	/** The player 2, only set when multiplayer is true. **/
	public Player player2;

	/** The enemies. */
	private ArrayList<Enemy> enemies;

	/** The hud. */
	private HUD hud;

	/** The tile map. */
	private TileMap tileMap;

	/** List of pickupobjects in the level. **/
	private ArrayList<PickupObject> pickups;

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

		pickups = new ArrayList<PickupObject>();
		int tileSize = tileMap.getTileSize();

		player1 = new Player(tileMap);
		player1.setPosition(tileSize * (2d + .5d) + 5,
				tileSize * (tileMap.getNumRows() - 2 + .5d));
		player1.setLives(PlayerSave.getLivesP1());
		player1.setScore(PlayerSave.getScoreP1());
		player1.setExtraLive(PlayerSave.getExtraLiveP1());

		if (multiplayer) {
			player2 = new Player(tileMap);
			player2.setPosition(
					tileSize * (tileMap.getNumCols() - 3 + .5d) - 5, tileSize
							* (tileMap.getNumRows() - 2 + .5d));
			player2.setLives(PlayerSave.getLivesP2());
			player2.setScore(PlayerSave.getScoreP2());
			player2.setExtraLive(PlayerSave.getExtraLiveP2());
			player2.setFacingRight(false);
		}
		hud = new HUD(player1, player2);

		populateEnemies();
		populatePowerups();
		AudioPlayer.stopAll();
		AudioPlayer.loop(musicFileNames[level]);
	}

	/**
	 * populate the game with enemies.
	 */
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		ArrayList<int[]> points = tileMap.getEnemyStartLocations();
		Enemy enemy;
		for (int i = 0; i < points.size(); i++) {
			switch(points.get(i)[2]) {
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
		}
	}

	/**
	 * loads the powerups.
	 */
	private void populatePowerups() {
		PickupObject po = new MovementSpeedPowerup(tileMap);
		po.setPosition(100, 100);
		pickups.add(po);
		po = new BubbleSizePowerup(tileMap);
		po.setPosition(tileMap.getWidth() - 100, 100);
		pickups.add(po);
		po = new BubbleSpeedPowerup(tileMap);
		po.setPosition(tileMap.getWidth() / 2, 100);
		pickups.add(po);
	}

	/**
	 * Update the player and enemies.
	 */
	@Override
	public final void update() {
		if (!paused) {
			if (player1.getLives() > 0) {
				player1.update();
				player1.directEnemyCollision(enemies, getGsm());
				player1.indirectEnemyCollision(enemies);
			}
			if (multiplayer && player2.getLives() > 0) {
				player2.update();
				player2.directEnemyCollision(enemies, getGsm());
				player2.indirectEnemyCollision(enemies);
			}

			lostCheck();

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update();
			}

			for (int i = 0; i < pickups.size(); i++) {
				if (pickups.get(i).checkCollision(player1)
						|| (multiplayer && pickups.get(i).checkCollision(
								player2))) {
					AudioPlayer.play("extraLife");
					pickups.remove(i);
					i--;
				} else {
					pickups.get(i).update();
				}
			}

			nextLevelCheck();
		}
	}

	/**
	 * checks if the player is dead.
	 */
	private void lostCheck() {
		if (player1.getLives() <= 0) {
			if (!multiplayer || player2.getLives() <= 0) {
				getGsm().setState(GameStateManager.GAMEOVERSTATE);
			}
		}
	}

	/**
	 * Next level.
	 */
	public final void nextLevelCheck() {
		if (enemies.size() == 0) {
			PlayerSave.setLivesP1(player1.getLives());
			PlayerSave.setScoreP1(player1.getScore());
			PlayerSave.setExtraLiveP1(player1.getExtraLive());
			if (multiplayer) {
				PlayerSave.setLivesP2(player2.getLives());
				PlayerSave.setScoreP2(player2.getScore());
				PlayerSave.setExtraLiveP2(player2.getExtraLive());
				System.out.println(PlayerSave.getExtraLiveP2());
			}
			setupLevel(level + 1);
			Log.info("Player Action", "Player reached next level");
		}
	}

	/**
	 * Draw everything of level 1.
	 */
	@Override
	public final void draw(final Graphics2D gr) {

		gr.setColor(Color.BLACK);
		gr.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		tileMap.draw(gr);

		for (int i = 0; i < pickups.size(); i++) {
			pickups.get(i).draw(gr);
		}
		if (player1.getLives() > 0) {
			player1.draw(gr);
		}
		if (multiplayer && player2.getLives() > 0) {
			player2.draw(gr);
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(gr);
		}

		hud.draw(gr);
		if (paused) {
			gr.setColor(new Color(0, 0, 0, 180));
			gr.fillRect(0, 0, tileMap.getWidth(), tileMap.getHeight());
			gr.setColor(Color.WHITE);
			gr.drawString("PAUSED", 680, 26);
		}
	}

	/**
	 * keyPressed.
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
				player2.setLeft(true);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				player2.setRight(true);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				player2.setUp(true);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				player2.setDown(true);
			}
			return;
		default:
			return;
		}
	}

	/**
	 * keyReleased.
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
				player2.setLeft(false);
			}
			return;
		case KeyEvent.VK_D:
			if (multiplayer) {
				player2.setRight(false);
			}
			return;
		case KeyEvent.VK_W:
			if (multiplayer) {
				player2.setUp(false);
			}
			return;
		case KeyEvent.VK_S:
			if (multiplayer) {
				player2.setDown(false);
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
