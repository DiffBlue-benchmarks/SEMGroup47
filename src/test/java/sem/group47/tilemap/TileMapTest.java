package sem.group47.tilemap;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import sem.group47.tilemap.TileMap;

/**
 * The Class TileMapTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class TileMapTest {

	/** The tile map. */
	private TileMap tileMap;

	/** The tile size. */
	private int tileSize = 30;

	/** The num of cols. */
	private int numOfCols = 2;

	/** The num of rows. */
	private int numOfRows = 2;

	/**
	 * Sets the up.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public void setUp() throws IOException {
		tileMap = new TileMap(tileSize);
		tileMap.loadTiles("test/Test_Tile.gif");
		tileMap.loadMap("test/Test_Map.map");
	}

	/**
	 * Check xand y.
	 */
	@Test
	public void checkXandY() {
		assertEquals(tileMap.getx(), 0);
		assertEquals(tileMap.gety(), 0);
	}

	/**
	 * Check width and heigth.
	 */
	@Test
	public void checkWidthAndHeigth() {
		assertEquals(tileMap.getWidth(), tileSize * numOfCols);
		assertEquals(tileMap.getHeight(), tileSize * numOfRows);
	}

	/**
	 * Check tile size.
	 */
	@Test
	public void checkTileSize() {
		assertEquals(tileMap.getTileSize(), tileSize);
	}

	/**
	 * Check type.
	 */
	@Test
	public void checkType() {
		// Tile 1
		assertEquals(tileMap.getType(0, 0), 0);
		// Tile 2
		assertEquals(tileMap.getType(0, 1), 0);
		// Tile 3
		assertEquals(tileMap.getType(1, 0), 1);
		// Tile 4
		assertEquals(tileMap.getType(1, 1), 1);

	}

	/**
	 * Check num of rows and cols.
	 */
	@Test
	public void checkNumOfRowsAndCols() {
		assertEquals(tileMap.getNumCols(), numOfCols);
		assertEquals(tileMap.getNumRows(), numOfRows);
	}

	/**
	 * Check load map.
	 */
	@Test
	public void checkLoadMap() {
		assertEquals(tileMap.getMap().length, numOfRows);
	}

	/**
	 * Check load tiles.
	 */
	@Test
	public void checkLoadTiles() {
		assertEquals(tileMap.getTiles().length, 3);
	}

}
