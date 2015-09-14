package sem.sem_group47.entity.enemies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import sem.sem_group47.tilemap.TileMap;

import java.awt.Rectangle;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class Level1EnemyTest {

	private TileMap tileMap;
	private int tileSize = 30;
	
	private Level1Enemy enemy;
	
	@Before
	public void setUp() throws IOException {
		tileMap = new TileMap(tileSize);
		tileMap.loadTiles("/Test/Test_Tile.gif");
		tileMap.loadMap("/Test/Test_Map.map");
	}
	
	@Test 
	public void testConstructor() {
		enemy = new Level1Enemy(tileMap);
		assertNotEquals(enemy.getCHeight(), 0);
		assertNotEquals(enemy.getCWidth(), 0);
		assertNotEquals(enemy.getWidth(), 0);
		assertNotEquals(enemy.getHeight(), 0);
		assertEquals(enemy.getIsAlive(), true);
	}
	
	@Test
	public void testGetRectangle() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(200, 200);
		assertEquals(
				enemy.getRectangle(), 
				new Rectangle(
						(int) (enemy.getx()-enemy.getWidth()/2), 
						(int) (enemy.gety()-enemy.getHeight()/2), 
						enemy.getWidth(), 
						enemy.getHeight()
					)
		);
	}
	
	@Test
	public void testUpdate() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(400, 100);
		enemy.setVector(0, 0);
		enemy.update();
		assertEquals(enemy.getIsAlive(), true);
	}

	@Test
	public void testHit() {
		enemy = new Level1Enemy(tileMap);
		assertEquals(enemy.isCaught(), false);
		enemy.hit();
		assertEquals(enemy.isCaught(), true);
	}
	
	@Test
	public void testSetCaught() {
		enemy = new Level1Enemy(tileMap);
		assertEquals(enemy.isCaught(), false);
		enemy.setCaught();
		assertEquals(enemy.isCaught(), true);
	}
	
	@Test
	public void testSetLeft() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setLeft(true);
		enemy.setVector(-50, 0);
		enemy.update();
		assertNotEquals(enemy.getx(), 100d);
	}
	
	@Test
	public void testSetRight() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setRight(true);
		enemy.setVector(50, 0);
		enemy.update();
		assertNotEquals(enemy.getx(), 100d);
	}
	
	@Test
	public void testSetUp() {
		enemy = new Level1Enemy(tileMap);
		enemy.setPosition(100, 100);
		enemy.setUp(true);
		for(int i = 0; i < 100; i++)
			enemy.update();
		assertNotEquals(enemy.gety(), 100);
	}
}
