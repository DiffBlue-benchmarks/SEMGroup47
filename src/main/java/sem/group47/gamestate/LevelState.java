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

	private Magiron aaron;

	public static double time = System.currentTimeMillis();

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

		clearComponents();

		addComponent(tileMap);

		pickups = new ArrayList<PickupObject>();
		int tileSize = tileMap.getTileSize();

		player1 = new Player(tileMap);
		player1.setPosition(tileSize * (2d + .5d) + 5,
				tileSize * (tileMap.getNumRows() - 2 + .5d));
		player1.setLives(PlayerSave.getLivesP1());
		player1.setScore(PlayerSave.getScoreP1());
		player1.setExtraLive(PlayerSave.getExtraLiveP1());

		addComponent(player1);

		if (multiplayer) {
			player2 = new Player(tileMap);
			player2.setPosition(
					tileSize * (tileMap.getNumCols() - 3 + .5d) - 5, tileSize
							* (tileMap.getNumRows() - 2 + .5d));
			player2.setLives(PlayerSave.getLivesP2());
			player2.setScore(PlayerSave.getScoreP2());
			player2.setExtraLive(PlayerSave.getExtraLiveP2());
			player2.setFacingRight(false);
			addComponent(player2);
		}
		hud = new HUD(player1, player2);
		addComponent(hud);

		aaron = new Magiron(tileMap);

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
		aaron.setPosition((points.get(j)[0] + .5d) * 30, (points.get(j)[1] + 1)
				* 30 - .5d * aaron.getCHeight());
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
			// if (System.currentTimeMillis() - time > 89999) {
			addComponent(aaron);
			// }
			if (player1.getLives() > 0) {
				player1.update();
				directEnemyCollision(player1);
				player1.indirectEnemyCollision(enemies);
			} else {
				removeComponent(player1);
			}
			if (multiplayer) {
				if (player2.getLives() > 0) {
					player2.update();
					directEnemyCollision(player2);
					player2.indirectEnemyCollision(enemies);
				} else {
					removeComponent(player2);
				}
			}
			aaron.update();
			lostCheck();

			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).update();
				if (enemies.get(i).projectileCollision(player1))
					player1.kill();
				if (multiplayer && enemies.get(i).projectileCollision(player2))
					player2.kill();
			}

			for (int i = 0; i < pickups.size(); i++) {
				if (pickups.get(i).checkCollision(player1)
						|| (multiplayer && pickups.get(i).checkCollision(
								player2))) {
					AudioPlayer.play("extraLife");
					removeComponent(pickups.get(i));
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
			removeComponent(player1);
			if (!multiplayer || player2.getLives() <= 0) {
				getGsm().setState(GameStateManager.GAMEOVERSTATE);
			}
			time = System.currentTimeMillis();
		} else if (multiplayer && player2.getLives() <= 0) {
			removeComponent(player2);
			time = System.currentTimeMillis();
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
			time = System.currentTimeMillis();
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

	/**
	 * checks what happens when the player directly collides with an enemy.
	 * 
	 * @param player
	 *            the Player object to check collisions with
	 */
	public final void directEnemyCollision(Player player) {
		if (player.intersects(aaron)) {
			player.kill();
		}

		for (int i = 0; i < enemies.size(); i++) {

			if (player.intersects(enemies.get(i))) {
				if (enemies.get(i).isCaught()) {

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

}
