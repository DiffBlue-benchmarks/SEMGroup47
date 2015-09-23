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
public class BubbleSpeedPowerup extends PickupObject {

 /**
  * constructor.
  * @param tm TileMap
  */
 public BubbleSpeedPowerup(final TileMap tm) {
  super(tm);
  try {
   BufferedImage spritesheet = ImageIO.read(getClass()
     .getResourceAsStream("/player/player.png"));
   setSprite(spritesheet.getSubimage(0, 0, 38, 32));
  } catch (Exception e) {
   Log.error("IO Read", "Could not file player sprite");
   e.printStackTrace();
  }
 }

 @Override
 protected final void onPickup(final Player p) {
  p.setBubbleSpeed(p.getBubbleSpeed() + 2.5f);
 }

}
