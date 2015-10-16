package sem.group47.tilemap;

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

	/** The Constant BLOCKED. */
	// third row
	public static final int BLOCKED = 2;

	/**
	 * Instantiates a new tile.
	 *
	 * @param pimage
	 *            the image
	 * @param ptype
	 *            the type
	 */
	public Tile(final BufferedImage pimage, final int ptype) {
		this.image = pimage;
		this.type = ptype;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public final BufferedImage getImage() {
		return image;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public final int getType() {
		return type;
	}

}
