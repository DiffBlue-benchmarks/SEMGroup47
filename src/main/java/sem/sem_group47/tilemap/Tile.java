package sem.sem_group47.tilemap;

import java.awt.image.BufferedImage;

/**
 * The Class Tile.
 */
public class Tile {

	/** The image. */
	private BufferedImage image;

	/** The type. */
	private int type;

	// TILE TYPES

	// first row
	/** The Constant NORMAL. */
	public static final int NORMAL = 0;

	// second row
	/** The Constant BLOCKED. */
	public static final int SEMIBLOCKED = 1;

	// third row
	public static final int BLOCKED = 2;

	/**
	 * Instantiates a new tile.
	 *
	 * @param image
	 *            the image
	 * @param type
	 *            the type
	 */
	public Tile(BufferedImage image, int type) {
		this.image = image;
		this.type = type;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
