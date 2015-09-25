package sem.group47.entity.pickups;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.entity.Player;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Speeds up the player movement when picked up.
 * @author Christian
 *
 */
public class MovementSpeedPowerup extends PickupObject {

 /**
  * constructor.
  * @param tm TileMap
  */
 public MovementSpeedPowerup(final TileMap tm) {
  super(tm);
 }

 @Override
 protected final void onPickup(final Player p) {
  p.setMaxSpeed(p.getMaxSpeed() + 1.8);
  p.setMovSpeed(getMovSpeed() + 1.8);
 }

}
