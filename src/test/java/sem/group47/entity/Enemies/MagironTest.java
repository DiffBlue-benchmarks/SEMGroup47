package sem.group47.entity.enemies;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.awt.Graphics2D;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.MapObject;
import sem.group47.entity.Player;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;


public class MagironTest extends EnemyTest {
	private Magiron magiron;
	private TileMap tileMap;
	private Player player;
	
	private Graphics2D gr;
	
	@Before
	public final void setUp() throws IOException {
		Log.setLog();
		AudioPlayer.init();

		gr = mock(Graphics2D.class);
		tileMap = new TileMap(30);
		
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		magiron = new Magiron(tileMap);
		
		player = new Player(tileMap);
	}
	
	@Override
	public MapObject supplyMapObject() {
		return magiron;
	}
	
	@Test
	public final void drawTest() {
		magiron.setFacingRight(true);
		magiron.draw(gr);
		assertTrue(magiron.isFacingRight());
		magiron.setFacingRight(false);
		magiron.draw(gr);
		assertFalse(magiron.isFacingRight());
	}
	
	@Test
	public final void updateTest() {
		magiron.setPosition(-100, 100);
		player.setPosition(50, 200);
		double prevX, newX;
		double prevY, newY;
		prevX = magiron.getx();
		prevY = magiron.gety();
		magiron.update();
		newX = magiron.getx();
		newY = magiron.gety();
		assertEquals(prevX, newX, 0.001d);
		assertEquals(prevY, newY, 0.001d);
		
		magiron.setTarget(player);
		prevX = magiron.getx();
		prevY = magiron.gety();
		magiron.update();
		newX = magiron.getx();
		newY = magiron.gety();
		assertTrue (newX != prevX || newY != prevY);
		
		magiron.setTarget(null);
		player.setPosition(-100, 100);
		magiron.setPosition(50, 200);
		prevX = magiron.getx();
		prevY = magiron.gety();
		magiron.update();
		newX = magiron.getx();
		newY = magiron.gety();
		assertEquals(prevX, newX, 0.001d);
		assertEquals(prevY, newY, 0.001d);
		
		magiron.setTarget(player);
		prevX = magiron.getx();
		prevY = magiron.gety();
		magiron.update();
		newX = magiron.getx();
		newY = magiron.gety();
		assertTrue (newX != prevX || newY != prevY);
	}
}
