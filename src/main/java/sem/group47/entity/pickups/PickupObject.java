package sem.group47.entity.pickups;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.entity.MapObject;
import sem.group47.entity.Player;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Object which can be pickedup by the player and has a certain effect
 * @author Christian
 *
 */
public abstract class PickupObject extends MapObject {

 /**
  * constructor
  * @param tm TileMap
  */
 public PickupObject(final TileMap tm) {
  super(tm);
  setWidth(15);
  setHeight(15);
  setCwidth(15);
  setCheight(15);
  setFallSpeed(.35);
  setMaxFallSpeed(6.0);
 }

 /**
  * Checks whether the object collides with the player, if so executes onPickup(p), activating the powerup.
  * @param p Player object
  * @return true if collision occurred
  */
 public final boolean checkCollision(final Player p) {
  if (p.intersects(this)) {
   onPickup(p);
   return true;
  }
  return false;
 }

 /**
  * Effect of getting picked up.
  * @param p Player object
  */
 protected abstract void onPickup(final Player p);

 @Override
 public final void update() {
  setDy(getDy() + getFallSpeed());
  checkTileMapCollision();
  setPosition(getXposNew(), getYposNew());
 }
}
