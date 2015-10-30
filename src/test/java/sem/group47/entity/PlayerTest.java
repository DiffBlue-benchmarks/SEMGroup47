package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.gamestate.GameStateManager;
import sem.group47.gamestate.LevelState;
import sem.group47.main.Level;
import sem.group47.main.Log;

/**
 * The Class PlayerTest.
 */
public class PlayerTest extends MapObjectTest {
	
	/** The num of cols. */
	public int numOfCols = 2;
	
	/** The num of rows. */
	public int numOfRows = 2;
	
	/** The player. */
	public Player player;
	
	/** The player save state. */
	public PlayerSave playerSave;
	
	/** The projectile. */
	public Projectile projectile;
	
	/**
	 * SetUp.
	 * 
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@Before
	public final void setUp() throws IOException {
		
		Log.setLog();
		AudioPlayer.init();
		
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		player = new Player(tileMap);
		projectile = new Projectile(tileMap);
	}
	
	/**
	 * Update test.
	 */
	@Test
	public final void updateTest() {
		player.setDown(true);
		player.update();
		assertEquals(player.getProjectiles().getSize(), 1);
	}
	
	/**
	 * Hit test.
	 */
	@Test
	public final void hitTest() {
		player.hit(1);
		assertEquals(PlayerSave.getLivesP1(), 3);
	}
	
	/**
	 * Hit dead test.
	 */
	@Test
	public final void hitDeadTest() {
		player.hit(3);
		assertEquals(player.getIsAlive(), false);
		assertEquals(player.getLives(), 0);
	}
	
	/**
	 * Hit out of bounds test.
	 */
	@Test
	public final void hitOutOfBoundsTest() {
		player.hit(4);
		assertEquals(player.getIsAlive(), false);
		assertEquals(player.getLives(), 0);
	}
	
	/**
	 * Hit flinch test.
	 */
	@Test
	public final void hitFlinchTest() {
		player.setFlinch(true);
		player.hit(1);
		assertEquals(playerSave.getLivesP1(), 3);
	}
	
	/**
	 * Next position left test.
	 */
	@Test
	public final void nextPositionLeftTest() {
		player.setLeft(true);
		player.setMovSpeed(3.0);
		player.setMaxSpeed(2.0);
		player.getNextXPosition();
		assertEquals(player.getDx(), -2.0, 0.1);
	}
	
	/**
	 * Next position right test.
	 */
	@Test
	public final void nextPositionRightTest() {
		player.setRight(true);
		player.setMovSpeed(3.0);
		player.setMaxSpeed(2.0);
		player.getNextXPosition();
		assertEquals(player.getDx(), 2.0, 0.1);
	}
	
	/**
	 * Next position stop test.
	 */
	@Test
	public final void nextPositionStopTest() {
		player.setRight(false);
		player.setLeft(false);
		player.getNextXPosition();
		assertEquals(player.getDx(), 0, 0);
	}
	
	/**
	 * Next position up test.
	 */
	@Test
	public final void nextPositionUpTest() {
		player.setUp(true);
		player.getNextYPosition();
		assertTrue(player.isJumping());
	}
	
	/**
	 * Next position falling test.
	 */
	@Test
	public final void nextPositionFallingTest() {
		player.setFallSpeed(1);
		player.setFalling(true);
		player.getNextYPosition();
		assertTrue(!player.isJumping());
	}
	
	@Test
	public final void BubbleSpeedPowerup() {
		BubbleSpeedPowerup p = new BubbleSpeedPowerup(tileMap);
		p.onPickup(player);
		int bs = (int) player.getBubbleSpeed() * 10;
		assertEquals(bs, 90);
	}
	
	@Test
	public final void BubbleSizePowerup() {
		BubbleSizePowerup p = new BubbleSizePowerup(tileMap);
		p.onPickup(player);
		assertEquals(player.getBubbleSize(), 48);
	}
	
	@Test
	public final void MovementSpeedPowerup() {
		MovementSpeedPowerup p = new MovementSpeedPowerup(tileMap);
		p.onPickup(player);
		int ms = (int) player.getMovSpeed() * 10;
		int maxs = (int) player.getMaxSpeed() * 10;
		assertEquals(ms, 10);
		assertEquals(maxs, 40);
	}
	
	@Test
	public final void multiplayerTest() {
		GameStateManager gsm = GameStateManager.getInstance();
		PlayerSave.setMultiplayerEnabled(true);
		LevelState ls = new LevelState(gsm);
		ls.init();
		Level level = ls.getCurrentLevel();
		assertEquals(level.getPlayer1().getLives(), level.getPlayer2().getLives());
		assertEquals(level.getPlayer1().getScore(), level.getPlayer2().getScore());
		level.getPlayer1().hit(1);
		assertEquals(level.getPlayer1().getLives(), 2);
		assertEquals(level.getPlayer2().getLives(), 3);
		level.getPlayer1().hit(3);
		assertFalse(level.getPlayer1().getIsAlive());
		assertFalse(level.getPlayer2().getIsAlive());
	}
	
	@Override
	public MapObject supplyMapObject() {
		return player;
	}
	
}
