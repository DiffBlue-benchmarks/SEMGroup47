package sem.group47.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.tilemap.TileMap;

/**
 * The Class Enemy.
 */
public class Enemy extends MapObject {

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

	/** Whether or not the enemy is angry. */
	private boolean isAngry;

	/** The time the enemy got caught. */
	private float timeCaught;

	/** The time the enemy needs to break free from the bubble. */
	private float timeUntillBreakFree;
	
	/** The time the enemy got angry after break free from bubble. */
	private float angryTime;

	/**
	 * Instantiates a new enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Enemy(final TileMap tm) {
		super(tm);
		setAlive(true);

		try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/enemies/level1.gif"));
			setSprite(spritesheet.getSubimage(0, 0, 30, 30));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	 * Sets the caught.
	 * @param isCaught
	 * Whether or not the enemy is caught
	 */
	public final void setCaught(boolean isCaught) {
		caught = isCaught;
		setIsAngry(false);
		setTimeCaught(System.nanoTime());
		if(isCaught) {
			setSprite(spritesheet.getSubimage(90, 0, 30, 30));
		} else {
			setIsAngry(true);
		}
	}

	/**
	 * Sets the isAngry.
	 * @param angry
	 * whether or not the enemy is angry
	 */
	public final void setIsAngry(boolean angry) {
		isAngry = angry;
		if(angry) {
			setAngryTime(System.nanoTime());
			setSprite(spritesheet.getSubimage(120, 0, 30, 30));
			setMaxSpeed(getMaxSpeed() * 1.5);
			setMovSpeed(getMovSpeed() * 1.5);		} else {
			setSprite(spritesheet.getSubimage(0, 0, 30, 30));
			setMovSpeed(0.3);
		}
	}

	/**
	 * Gets isAngry.
	 * @return isAngry
	 */
	public final boolean isAngry() {
		return isAngry;
	}

	/**
	 * Gets the timeCaught.
	 * @return timeCaught
	 */
	public final float getTimeCaught() {
		return timeCaught;
	}
	
	/**
	 * Gets the Angry Time.
	 * @return angryTime
	 */
	public final float getAngryTime() {
		return angryTime;
	}

	/**
	 * Sets the Angry Time.
	 * @param time
	 * the Angry time set
	 */
	public final void setAngryTime(float time) {
		angryTime = time;
	}

	/**
	 * Sets the timeCaught.
	 * @param time
	 * The new timeCaught
	 */
	public final void setTimeCaught(float time) {
		timeCaught = time;
	}
	
	/**
	 * Sets the time needed to break free from a bubble
	 * @param time
	 * The time needed
	 */
	public final void setTimeUntillBreakFree(float time) {
		timeUntillBreakFree = time;
	}
	
	/**
	 * Gets the time needed to break free from a bubble
	 * @return timeUntillBreakFree
	 * The time needed
	 */
	public final float getTimeUntillBreakFree() {
		return timeUntillBreakFree;
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


}
