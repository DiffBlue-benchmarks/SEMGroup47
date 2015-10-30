package sem.group47.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Rectangle;

import org.junit.Before;
import org.junit.Test;

import sem.group47.tilemap.TileMap;

public abstract class MapObjectTest {
	
	protected TileMap tm = new TileMap(30);
	protected MapObject mapobject;
	
	public abstract MapObject supplyMapObject();
	
	@Before
	public void setup() {
		mapobject = supplyMapObject();
	}
	
	// doet het niet
	// @Test
	// public void getRectangleTest() {
	// mapobject.setCheight(2);
	// mapobject.setCwidth(2);
	// mapobject.setPosition(3, 3);
	// Rectangle r1 = new Rectangle(2, 2, 3, 3);
	// Rectangle r2 = mapobject.getRectangle();
	// System.out.println(r2);
	// assertEquals(r2, r1);
	// }
	
	@Test
	public void intersectsTest() {
		Rectangle r1 = new Rectangle(2, 2, 3, 3);
		Rectangle r2 = new Rectangle(4, 5, 6, 6);
		Rectangle r3 = new Rectangle(2, 2, 3, 3);
		assertFalse(r1.intersects(r2));
		assertTrue(r1.intersects(r3));
	}
	
	// Doet het niet
	// @Test
	// public void calculateCornersTest() {
	// TileMap tilemap = Mockito.mock(TileMap.class);
	// int row = 0;
	// int col = 0;
	// Mockito.when(tilemap.getType(row, col)).thenReturn(2);
	//
	// mapobject.setCheight(2);
	// mapobject.setCwidth(2);
	// mapobject.setPosition(3, 3);
	// row = 3;
	// col = 3;
	//
	// mapobject.calculateCorners(row, col);
	//
	// assertTrue(mapobject.topLeftBlocked);
	// assertTrue(mapobject.topRightBlocked);
	// assertTrue(mapobject.bottomLeftBlocked);
	// assertTrue(mapobject.bottomRightBlocked);
	//
	// Mockito.when(tilemap.getType(row, col)).thenReturn(0);
	//
	// mapobject.calculateCorners(row, col);
	//
	// assertFalse(mapobject.topLeftBlocked);
	// assertFalse(mapobject.topRightBlocked);
	// assertFalse(mapobject.bottomLeftBlocked);
	// assertFalse(mapobject.bottomRightBlocked);
	//
	// Mockito.when(tilemap.getType(row, col)).thenReturn(1);
	//
	// mapobject.calculateCorners(row, col);
	//
	// assertFalse(mapobject.bottomLeftSemiBlocked);
	// assertFalse(mapobject.bottomRightSemiBlocked);
	// }
	
	@Test
	public void checkYCollisionTest() {
		
	}
	
	@Test
	public void checkXCollisionTest() {
		
	}
	
	@Test
	public void CheckTileMapCollisionsTest() {
		
	}
	
	@Test
	public void drawTest() {
		
	}
	
}
