package sem.group47.entity.enemies;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.MapObject;
import sem.group47.entity.MapObjectTest;
import sem.group47.entity.Player;
import sem.group47.entity.Projectile;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

public class EnemyProjectileTest extends MapObjectTest {

	EnemyProjectile projectile;
	TileMap tileMap;
	
	@Before
	public final void setUp() throws IOException {
		
		Log.setLog();
		AudioPlayer.init();
		
		tileMap = new TileMap(30);
		
		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		projectile = new EnemyProjectile(tileMap);
		
	}
	
	@Override
	public MapObject supplyMapObject() {
		return projectile;
	}
	
	@Test
	public final void updateTest() {
		double prevX = projectile.getx();
		projectile.update();
		double newX = projectile.getx();
		Assert.assertNotEquals(prevX, newX);
		
		prevX = projectile.getx();
		projectile.setDx(projectile.getDx()*-1);
		projectile.update();
		newX = projectile.getx();
		Assert.assertNotEquals(prevX, newX);
	}
}
