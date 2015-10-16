package sem.group47.entity.pickups;

import sem.group47.entity.Player;
import sem.group47.tilemap.TileMap;

/**
 * Speeds up the player movement when picked up.
 *
 * @author Christian
 *
 */
public class BubbleSpeedPowerup extends PickupObject {

	/**
	 * constructor.
	 *
	 * @param tm
	 *            TileMap
	 */
	public BubbleSpeedPowerup(final TileMap tm) {
		super(tm);
	}

	@Override
	public final void onPickup(final Player p) {
		p.setBubbleSpeed(p.getBubbleSpeed() + 4.5f);
	}

}
