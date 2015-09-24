package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.Enemy;
import sem.group47.entity.HUD;
import sem.group47.entity.Player;
import sem.group47.entity.PlayerSave;
import sem.group47.entity.enemies.Level1Enemy;
import sem.group47.main.GamePanel;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class Level1State.
 */
public class Level1State extends GameState {

	/** The player. */
	private Player player;

	/** The enemies. */
	private ArrayList<Enemy> enemies;

	/** The hud. */
	private HUD hud;

	/** The tile map. */
	private TileMap tileMap;

	/** The audioPlayer. */
	private AudioPlayer bgMusic;

	/**
	 * Instantiates a new level1 state.
	 *
	 * @param gsm
	 *            the gsm
	 */
	public Level1State(final GameStateManager gsm) {
		setGsm(gsm);
		init();
	}

	/**
	 * Init.
	 */
	@Override
	public final void init() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tiles/Bubble_Tile.gif");
		tileMap.loadMap("/maps/level1.map");
		player = new Player(tileMap);
		player.setPosition(100d, 100d);
		player.setLives(PlayerSave.getLives());
		player.setScore(PlayerSave.getScore());
		player.setExtraLive(PlayerSave.getExtraLive());

		populateEnemies();
		hud = new HUD(player);

		try {
			AudioPlayer.load("/music/level1.wav", "level1");
			AudioPlayer.loop("level1");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * populate the game with enemies.
	 */
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();
		Point[] points = new Point[] { new Point(300, 100),
				new Point(500, 100), new Point(300, 250), new Point(500, 400),
				new Point(100, 550) };
		Level1Enemy e;
		for (int i = 0; i < points.length; i++) {
			e = new Level1Enemy(tileMap);
			e.setPosition(points[i].x, points[i].y);
			enemies.add(e);
		}
	}

	/**
	 * Update the player and enemies.
	 */
	@Override
	public final void update() {
		player.update();
		player.directEnemyCollision(enemies, getGsm());
		player.indirectEnemyCollision(enemies);

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}

		nextLevel();
	}

	/**
	 * Next level.
	 */
	public final void nextLevel() {
		if (enemies.size() == 0) {
			PlayerSave.setLives(player.getLives());
			PlayerSave.setScore(player.getScore());
			PlayerSave.setExtraLive(player.getExtraLive());
			System.out.println(PlayerSave.getExtraLive());
			getGsm().setState(GameStateManager.LEVEL2STATE);
			Log.info("Player Action", "Player reached next level");
			AudioPlayer.stop("level1");
		}
	}

	/**
	 * Draw everything of level 1.
	 */
	@Override
	public final void draw(final Graphics2D g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		tileMap.draw(g);
		player.draw(g);

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}

		hud.draw(g);
	}

	/**
	 * keyPressed.
	 */
	@Override
	public final void keyPressed(final int k) {
		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(true);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(true);
		}
		if (k == KeyEvent.VK_UP) {
			player.setUp(true);
		}
		if (k == KeyEvent.VK_DOWN) {
			player.setDown(true);
		}
	}

	/**
	 * keyReleased.
	 */
	@Override
	public final void keyReleased(final int k) {
		if (k == KeyEvent.VK_LEFT) {
			player.setLeft(false);
		}
		if (k == KeyEvent.VK_RIGHT) {
			player.setRight(false);
		}
		if (k == KeyEvent.VK_UP) {
			player.setUp(false);
		}
		if (k == KeyEvent.VK_DOWN) {
			player.setDown(false);
		}
	}

}
