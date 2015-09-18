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
	 */
	public final void setCaught() {
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

}
