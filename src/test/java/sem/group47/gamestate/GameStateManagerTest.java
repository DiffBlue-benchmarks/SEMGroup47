package sem.group47.gamestate;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import sem.group47.main.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class GameStateManagerTest.
 */
public class GameStateManagerTest {

	/** The g2d. */
	private Graphics2D g2d;

	/** The gsm. */
	public GameStateManager gsm;

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
		gsm = new GameStateManager();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVEL1STATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVEL1STATE);
		for (int i = 0; i < 6000; i++) {
			gsm.update();
		}
	}

	/**
	 * Test game update and draw.
	 */
	@Test
	public final void testGameUpdateAndDraw() {
		gsm = new GameStateManager();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVEL1STATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVEL1STATE);
		for (int i = 0; i < 6000; i++) {
			gsm.update();
			gsm.draw(g2d);
		}
	}

}
