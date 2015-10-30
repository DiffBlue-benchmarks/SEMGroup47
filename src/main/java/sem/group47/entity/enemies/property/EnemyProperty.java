package sem.group47.entity.enemies.property;

/**
 * The Interface EnemyProperty.
 *
 * @author Christian
 */
public interface EnemyProperty {

	/**
	 * The y coordinate of the sprite to use in enemies/monster_enemies.png.
	 *
	 * @return y coord
	 */
	int getSpriteSheetY();

	/**
	 * Wether the enemy can fire.
	 * 
	 * @return a boolean
	 */
	boolean canFire();

	/**
	 * The projectile speed.
	 * 
	 * @return speed
	 */
	float getProjectileSpeed();

	/**
	 * the fire delay.
	 * 
	 * @return in seconds
	 */
	int getFireDelay();

	/**
	 * The angry movement speed.
	 * 
	 * @return the angry movement speed
	 **/
	double getAngryMovSpeed();

	/**
	 * The normal movement speed.
	 * 
	 * @return the normal movement speed
	 **/
	double getNormalMovSpeed();

	/**
	 * The max movement speed.
	 * 
	 * @return the max movement speed
	 */
	double getMaxMovSpeed();

	/**
	 * Gets the point value.
	 * 
	 * @return how much points the enemy is worth
	 **/
	int getPoints();

	/**
	 * Gets the float speed.
	 * 
	 * @return float speed
	 **/
	double getFloatSpeed();

	/**
	 * Gets the maximum float speed.
	 * 
	 * @return the maximum float speed
	 **/
	double getMaxFloatSpeed();

	/**
	 * Gets how long the enemy stays trapped.
	 * 
	 * @return time in seconds
	 **/
	float getBreakFreeTime();

	/**
	 * Gets the tiem the enemy stays angry.
	 * 
	 * @return time in seconds
	 **/
	float getAngryTime();

}
