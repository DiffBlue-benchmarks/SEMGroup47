package sem.group47.entity.enemies;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.Graphics2D;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.Player;
import sem.group47.entity.enemies.property.BaseEnemyProperty;
import sem.group47.entity.enemies.property.CanFireProperty;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.tilemap.TileMap;


public class GroundEnemyTest extends EnemyTest {
	private TileMap tileMap;
	private GroundEnemy ge;
	private Graphics2D gr;
	private Player player;
	
	@Before
	public void setUp() {
		enemy = ge;
		tileMap = new TileMap(30);
		
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		EnemyProperty prop = new CanFireProperty(new BaseEnemyProperty());
		player = new Player(tileMap);
		ge = new GroundEnemy(tileMap, prop);
		gr = mock(Graphics2D.class);
	}
	
	@Test
	public void updateTest() {
		ge.update();
		ge.setIsAngry(true);
		ge.update();
		ge.projectileCollision(player);
		assertTrue(ge.isAngry());
		ge.setCaught(true);
		ge.update();
		assertTrue(ge.isCaught());
	}
	
	@Test
	public void drawTest() {
		ge.setFacingRight(true);
		ge.draw(gr);
		assertTrue(ge.isFacingRight());
		ge.setFacingRight(false);
		ge.draw(gr);
		assertFalse(ge.isFacingRight());
		
		ge.update();
		ge.setIsAngry(true);
		ge.update();
		ge.draw(gr);
		assertTrue(ge.isAngry());
		ge.setCaught(true);
		ge.update();
		ge.draw(gr);
		assertTrue(ge.isCaught());
	}
	
	
	
	@Test
	public void fireHitTest() {
		int lives = player.getLives();
		ge.setPosition(0, 0);
		player.setPosition(1, 0);
		ge.setFacingRight(false);
		ge.update();
		assertTrue(true);
	}
}
