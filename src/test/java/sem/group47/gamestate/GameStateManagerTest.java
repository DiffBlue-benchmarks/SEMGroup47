package sem.group47.gamestate;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

// TODO: Auto-generated Javadoc
/**
 * The Class GameStateManagerTest.
 */
public class GameStateManagerTest {

	/** The g2d. */
	private Graphics2D g2d;

	/** The gsm. */
	public GameStateManager gsm;

	public TileMap tileMap;

	/**
	 * Sets the up.
	 */
	@Before
	public final void setUp() {
		Log.setLog();
		BufferedImage image = new BufferedImage(810, 600,
				BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
	}

	/**
	 * Test game update.
	 */
	@Test
	public final void testGameUpdate() {
		gsm = GameStateManager.getInstance();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVELSTATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVELSTATE);
		for (int i = 0; i < 6000; i++) {
			gsm.update();
		}
	}

	/**
	 * Test game update and draw.
	 */
	@Test
	public final void testGameUpdateAndDraw() {
		gsm = GameStateManager.getInstance();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVELSTATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVELSTATE);
		for (int i = 0; i < 6000; i++) {
			gsm.update();
			gsm.draw(g2d);
		}
	}

	// @Test
	// public final void testMultiPlayer() {
	// PlayerSave.setMultiplayerEnabled(true);
	// LevelState ls = new LevelState(gsm);
	// ls.player1 = new Player(tileMap);
	// ls.player2 = new Player(tileMap);
	// assertEquals(ls.player1.getLives(), ls.player2.getLives());
	// assertEquals(ls.player1.getScore(), ls.player2.getScore());
	// ls.player1.hit(1);
	// assertEquals(ls.player1.getLives(), 2);
	// assertEquals(ls.player2.getLives(), 3);
	// ls.player2.hit(4);
	// assertEquals(ls.player2.getIsAlive(), false);
	// assertEquals(ls.player1.getIsAlive(), true);
	// }

}
