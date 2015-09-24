package sem.group47.entity.enemies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.Rectangle;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import sem.group47.tilemap.TileMap;

/**
 * The Class Level1EnemyTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class Level1EnemyTest {

	/** The tile map. */
	private TileMap tileMap;

	/** The tile size. */
	private int tileSize = 30;

	/** The enemy. */
	private Level1Enemy enemy;

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
		enemy = new Level1Enemy(tileMap);
		assertNotEquals(enemy.getCHeight(), 0);
		assertNotEquals(enemy.getCWidth(), 0);
		assertNotEquals(enemy.getWidth(), 0);
		assertNotEquals(enemy.getHeight(), 0);
		assertEquals(enemy.getIsAlive(), true);
	}

	/**
	 * Test get rectangle.
	 */
	@Test
	public final void testGetRectangle() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(200, 200);
		assertEquals(
				enemy.getRectangle(),
				new Rectangle((int) (enemy.getx() - enemy.getWidth() / 2),
						(int) (enemy.gety() - enemy.getHeight() / 2), enemy
								.getWidth(), enemy.getHeight()));
	}

	/**
	 * Test update.
	 */
	@Test
	public final void testUpdate() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(400, 100);
		enemy.setVector(0, 0);
		enemy.update();
		assertEquals(enemy.getIsAlive(), true);
	}

	/**
	 * Test hit.
	 */
	@Test
	public final void testHit() {
		enemy = new Level1Enemy(tileMap);
		assertEquals(enemy.isCaught(), false);
		enemy.hit();
		assertEquals(enemy.isCaught(), true);
	}

	/**
	 * Test set caught.
	 */
	@Test
	public final void testSetCaught() {
		enemy = new Level1Enemy(tileMap);
		assertEquals(enemy.isCaught(), false);
		enemy.setCaught();
		assertEquals(enemy.isCaught(), true);
	}

	/**
	 * Test set left.
	 */
	@Test
	public final void testSetLeft() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setLeft(true);
		enemy.setVector(-50, 0);
		enemy.update();
		assertNotEquals(enemy.getx(), 100d);
	}

	/**
	 * Test set right.
	 */
	@Test
	public final void testSetRight() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setRight(true);
		enemy.setVector(50, 0);
		enemy.update();
		assertNotEquals(enemy.getx(), 100d);
	}

	/**
	 * Test set up.
	 */
	@Test
	public final void testSetUp() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setUp(true);
		for (int i = 0; i < 100; i++) {
			enemy.update();
		}
		assertNotEquals(enemy.gety(), 100);
	}
}
