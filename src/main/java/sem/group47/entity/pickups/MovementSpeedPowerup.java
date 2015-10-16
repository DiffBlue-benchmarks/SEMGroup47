package sem.group47.entity.pickups;

import sem.group47.entity.Player;
import sem.group47.tilemap.TileMap;

/**
 * Speeds up the player movement when picked up.
 *
 * @author Christian
 *
 */
public class MovementSpeedPowerup extends PickupObject {

	/**
	 * constructor.
	 *
	 * @param tm
	 *            TileMap
	 */
	public MovementSpeedPowerup(final TileMap tm) {
		super(tm);
	}

	@Override
	public final void onPickup(final Player p) {
		p.setMaxSpeed(p.getMaxSpeed() + 1.8);
		p.setMovSpeed(getMovSpeed() + 1.8);
	}
}
