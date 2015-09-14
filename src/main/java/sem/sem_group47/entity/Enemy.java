package sem.sem_group47.entity;

import sem.sem_group47.tilemap.TileMap;

/**
 * The Class Enemy.
 */
public class Enemy extends MapObject {
	
	/** The caught. */
	protected boolean caught;

	/** The score points. */
	protected int scorePoints;

	/** The float speed. */
	protected double floatSpeed;

	/**
	 * Instantiates a new enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Enemy(TileMap tm) {
		super(tm);
		isAlive = true;
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
		if (!caught)
			caught = true;
	}

	/**
	 * Update.
	 */
	public void update() {
	}

	/**
	 * Sets the caught.
	 */
	public void setCaught() {
		caught = true;
	}

	/**
	 * Gets the score points.
	 *
	 * @return the score points
	 */
	public int getScorePoints() {
		return scorePoints;
	}

}
