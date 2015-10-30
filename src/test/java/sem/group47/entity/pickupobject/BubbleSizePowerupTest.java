package sem.group47.entity.pickupobject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.PickupObject;

public class BubbleSizePowerupTest extends PickupObjectTest {
	
	BubbleSizePowerup bubblesize;
	
	@Before
	public void setup() {
		bubblesize = new BubbleSizePowerup(tileMap);
		super.setup();
	}
	
	@Override
	public PickupObject supplyPickupObject() {
		return bubblesize;
	}
	
	@Test
	public void OnPickupTest() {
		bubblesize.onPickup(player);
		assertEquals(player.getBubbleSize(), 48);
	}
}
