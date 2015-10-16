package sem.group47.entity.pickups;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.entity.MapObject;
import sem.group47.entity.Player;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Object which can be pickedup by the player and has a certain effect
 *
 * @author Christian
 *
 */
public abstract class PickupObject extends MapObject {

	/**
	 * constructor
	 *
	 * @param tm
	 *            TileMap
	 */
	public PickupObject(final TileMap tm) {
		super(tm);
		setWidth(32);
		setHeight(32);
		setCwidth(32);
		setCheight(32);
		setFallSpeed(3);
		setMaxFallSpeed(6.0);

		try {
			BufferedImage spritesheet = ImageIO.read(
					getClass().getResourceAsStream("/items/items.png"));
			setSprite(spritesheet.getSubimage(
					(int) Math.round(Math.random() * 7) * 32,
					(int) Math.round(Math.random() * 7) * 32, 32, 32));
		} catch (Exception e) {
			Log.error("IO Read", "Could not file player sprite");
			e.printStackTrace();
		}
	}

	/**
	 * Checks whether the object collides with the player, if so executes
	 * onPickup(p), activating the powerup.
	 *
	 * @param p
	 *            Player object
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
	 *
	 * @param p
	 *            Player object
	 */
	public abstract void onPickup(final Player p);

	@Override
	public final void update() {

		setDy(getDy() + getFallSpeed());
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());

		if (getDy() < this.getMaxFallSpeed()) {
			setDy(this.getMaxSpeed());
		}
	}
}
