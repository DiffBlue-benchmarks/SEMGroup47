package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class ProjectileTest.
 */
public class ProjectileTest extends MapObjectTest {
	
	/** The tile map. */
	private TileMap tileMap;
	
	/** The tile size. */
	private int tileSize = 30;
	
	/** The projectile. */
	private Projectile projectile;
	
	/**
	 * Sets the up.
	 * 
	 * @throws IOException
	 *            Signals that an I/O exception has occurred.
	 */
	@Before
	public final void setUp() throws IOException {
		tileMap = new TileMap(tileSize);
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		projectile = new Projectile(tileMap);
	}
	
	/**
	 * Test constructor.
	 */
	@Test
	public final void testConstructor() {
		projectile = new Projectile(tileMap);
		assertEquals(projectile.getIsAlive(), true);
	}
	
	/**
	 * Test update.
	 */
	@Test
	public final void updateTest() {
		double prevX = projectile.getDx();
		projectile.update();
		assertTrue(projectile.getDx() != prevX);
	}
	
	@Test
	public final void floatTest() {
		double prevY = projectile.getDy();
		projectile.setFloatDelay(0);
		projectile.update();
		assertTrue(projectile.getDy() != prevY);
	}

	@Override
	public MapObject supplyMapObject() {
		return projectile;
	}
	
}
