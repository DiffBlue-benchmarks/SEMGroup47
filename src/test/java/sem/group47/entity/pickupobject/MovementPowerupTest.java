package sem.group47.entity.pickupobject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;

public class MovementPowerupTest extends PickupObjectTest {
	
	MovementSpeedPowerup movement;
	
	@Before
	public void setup() {
		movement = new MovementSpeedPowerup(tileMap);
		super.setup();
	}
	
	@Override
	public PickupObject supplyPickupObject() {
		return movement;
	}
	
	@Test
	public void OnPickupTest() {
		movement.onPickup(player);
		int ms = (int) player.getMovSpeed() * 10;
		int maxs = (int) player.getMaxSpeed() * 10;
		assertEquals(ms, 10);
		assertEquals(maxs, 40);
	}
	
}
