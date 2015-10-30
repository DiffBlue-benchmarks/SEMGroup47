package sem.group47.entity.enemies;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.entity.MapObject;
import sem.group47.entity.enemies.property.BaseEnemyProperty;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.tilemap.TileMap;

/**
 * The Class Enemy, contains the logic for creating an Enemy object.
 */
public class Enemy extends MapObject {

	/** The Constant LEVEL1_ENEMY. */
	public static final int LEVEL1_ENEMY = 0;

	/** The Constant PROJECTILE_ENEMEY. */
	public static final int PROJECTILE_ENEMEY = 1;

	/** The caught. */
	protected boolean caught;

	/** The spritesheet. */
	private BufferedImage spritesheet;

	/** Whether or not the enemy is angry. */
	private boolean isAngry;

	/** The time the enemy got caught. */
	private float timeCaught;

	/** The time the enemy stays angry after break free from bubble. */
	private float angryStartTime;

	/** Holds all the enemy properties. */
	private EnemyProperty properties;

	/**
	 * Instantiates a new enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Enemy(final TileMap tm) {
		super(tm);
		setWidth(36);
		setHeight(36);
		setCwidth(36);
		setCheight(36);
		setMovSpeed(0.3);
		setStopSpeed(.4);
		setFallSpeed(.35);
		setMaxFallSpeed(6.0);
		setJumpStart(-10.0);
		setStopJumpSpeed(.3);
		setAlive(true);
		properties = new BaseEnemyProperty();

		if (spritesheet == null) {
			try {
				this.setSpriteSheet(ImageIO.read(getClass()
						.getResourceAsStream("/enemies/monsters_sprite.png")));
				setSprite(getSpriteSheet().getSubimage(1, 0, 36, 36));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if is caught.
	 *
	 * @return true, if is caught
	 */
	public boolean isCaught() {
		return caught;
	}

	/**
	 * Hit.
	 */
	public void hit() {
		if (!caught) {
			caught = true;
		}
	}

	@Override
	public void update() {
	}

	/**
	 * checks for projectile collision with object.
	 *
	 * @param o
	 *            the o
	 * @return true, if successful
	 */
	public boolean projectileCollision(MapObject o) {
		return false;
	}

	/**
	 * Sets the caught.
	 *
	 * @param isCaught
	 *            Whether or not the enemy is caught
	 */
	public void setCaught(boolean isCaught) {
		caught = isCaught;
		setIsAngry(false);
		setTimeCaught(System.nanoTime());
		if (isCaught) {
			setSprite(spritesheet.getSubimage(8 * 36,
					properties.getSpriteSheetY() * 36, 36, 36));
		} else {
			setIsAngry(true);
		}
	}

	/**
	 * Sets the isAngry.
	 *
	 * @param angry
	 *            whether or not the enemy is angry
	 */
	public void setIsAngry(boolean angry) {
		isAngry = angry;
		if (angry) {
			angryStartTime = System.nanoTime();
			setSprite(spritesheet.getSubimage(4 * 36,
					properties.getSpriteSheetY() * 36, 36, 36));
			setInverseDraw(true);
			setMaxSpeed(properties.getAngryMovSpeed());
		} else {
			setSprite(spritesheet.getSubimage(0 * 36,
					properties.getSpriteSheetY() * 36, 36, 36));
			setInverseDraw(false);
			setMaxSpeed(properties.getNormalMovSpeed());
		}
	}

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public EnemyProperty getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 *
	 * @param properties
	 *            the new properties
	 */
	public final void setProperties(EnemyProperty properties) {
		this.properties = properties;
	}

	/**
	 * Gets isAngry.
	 *
	 * @return isAngry
	 */
	public final boolean isAngry() {
		return isAngry;
	}

	/**
	 * Gets the timeCaught.
	 *
	 * @return timeCaught
	 */
	public final float getTimeCaught() {
		return timeCaught;
	}

	/**
	 * Sets the timeCaught.
	 *
	 * @param time
	 *            The new timeCaught
	 */
	public final void setTimeCaught(final float time) {
		timeCaught = time;
	}

	/**
	 * Gets the sprite sheet.
	 *
	 * @return the sprite sheet
	 */
	public final BufferedImage getSpriteSheet() {
		return spritesheet;
	}

	/**
	 * Sets the sprite sheet.
	 *
	 * @param bi
	 *            the new sprite sheet
	 */
	public final void setSpriteSheet(final BufferedImage bi) {
		spritesheet = bi;
	}

	/**
	 * Returns the time at which the enemy became angry.
	 *
	 * @return time
	 */
	public final float getAngryStartTime() {
		return angryStartTime;
	}

	/**
	 * Sets the enemy to caught.
	 */
	public void setCaught() {

	}

}
