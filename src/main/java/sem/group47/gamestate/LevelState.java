package sem.group47.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
public class LevelState extends GameState {

 /** level file names. **/
 private String[] levelFileNames = new String[] {"level1.map", "level2.map"};

 /** Current level. **/
 private int level;

 /** Whether multiplayer is on. **/
 private boolean multiplayer;

 /** Paused flag.
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
	 multiplayer = PlayerSave.getMultiplayerEnabled();
	 System.out.println("mp: " + multiplayer);
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tiles/Bubble_Tile.gif");
		level = 0;
		setupLevel(level);
  hud = new HUD(player1);
  paused = false;
	}

	/**
	 * Sets up a certain level.
	 * @param level number of level to be set
	 */
	private void setupLevel(int level) {
	 if (level >= levelFileNames.length) {
	  level = 0;
	 }
	 this.level = level;
	 tileMap.loadMap("/maps/" + levelFileNames[level]);

	 int tileSize = tileMap.getTileSize();

  player1 = new Player(tileMap);
  player1.setPosition(
    tileSize * (2d + .5d) + 5,
    tileSize * (tileMap.getNumRows() - 2 + .5d));
  player1.setLives(PlayerSave.getLives());
  player1.setScore(PlayerSave.getScore());
  player1.setExtraLive(PlayerSave.getExtraLive());

  if (multiplayer) {
   player2 = new Player(tileMap);
   player2.setPosition(
     tileSize * (tileMap.getNumCols() - 3 + .5d) - 5,
     tileSize * (tileMap.getNumRows() - 2 + .5d));
   player2.setLives(PlayerSave.getLives());
   player2.setScore(PlayerSave.getScore());
   player2.setExtraLive(PlayerSave.getExtraLive());
   player2.setFacingRight(false);
  }

  populateEnemies();
	}

	/**
	 * populate the game with enemies.
	 */
	private void populateEnemies() {
		enemies = new ArrayList<Enemy>();

		ArrayList<Point> points = tileMap.getEnemyStartLocations();
		Level1Enemy enemy;
		for (int i = 0; i < points.size(); i++) {
			enemy = new Level1Enemy(tileMap);
			enemy.setPosition(
			  (points.get(i).x + .5d) * 30d,
			  (points.get(i).y + .5d) * 30d);
			enemies.add(enemy);
		}
	}

	/**
	 * Update the player and enemies.
	 */
	@Override
	public final void update() {
	 if (!paused) {
 		player1.update();
 		player1.directEnemyCollision(enemies, getGsm());
 		player1.indirectEnemyCollision(enemies);

 		if (multiplayer) {
 		 player2.update();
 		 player2.directEnemyCollision(enemies, getGsm());
 		 player2.indirectEnemyCollision(enemies);
 		}

 		for (int i = 0; i < enemies.size(); i++) {
 			enemies.get(i).update();
 		}

 		nextLevelCheck();
	 }
	}

	/**
	 * Next level.
	 */
	public final void nextLevelCheck() {
		if (enemies.size() == 0) {
			PlayerSave.setLives(player1.getLives());
			PlayerSave.setScore(player1.getScore());
			PlayerSave.setExtraLive(player1.getExtraLive());
			System.out.println(PlayerSave.getExtraLive());
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
		player1.draw(gr);
		if (multiplayer) {
		 player2.draw(gr);
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(gr);
		}

		hud.draw(gr);
		if (paused) {
		 gr.setColor(new Color(0, 0, 0, 180));
		 gr.fillRect(0,  0, tileMap.getWidth(), tileMap.getHeight());
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
   } else {
    paused = true;
   }
   return;
  default:
	  return;
  }
	}

}
