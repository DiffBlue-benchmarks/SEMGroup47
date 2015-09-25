package sem.group47.entity.pickups;

import sem.group47.entity.Player;
import sem.group47.tilemap.TileMap;

/**
 * Speeds up the player movement when picked up.
 * @author Christian
 *
 */
public class BubbleSizePowerup extends PickupObject {
 /**
  * constructor.
  * @param tm TileMap
  */
 public BubbleSizePowerup(final TileMap tm) {
  super(tm);
 }

 @Override
 protected final void onPickup(final Player p) {
  p.setBubbleSize(p.getBubbleSize() + 16);
 }

}
