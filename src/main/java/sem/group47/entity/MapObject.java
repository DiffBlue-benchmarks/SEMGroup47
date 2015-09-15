package sem.group47.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import sem.group47.tilemap.Tile;
import sem.group47.tilemap.TileMap;

/**
 * MapObject, SuperClass for all movable and interactable objects inside the
 * gameworld.
 */

public abstract class MapObject {

	// TILEMAP

	/** The tile map. */
	protected TileMap tileMap;

	/** The tile size. */
	protected int tileSize;

	// POSITION AND VECTOR

	/** The actual x position. */
	protected double x;

	/** The actual y position. */
	protected double y;

	/** Vector in x direction. */
	protected double dx;

	/** Vector in y direction. */
	protected double dy;

	// SPRITE DIMENSION

	/** The width. */
	protected int width;

	/** The height. */
	protected int height;

	// COLLISION

	/** The cwidth. */
	protected int cwidth;

	/** The cheight. */
	protected int cheight;

	/** The curr row. */
	protected int currRow;

	/** The curr col. */
	protected int currCol;

	/** The xdest: x position + x vector(x+dx). */
	protected double xdest;

	/** The ydest: y position + y vector(y+dy). */
	protected double ydest;

	/** The xtemp: Helper variable used in physics calculation. */
	protected double xtemp;

	/** The ytemp. Helper variable used in physics calculation. */
	protected double ytemp;

	/** The top left. */
	protected boolean topLeftBlocked;

	/** The top right. */
	protected boolean topRightBlocked;

	/** The bottom left. */
	protected boolean bottomLeftBlocked;

	/** The bottom right. */
	protected boolean bottomRightBlocked;

	/** The top left. */
	protected boolean topLeftSemiBlocked;

	/** The top right. */
	protected boolean topRightSemiBlocked;

	/** The bottom left. */
	protected boolean bottomLeftSemiBlocked;

	/** The bottom right. */
	protected boolean bottomRightSemiBlocked;

	// ANIMATION

	/** The current action. */
	protected int currentAction;

	/** The previous action. */
	protected int previousAction;

	/** The facing right. */
	protected boolean facingRight;

	// MOVEMENT

	/** The left. */
	protected boolean left;

	/** The right. */
	protected boolean right;

	/** The up. */
	protected boolean up;

	/** The down. */
	protected boolean down;

	/** The jumping. */
	protected boolean jumping;

	/** The falling. */
	protected boolean falling;

	/** The mov speed. */
	protected double movSpeed;

	/** The max speed. */
	protected double maxSpeed;

	/** The stop speed. */
	protected double stopSpeed;

	/** The fall speed. */
	protected double fallSpeed;

	/** The max fall speed. */
	protected double maxFallSpeed;

	/** The jump start. */
	protected double jumpStart;

	/** The stop jump speed. */
	protected double stopJumpSpeed;

	/** The is alive. */
	protected boolean isAlive;

	// GRAPHICS
	/** The sprite. */
	protected BufferedImage sprite;

	/**
	 * Constructor.
	 *
	 * @param tm
	 *            TileMap in which this object lives
	 */
	public MapObject(TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	/**
	 * Determines if this MapObject intersects with another object.
	 *
	 * @param o
	 *            the other MapObject
	 * @return true if they intersect
	 */
	public boolean intersects(MapObject o) {
		return this.getRectangle().intersects(o.getRectangle());
	}

	/**
	 * returns a Rectangle object describing the collisionbox of the MapObject.
	 *
	 * @return Rectangle
	 */
	public Rectangle getRectangle() {
		return new Rectangle((int) x - cwidth / 2, (int) y - cheight / 2,
				cwidth, cheight);
	}

	/**
	 * modifies xtemp and ytemp so that they don't intersect with the tileMap.
	 */
	public void checkTileMapCollision() {
		// Determine current column and row
		currCol = (int) x / tileSize;
		currRow = (int) y / tileSize;

		// Destination coordinates
		xdest = x + dx;
		ydest = y + dy;

		// Temporary coordinates
		xtemp = x;
		ytemp = y;

		calculateCorners(x, ydest);

		// If we are moving upwards
		if (dy < 0) {
			// And our topLeft or topRight corner is
			// colliding with a 'BLOCKED' tile
			if (topLeftBlocked || topRightBlocked) {
				// Our vertical movement vector becomes 0
				dy = 0;

				// Our new position becomes just below the tile above we are
				// colliding with
				ytemp = currRow * tileSize + cheight / 2;

			}

			// If we are not colliding
			else {
				// Our y vector is added to our temporary y position
				ytemp += dy;
			}

		}
		// If we are moving downwards
		else if (dy > 0) {
			// And our bottomLeft or bottomRight corner is colliding with a
			// 'BLOCKED' or 'SEMIBLOCKED' tile
			if (bottomLeftBlocked || bottomRightBlocked
					|| bottomLeftSemiBlocked || bottomRightSemiBlocked) {

				// Our horizontal movement vector becomes 0
				dy = 0;
				// We are standing on a solid tile, so we are not falling
				falling = false;

				// Our new position becomes just on top of the tile we are
				// colliding with
				//
				ytemp = (currRow + 1) * tileSize - cheight / 2;
			}
			// If we are not colliding
			else {
				// Our y vector is added to our temporary y position
				ytemp += dy;
				if (ytemp >= tileMap.getHeight() - 15) {
					ytemp = 3 * tileMap.getTileSize();
				}
			}
		}

		// Check for collisions in the x direction
		calculateCorners(xdest, y);

		// If we are moving to the left
		if (dx < 0) {
			// And we are colliding on the left

			if (topLeftBlocked) {
				// Our horizontal movement vector becomes 0
				dx = 0;

				// Our new position is set to just to the right of the blocking
				// tile
				xtemp = currCol * tileSize + cwidth / 2;

			}

			// If we are not colliding
			else {
				// Apply the horizontal movement vector
				xtemp += dx;
			}

		}

		// If we are moving to the right
		else if (dx > 0) {
			// And we are colliding on the right
			if (topRightBlocked) {
				// Our horizontal movement vector becomes 0
				dx = 0;

				// Our new position is set just to the left of the blocking tile
				xtemp = (currCol + 1) * tileSize - cwidth / 2;
			}

			// If we are not colliding
			else {

				// Apply the horizontal movement vector;
				xtemp += dx;
			}
		}

		// Check if we are falling
		if (!falling) {
			// See if we move 1 pixel downwards if we are colliding
			calculateCorners(x, ydest + 1);

			// If we do not collide we are falling
			if (!bottomLeftBlocked && !bottomLeftSemiBlocked
					&& !bottomRightSemiBlocked && !bottomRightBlocked) {
				falling = true;
			}
		}
	}

	/**
	 * calculates if any of the corners of the object intersect with tiles and
	 * sets the topLeft, topRight, bottomLeft and bottomRight boolean
	 * accordingly.
	 *
	 * @param x
	 *            xposition to check
	 * @param y
	 *            yposition to check
	 */
	public void calculateCorners(double x, double y) {
		int leftTile = (int) (x - cwidth / 2) / tileSize;
		int rightTile = (int) (x - cwidth / 2 + cwidth - 1) / tileSize;
		int topTile = (int) (y - cheight / 2) / tileSize;
		int bottomTile = (int) (y - cheight / 2 + cheight) / tileSize;

		int tl = tileMap.getType(topTile, leftTile);
		int tr = tileMap.getType(topTile, rightTile);
		int bl = tileMap.getType(bottomTile, leftTile);
		int br = tileMap.getType(bottomTile, rightTile);

		// Flags for whether we are colliding
		topLeftBlocked = tl == Tile.BLOCKED;
		topRightBlocked = tr == Tile.BLOCKED;
		bottomLeftBlocked = bl == Tile.BLOCKED;
		bottomRightBlocked = br == Tile.BLOCKED;
		topLeftSemiBlocked = tl == Tile.SEMIBLOCKED;
		topRightSemiBlocked = tr == Tile.SEMIBLOCKED;
		bottomLeftSemiBlocked = bl == Tile.SEMIBLOCKED;
		bottomRightSemiBlocked = br == Tile.SEMIBLOCKED;
	}

	/**
	 * Draw.
	 *
	 * @param g
	 *            the g
	 */
	public void draw(Graphics2D g) {
		if (facingRight)
			g.drawImage(sprite, (int) (x - width / 2), (int) (y - height / 2),
					null);
		else
			g.drawImage(sprite, (int) (x + width / 2), (int) (y - height / 2),
					-width, height, null);
	}

	/**
	 * Gets the checks if is alive.
	 *
	 * @return the checks if is alive
	 */
	public boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getx() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double gety() {
		return y;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gets the c width.
	 *
	 * @return the c width
	 */
	public int getCWidth() {
		return cwidth;
	}

	/**
	 * Gets the c height.
	 *
	 * @return the c height
	 */
	public int getCHeight() {
		return cheight;
	}

	/**
	 * Sets the position.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the vector.
	 *
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Sets the left.
	 *
	 * @param b
	 *            the new left
	 */
	public void setLeft(boolean b) {
		left = b;
	}

	/**
	 * Sets the right.
	 *
	 * @param b
	 *            the new right
	 */
	public void setRight(boolean b) {
		right = b;
	}

	/**
	 * Sets the up.
	 *
	 * @param b
	 *            the new up
	 */
	public void setUp(boolean b) {
		up = b;
	}

	/**
	 * Sets the down.
	 *
	 * @param b
	 *            the new down
	 */
	public void setDown(boolean b) {
		down = b;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}

}
