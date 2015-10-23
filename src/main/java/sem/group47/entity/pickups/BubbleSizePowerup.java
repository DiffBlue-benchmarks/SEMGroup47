package sem.group47.entity.pickups;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.entity.Player;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Speeds up the player movement when picked up.
 *
 * @author Christian
 *
 */
public class BubbleSizePowerup extends PickupObject {
	/**
	 * constructor.
	 *
	 * @param tm
	 *            TileMap
	 */
	public BubbleSizePowerup(final TileMap tm) {
		super(tm);

		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/items/powerups.png"));
			setSprite(spritesheet.getSubimage(0, 0, 16, 15));
		} catch (Exception e) {
			Log.error("IO Read", "Could not file powerup sprite");
			e.printStackTrace();
		}
	}

	@Override
	public final void onPickup(final Player p) {
		p.setBubbleSize(p.getBubbleSize() + 16);
	}
}
