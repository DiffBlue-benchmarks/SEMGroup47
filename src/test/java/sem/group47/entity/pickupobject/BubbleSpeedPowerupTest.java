package sem.group47.entity.pickupobject;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;

public class BubbleSpeedPowerupTest extends PickupObjectTest {
	
	BubbleSpeedPowerup bubblespeed;
	
	@Before
	public void setup() {
		bubblespeed = new BubbleSpeedPowerup(tileMap);
	}
	
	@Override
	public PickupObject supplyPickupObject() {
		return bubblespeed;
	}
	
	@Test
	public void OnPickupTest(){
		bubblespeed.onPickup(player);
		int bs = (int) player.getBubbleSpeed() * 10;
		assertEquals(bs, 90);
	}
	
}
