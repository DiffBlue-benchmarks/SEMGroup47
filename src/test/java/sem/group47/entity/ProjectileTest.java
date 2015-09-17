package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.tilemap.TileMap;

public class ProjectileTest {
	private TileMap tileMap;
	private int tileSize = 30;

	private Projectile projectile;

	@Before
	public void setUp() throws IOException {
		tileMap = new TileMap(tileSize);
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
	}

	@Test
	public void testConstructor() {
		projectile = new Projectile(tileMap);
		assertEquals(projectile.getIsAlive(), true);
	}

	@Test
	public void testUpdate() {
		projectile = new Projectile(tileMap);
		projectile.update();
		assertTrue(projectile.getDx() > 0);
	}

}
