package sem.group47.entity.pickupobject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.pickups.Fruit;
import sem.group47.entity.pickups.PickupObject;

public class FruitTest extends PickupObjectTest {
	
	Fruit fruit;
	
	@Before
	public void setup() {
		fruit = new Fruit(tileMap);
		super.setup();
	}
	
	@Override
	public PickupObject supplyPickupObject() {
		return fruit;
	}
	
	@Test
	public void onPickupTest() {
		int i = player.getScore();
		fruit.onPickup(player);
		int y = player.getScore();
		int x = y - i;
		assertEquals(x, 25);
	}
}
