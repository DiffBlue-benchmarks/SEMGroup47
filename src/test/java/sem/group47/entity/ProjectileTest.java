package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

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
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public final void setUp() throws IOException {
		tileMap = new TileMap(tileSize);
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
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
	public final void testUpdate() {
		projectile = new Projectile(tileMap);
		projectile.update();
		assertTrue(projectile.getDx() > 0);
	}

	@Override
	public MapObject supplyMapObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
