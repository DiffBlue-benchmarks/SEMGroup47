package sem.group47.entity.pickupobject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.MapObject;
import sem.group47.entity.MapObjectTest;
import sem.group47.entity.Player;
import sem.group47.entity.pickups.PickupObject;

public abstract class PickupObjectTest extends MapObjectTest {
	
	protected PickupObject pickupObject;
	protected Player player;
	
	@Override
	public MapObject supplyMapObject() {
		return supplyPickupObject();
	}
	
	public abstract PickupObject supplyPickupObject();
	
	@Before
	public void setup() {
		player = new Player(tileMap);
	}
	
	@Test
	public void CheckCollisionTest() {
		assertFalse(pickupObject.checkCollision(player));
		player.intersects(pickupObject);
		assertTrue(pickupObject.checkCollision(player));
	}
}