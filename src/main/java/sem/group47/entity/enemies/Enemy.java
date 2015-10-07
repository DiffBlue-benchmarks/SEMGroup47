package sem.group47.entity.enemies;

import java.awt.image.BufferedImage;

import sem.group47.entity.Player;
import sem.group47.entity.MapObject;
import sem.group47.tilemap.TileMap;

/**
 * The Class Enemy.
 */
public class Enemy extends MapObject {

	public static final int LEVEL1_ENEMY = 0;
	public static final int PROJECTILE_ENEMEY = 1;
	
	/** The caught. */
	private boolean caught;

	/** The score points. */
	private int scorePoints;

	/** The float speed. */
	private double floatSpeed;
	
	/** The max float speed. */
	private double maxFloatSpeed;

	/** The spritesheet. */
	private BufferedImage spritesheet;

	/**
	 * Instantiates a new enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Enemy(final TileMap tm) {
		super(tm);
		setAlive(true);
	}

	/**
	 * Checks if is caught.
	 *
	 * @return true, if is caught
	 */
	public final boolean isCaught() {
		return caught;
	}

	/**
	 * Hit.
	 */
	public final void hit() {
		if (!caught) {
			caught = true;
		}
	}

	/**
	 * Update.
	 */
	public void update() {
	}

	/**
	 * checks for projectile collision with object
	 */
	public boolean projectileCollision(MapObject o) {
		return false;
	}
	
	/**
	 * Sets the caught.
	 */
	public void setCaught() {
		caught = true;
		setSprite(spritesheet.getSubimage(90, 0, 30, 30));
	}

	/**
	 * Gets the score points.
	 *
	 * @return the score points
	 */
	public final int getScorePoints() {
		return scorePoints;
	}

	/**
	 * Sets the score points.
	 *
	 * @param pScorePoints
	 *            the new score points
	 */
	public final void setScorePoints(final int pScorePoints) {
		this.scorePoints = pScorePoints;
	}

	/**
	 * Sets the float speed.
	 *
	 * @param pFloatSpeed
	 *            the new float speed
	 */
	public final void setFloatSpeed(final double pFloatSpeed) {
		this.floatSpeed = pFloatSpeed;
	}

	/**
	 * Gets the float speed.
	 *
	 * @return the float speed
	 */
	public final double getFloatSpeed() {
		return floatSpeed;
	}

	/**
	 * Gets the max float speed.
	 *
	 * @return the speed
	 */
	public final double getMaxFloatSpeed() {
	 return maxFloatSpeed;
	}

	/**
	 * Sets the max float speed.
	 * @param speed
	 *  Speed to be set
	 */
	public final void setMaxFloatSpeed(final double speed) {
	 maxFloatSpeed = speed;
	}
	
	public final BufferedImage getSpriteSheet() {
		return spritesheet;
	}
	
	public final void setSpriteSheet(BufferedImage bi) {
		spritesheet = bi;
	}

}
