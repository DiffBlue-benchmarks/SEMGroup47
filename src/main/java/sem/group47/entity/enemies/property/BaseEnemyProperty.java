package sem.group47.entity.enemies.property;

/**
 * The class BaseEnemyProperty, contains the base enemy properties.
 *
 * @author Christian
 *
 */
public class BaseEnemyProperty implements EnemyProperty {

	/**
	 * Constructor.
	 */
	public BaseEnemyProperty() {
	}

	/**
	 * The y coordinate of the sprite to use in enemies/monster_sprite.png.
	 *
	 * @return y coord
	 */
	public final int getSpriteSheetY() {
		return 0;
	}

	/**
	 * Wether the enemy can fire.
	 *
	 * @return a boolean
	 */
	public final boolean canFire() {
		return false;
	}

	/**
	 * The projectile speed.
	 *
	 * @return speed
	 */
	public final float getProjectileSpeed() {
		return 2.5f;
	}

	/**
	 * the fire delay.
	 *
	 * @return in seconds
	 */
	public final int getFireDelay() {
		return 1500;
	}

	/**
	 * The angry movement speed.
	 *
	 * @return the angry movement speed
	 */
	public final double getAngryMovSpeed() {
		return 2.0d;
	}

	/**
	 * The normal movement speed.
	 *
	 * @return the normal movement speed
	 */
	public final double getNormalMovSpeed() {
		return 1.2d;
	}

	/**
	 * The max movement speed.
	 *
	 * @return the max movement speed
	 */
	public final double getMaxMovSpeed() {
		return 3d;
	}

	/**
	 * Gets the point value.
	 *
	 * @return how much points the enemy is worth
	 **/
	public final int getPoints() {
		return 10;
	}

	/**
	 * Gets the float speed.
	 *
	 * @return float speed
	 **/
	public final double getFloatSpeed() {
		return .1;
	}

	/**
	 * Gets the maximum float speed.
	 *
	 * @return the maximum float speed
	 **/
	public final double getMaxFloatSpeed() {
		return -4.5;
	}

	/**
	 * Gets how long the enemy stays trapped.
	 *
	 * @return time in seconds
	 **/
	public final float getBreakFreeTime() {
		return 10;
	}

	/**
	 * Gets the time the enemy stays angry.
	 *
	 * @return time in seconds
	 **/
	public final float getAngryTime() {
		return 10;
	}
}
