package sem.group47.entity.enemies;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.MapObject;
import sem.group47.entity.MapObjectTest;
import sem.group47.entity.enemies.property.BaseEnemyProperty;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

public class EnemyTest extends MapObjectTest {

	protected Enemy enemy;
	private TileMap tileMap;
	
	/** The tile size. */
	private int tileSize = 30;
	
	/** The num of cols. */
	private int numOfCols = 2;
	
	/** The num of rows. */
	private int numOfRows = 2;
	
	@Before
	public void setUp() throws IOException {
		Log.setLog();
		AudioPlayer.init();
		
		tileMap = new TileMap(tileSize);
		
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		enemy = new Enemy(tileMap);
	}
	
	@Override
	public MapObject supplyMapObject() {
		return enemy;
	}
	
	@Test
	public void caughtTest() {
		assertEquals(enemy.isCaught(), false);
		assertEquals(enemy.isAngry(), false);
		enemy.setCaught(true);
		assertEquals(enemy.isCaught(), true);
		assertEquals(enemy.isAngry(), false);
	}
	
	@Test
	public void enemyAngryTest() {
		enemy.setIsAngry(true);
		assertTrue(enemy.getAngryStartTime() > 0);
	}
	
	@Test
	public void hitTest() {
		enemy.hit();
		assertTrue(enemy.isCaught());
	}
	
	@Test
	public void getPropertiesTest() {
		EnemyProperty setProp = new BaseEnemyProperty();
		enemy.setProperties(setProp);
		
		EnemyProperty getProp = enemy.getProperties();
		assertEquals(getProp.getPoints(), setProp.getPoints());
	}
	
}
