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
	private TileMap tileMap;

	/** The tile size. */
	private int tileSize;

	// POSITION AND VECTOR

	/** The actual x position. */
	private double xpos;

	/** The actual y position. */
	private double ypos;

	/** Vector in x direction. */
	private double dx;

	/** Vector in y direction. */
	private double dy;

	// SPRITE DIMENSION

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	// COLLISION

	/** The cwidth. */
	private int cwidth;

	/** The cheight. */
	private int cheight;

	/** The curr row. */
	private int currRow;

	/** The curr col. */
	private int currCol;

	/** The xdest: x position + x vector(x+dx). */
	private double xdest;

	/** The ydest: y position + y vector(y+dy). */
	private double ydest;

	/** The xposNew: new actual x position. */
	private double xposNew;

	/** The yposNew. new actual y position. */
	private double yposNew;

	/** The top left. */
	private boolean topLeftBlocked;

	/** The top right. */
	private boolean topRightBlocked;

	/** The bottom left. */
	private boolean bottomLeftBlocked;

	/** The bottom right. */
	private boolean bottomRightBlocked;

	/** The top left. */
	private boolean topLeftSemiBlocked;

	/** The top right. */
	private boolean topRightSemiBlocked;

	/** The bottom left. */
	private boolean bottomLeftSemiBlocked;

	/** The bottom right. */
	private boolean bottomRightSemiBlocked;

	// ANIMATION

	/** The current action. */
	private int currentAction;

	/** The previous action. */
	private int previousAction;

	/** The facing right. */
	private boolean facingRight;

	// MOVEMENT

	/** The left. */
	private boolean left;

	/** The right. */
	private boolean right;

	/** The up. */
	private boolean up;

	/** The down. */
	private boolean down;

	/** The jumping. */
	private boolean jumping;

	/** The falling. */
	private boolean falling;

	/** The mov speed. */
	private double movSpeed;

	/** The max speed. */
	private double maxSpeed;

	/** The stop speed. */
	private double stopSpeed;

	/** The fall speed. */
	private double fallSpeed;

	/** The max fall speed. */
	private double maxFallSpeed;

	/** The jump start. */
	private double jumpStart;

	/** The stop jump speed. */
	private double stopJumpSpeed;

	/** The is alive. */
	private boolean isAlive;

	// GRAPHICS
	/** The sprite. */
	private BufferedImage sprite;

	/**
	 * Constructor.
	 *
	 * @param tm
	 *            TileMap in which this object lives
	 */
	public MapObject(final TileMap tm) {
		tileMap = tm;
		tileSize = tm.getTileSize();
	}

	/**
	 * Determines if this MapObject intersects with another object.
	 *
	 * @param obj
	 *            the obj
	 * @return true if they intersect
	 */
	public final boolean intersects(final MapObject obj) {
		return this.getRectangle().intersects(obj.getRectangle());
	}

	/**
	 * returns a Rectangle object describing the collisionbox of the MapObject.
	 *
	 * @return Rectangle
	 */
	public final Rectangle getRectangle() {
		return new Rectangle((int) xpos - cwidth / 2, (int) ypos - cheight / 2,
				cwidth, cheight);
	}

	/**
	 * modifies xposNew and yposNew so that they don't intersect with the
	 * tileMap.
	 */
	public final void checkTileMapCollision() {
		// Determine current column and row
		currCol = (int) xpos / tileSize;
		currRow = (int) ypos / tileSize;

		// Destination coordinates
		xdest = xpos + dx;
		ydest = ypos + dy;

		// Temporary coordinates
		xposNew = xpos;
		yposNew = ypos;

		checkYCollision();
		checkXCollision();
	}

	/**
	 * Check when the MapObject is moving upwards/ downwards if it is colliding
	 * with anything.
	 */
	public final void checkYCollision() {
		calculateCorners(xpos, ydest);

		// If we are moving upwards
		if (dy < 0) {
			// And our topLeft or topRight corner is
			// colliding with a 'BLOCKED' tile
			if (topLeftBlocked || topRightBlocked) {
				// Our vertical movement vector becomes 0
				dy = 0;

				// Our new position becomes just below the tile above we are
				// colliding with
				yposNew = currRow * tileSize + cheight / 2;
			} else { // If we are not colliding
				// Our y vector is added to our temporary y position
				yposNew += dy;
			}
		} else if (dy > 0) { // If we are moving downwards
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
				yposNew = (currRow + 1) * tileSize - cheight / 2;
			} else { // If we are not colliding
				// Our y vector is added to our temporary y position
				falling = true;
				yposNew += dy;
				int heightAdjustment = tileMap.getTileSize() / 2;

				if (yposNew >= tileMap.getHeight() - heightAdjustment) {
					yposNew = 3 * tileMap.getTileSize();
				}
			}

		}
		// Check if we are falling
		if (!falling) {
			// See if when we move 1 pixel downwards if we are colliding
			calculateCorners(xpos, ydest + 1);

			// If we do not collide we are falling
			if (!bottomLeftBlocked && !bottomLeftSemiBlocked
					&& !bottomRightSemiBlocked && !bottomRightBlocked) {
				falling = true;
			}
		}
	}

	/**
	 * Check x collision.
	 */
	public final void checkXCollision() {

		// Check for collisions in the x direction
		calculateCorners(xdest, ypos);

		// If we are moving to the left
		if (dx < 0) {
			// And we are colliding on the left

			if (topLeftBlocked) {
				// Our horizontal movement vector becomes 0
				dx = 0;

				// Our new position is set to just to the right of the blocking
				// tile
				xposNew = currCol * tileSize + cwidth / 2;

			} else { // If we are not colliding
				// Apply the horizontal movement vector
				xposNew += dx;
			}
		} else if (dx > 0) { // If we are moving to the right
			// And we are colliding on the right
			if (topRightBlocked) {
				// Our horizontal movement vector becomes 0
				dx = 0;

				// Our new position is set just to the left of the blocking tile
				xposNew = (currCol + 1) * tileSize - cwidth / 2;
			} else { // If we are not colliding

				// Apply the horizontal movement vector;
				xposNew += dx;
			}
		}

	}

	/**
	 * calculates if any of the corners of the object intersect with tiles and
	 * sets the topLeft, topRight, bottomLeft and bottomRight boolean
	 * accordingly.
	 *
	 * @param xin
	 *            xposition to check
	 * @param yin
	 *            yposition to check
	 */
	public final void calculateCorners(final double xin, final double yin) {
		int leftTile = (int) ((xin - cwidth / 2) / tileSize);
		int rightTile = (int) ((xin - cwidth / 2 + cwidth - 1) / tileSize);
		int topTile = (int) ((yin - cheight / 2) / tileSize);
		int bottomTile = (int) ((yin - cheight / 2 + cheight) / tileSize);

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
	 * @param gr
	 *            the graphics
	 */
	public void draw(final Graphics2D gr) {
		if (facingRight) {
			gr.drawImage(sprite, (int) (xpos - width / (double) 2),
					(int) (ypos - height / (double) 2), null);
		} else {
			gr.drawImage(sprite, (int) (xpos + width / (double) 2),
					(int) (ypos - height / (double) 2), -width, height, null);
		}
	}

	/**
	 * Gets the checks if is alive.
	 *
	 * @return the checks if is alive
	 */
	public final boolean getIsAlive() {
		return isAlive;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public final double getx() {
		return xpos;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public final double gety() {
		return ypos;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Gets the c width.
	 *
	 * @return the c width
	 */
	public final int getCWidth() {
		return cwidth;
	}

	/**
	 * Gets the c height.
	 *
	 * @return the c height
	 */
	public final int getCHeight() {
		return cheight;
	}

	/**
	 * Sets the position.
	 *
	 * @param xnew
	 *            the xnew
	 * @param ynew
	 *            the ynew
	 */
	public final void setPosition(final double xnew, final double ynew) {
		this.xpos = xnew;
		this.ypos = ynew;
	}

	/**
	 * Sets the vector.
	 *
	 * @param dx
	 *            the dx
	 * @param dy
	 *            the dy
	 */
	public final void setVector(final double dx, final double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * Sets the left.
	 *
	 * @param bleft
	 *            the new left
	 */
	public final void setLeft(final boolean bleft) {
		left = bleft;
	}

	/**
	 * Sets the right.
	 *
	 * @param bright
	 *            the new right
	 */
	public final void setRight(final boolean bright) {
		right = bright;
	}

	/**
	 * Sets the up.
	 *
	 * @param bup
	 *            the new up
	 */
	public final void setUp(final boolean bup) {
		up = bup;
	}

	/**
	 * Sets the down.
	 *
	 * @param bdown
	 *            the new down
	 */
	public final void setDown(final boolean bdown) {
		down = bdown;
	}

	public final boolean getDown() {
		return down;
	}

	public final boolean getUp() {
		return up;
	}

	/**
	 * Gets the dx.
	 *
	 * @return the dx
	 */
	public final double getDx() {
		return dx;
	}

	/**
	 * Gets the dy.
	 *
	 * @return the dy
	 */
	public final double getDy() {
		return dy;
	}

	// public final void setDx(final int dx) {
	// this.dx = dx;
	// }
	//
	public final void setDy(final int pDy) {
		this.dy = pDy;
	}

	//
	public final double getXpos() {
		return xpos;
	}

	//
	// public final void setXpos(final double xpos) {
	// this.xpos = xpos;
	// }
	//
	public final double getYpos() {
		return ypos;
	}

	//
	// public final void setYpos(final double ypos) {
	// this.ypos = ypos;
	// }
	//
	// public final double getXdest() {
	// return xdest;
	// }
	//
	// public final void setXdest(final double xdest) {
	// this.xdest = xdest;
	// }
	//
	// public final double getYdest() {
	// return ydest;
	// }
	//
	// public final void setYdest(final double ydest) {
	// this.ydest = ydest;
	// }
	//
	public final double getXposNew() {
		return xposNew;
	}

	//
	// public final void setXposNew(final double xposNew) {
	// this.xposNew = xposNew;
	// }
	//
	public final double getYposNew() {
		return yposNew;
	}

	//
	// public final void setYposNew(final double yposNew) {
	// this.yposNew = yposNew;
	// }
	//
	public final TileMap getTileMap() {
		return tileMap;
	}

	//
	// public final void setTileMap(TileMap tileMap) {
	// this.tileMap = tileMap;
	// }
	//
	// public final int getTileSize() {
	// return tileSize;
	// }
	//
	// public final void setTileSize(final int tileSize) {
	// this.tileSize = tileSize;
	// }
	//
	// public final int getCwidth() {
	// return cwidth;
	// }
	//

	public final void setCwidth(final int pCwidth) {
		this.cwidth = pCwidth;
	}

	//
	// public final int getCheight() {
	// return cheight;
	// }
	//
	public final void setCheight(final int pCheight) {
		this.cheight = pCheight;
	}

	//
	// public final int getCurrRow() {
	// return currRow;
	// }
	//
	// public final void setCurrRow(final int currRow) {
	// this.currRow = currRow;
	// }
	//
	// public final int getCurrCol() {
	// return currCol;
	// }
	//
	// public final void setCurrCol(final int currCol) {
	// this.currCol = currCol;
	// }
	//
	// public boolean isTopLeftBlocked() {
	// return topLeftBlocked;
	// }
	//
	// public void setTopLeftBlocked(boolean topLeftBlocked) {
	// this.topLeftBlocked = topLeftBlocked;
	// }
	//
	// public boolean isTopRightBlocked() {
	// return topRightBlocked;
	// }
	//
	// public void setTopRightBlocked(boolean topRightBlocked) {
	// this.topRightBlocked = topRightBlocked;
	// }
	//
	// public boolean isBottomLeftBlocked() {
	// return bottomLeftBlocked;
	// }
	//
	// public void setBottomLeftBlocked(boolean bottomLeftBlocked) {
	// this.bottomLeftBlocked = bottomLeftBlocked;
	// }
	//
	// public boolean isBottomRightBlocked() {
	// return bottomRightBlocked;
	// }
	//
	// public void setBottomRightBlocked(boolean bottomRightBlocked) {
	// this.bottomRightBlocked = bottomRightBlocked;
	// }
	//
	// public boolean isTopLeftSemiBlocked() {
	// return topLeftSemiBlocked;
	// }
	//
	// public void setTopLeftSemiBlocked(boolean topLeftSemiBlocked) {
	// this.topLeftSemiBlocked = topLeftSemiBlocked;
	// }
	//
	// public boolean isTopRightSemiBlocked() {
	// return topRightSemiBlocked;
	// }
	//
	// public void setTopRightSemiBlocked(boolean topRightSemiBlocked) {
	// this.topRightSemiBlocked = topRightSemiBlocked;
	// }
	//
	// public boolean isBottomLeftSemiBlocked() {
	// return bottomLeftSemiBlocked;
	// }
	//
	// public void setBottomLeftSemiBlocked(boolean bottomLeftSemiBlocked) {
	// this.bottomLeftSemiBlocked = bottomLeftSemiBlocked;
	// }
	//
	// public boolean isBottomRightSemiBlocked() {
	// return bottomRightSemiBlocked;
	// }
	//
	// public void setBottomRightSemiBlocked(boolean bottomRightSemiBlocked) {
	// this.bottomRightSemiBlocked = bottomRightSemiBlocked;
	// }
	//
	// public int getCurrentAction() {
	// return currentAction;
	// }
	//
	// public void setCurrentAction(int currentAction) {
	// this.currentAction = currentAction;
	// }
	//
	// public int getPreviousAction() {
	// return previousAction;
	// }
	//
	// public void setPreviousAction(int previousAction) {
	// this.previousAction = previousAction;
	// }
	//
	public final boolean isFacingRight() {
		return facingRight;
	}

	//
	public final void setFacingRight(final boolean pFacingRight) {
		this.facingRight = pFacingRight;
	}

	public final boolean isJumping() {
		return jumping;
	}

	public final void setJumping(final boolean pJumping) {
		this.jumping = pJumping;
	}

	public final boolean isFalling() {
		return falling;
	}

	public final void setFalling(final boolean pFalling) {
		this.falling = pFalling;
	}

	public double getMovSpeed() {
		return movSpeed;
	}

	public final void setMovSpeed(final double pMovSpeed) {
		this.movSpeed = pMovSpeed;
	}

	public final double getMaxSpeed() {
		return maxSpeed;
	}

	public final void setMaxSpeed(final double pMaxSpeed) {
		this.maxSpeed = pMaxSpeed;
	}

	public final double getStopSpeed() {
		return stopSpeed;
	}

	public final void setStopSpeed(final double pStopSpeed) {
		this.stopSpeed = pStopSpeed;
	}

	public final double getFallSpeed() {
		return fallSpeed;
	}

	public final double getMaxFallSpeed() {
		return maxFallSpeed;
	}

	public final void setFallSpeed(final double pFallSpeed) {
		this.fallSpeed = pFallSpeed;
	}

	public final void setMaxFallSpeed(final double pMaxFallSpeed) {
		this.maxFallSpeed = pMaxFallSpeed;
	}

	public double getJumpStart() {
		return jumpStart;
	}

	//
	public final void setJumpStart(final double pJumpStart) {
		this.jumpStart = pJumpStart;
	}

	public final double getStopJumpSpeed() {
		return stopJumpSpeed;
	}

	public final void setStopJumpSpeed(final double pStopJumpSpeed) {
		this.stopJumpSpeed = pStopJumpSpeed;
	}

	//
	// public BufferedImage getSprite() {
	// return sprite;
	// }
	//
	public final void setSprite(final BufferedImage pSprite) {
		this.sprite = pSprite;
	}

	public final boolean isLeft() {
		return left;
	}

	public final boolean isRight() {
		return right;
	}

	public final boolean isUp() {
		return up;
	}

	public final boolean isDown() {
		return down;
	}

	public final void setDx(final double pDx) {
		this.dx = pDx;
	}

	public final void setDy(final double pDy) {
		this.dy = pDy;
	}

	/**
	 * setWidth.
	 * 
	 * @param pWidth
	 *            .
	 */
	public final void setWidth(final int pWidth) {
		this.width = pWidth;
	}

	/**
	 * setHeight.
	 * 
	 * @param pHeight
	 *            .
	 */
	public final void setHeight(final int pHeight) {
		this.height = pHeight;
	}

	public final void setAlive(final boolean pIsAlive) {
		this.isAlive = pIsAlive;
	}

}
