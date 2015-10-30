package sem.group47.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

import sem.group47.tilemap.TileMap;

public abstract class MapObjectTest {
	
	protected TileMap tileMap = new TileMap(30);
	protected MapObject mapobject;
	
	public abstract MapObject supplyMapObject();
	
	@Before
	public void setup() {
		mapobject = supplyMapObject();
	}
	
	@Test
	public void intersectsTest() {
		Rectangle r1 = new Rectangle(2, 2, 3, 3);
		Rectangle r2 = new Rectangle(4, 5, 6, 6);
		Rectangle r3 = new Rectangle(2, 2, 3, 3);
		assertFalse(r1.intersects(r2));
		assertTrue(r1.intersects(r3));
	}
	
}
