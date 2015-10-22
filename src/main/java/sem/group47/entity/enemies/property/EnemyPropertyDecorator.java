package sem.group47.entity.enemies.property;

/**
 * Decorator for enemy properties
 * @author Christian
 *
 */
public abstract class EnemyPropertyDecorator implements EnemyProperty {
	protected EnemyProperty tempProperty;
	public EnemyPropertyDecorator(EnemyProperty newProperty) {
		tempProperty = newProperty;
	}

	/** The y coordinate of the sprite to use in enemies/monster_sprite.png.
	 *
	 * @return
	 *  y coord
	 */
	public int getSpriteSheetY() {
		return tempProperty.getSpriteSheetY();
	}

	/**
	 * Wether the enemy can fire.
	 * @return
	 * a boolean
	 */
	public boolean canFire() {
		return tempProperty.canFire();
	}

	/**
	 * The projectile speed.
	 * @return
	 * speed
	 */
	public float getProjectileSpeed() {
		return tempProperty.getProjectileSpeed();
	}

	/**
	 * the fire delay.
	 * @return
	 * in seconds
	 */
	public int getFireDelay() {
		return tempProperty.getFireDelay();
	}


	/** The angry movement speed.
	 * @return
	 *  the angry movement speed*/
	public double getAngryMovSpeed() {
		return tempProperty.getAngryMovSpeed();
	}

	/** The normal movement speed.
	 * @return
	 *  the normal movement speed*/
	public double getNormalMovSpeed() {
		return tempProperty.getNormalMovSpeed();
	}

	/** The max movement speed.
	 * @return
	 *  the max movement speed*/
	public double getMaxMovSpeed() {
		return tempProperty.getMaxMovSpeed();
	}

	/** Gets the point value.
	 * @return
	 *  how much points the enemy is worth
	 **/
	public int getPoints() {
		return tempProperty.getPoints();
	}

	/** Gets the float speed.
	 * @return
	 *  float speed
	 **/
	public double getFloatSpeed() {
		return tempProperty.getFloatSpeed();
	}

	/** Gets the maximum float speed.
	 * @return
	 *  the maximum float speed
	 **/
	public double getMaxFloatSpeed() {
		return tempProperty.getMaxFloatSpeed();
	}

	/** Gets how long the enemy stays trapped.
	 * @return
	 *  time in seconds
	 **/
	public float getBreakFreeTime() {
		return tempProperty.getBreakFreeTime();
	}

	/** Gets the tiem the enemy stays angry.
	 * @return
	 *  time in seconds
	 **/
	public float getAngryTime() {
		return tempProperty.getAngryTime();
	}
}
