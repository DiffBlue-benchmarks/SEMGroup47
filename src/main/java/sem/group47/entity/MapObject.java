package sem.group47.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import sem.group47.main.Drawable;
import sem.group47.tilemap.Tile;
import sem.group47.tilemap.TileMap;

/**
 * MapObject, SuperClass for all movable and interactable objects inside the
 * gameworld.
 */

public abstract class MapObject implements Drawable {

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

	/** The bottom left. */
	private boolean bottomLeftSemiBlocked;

	/** The bottom right. */
	private boolean bottomRightSemiBlocked;

	// ANIMATION

	/** The animation. */
	protected Animation animation;
	
	/** The current Action. */
	protected int currentAction;
	
	/** The previous action. */
	protected int previousAction;
	
	/** The facing right. */
	protected boolean facingRight;
	
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
	 * Any game physics should be applied here.
	 */
	public abstract void update();

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
		currCol = (int) xpos / tileSize;
		currRow = (int) ypos / tileSize;

		xdest = xpos + dx;
		ydest = ypos + dy;

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
		if (dy < 0) {
			if (topLeftBlocked || topRightBlocked) {
				dy = 0;
				yposNew = currRow * tileSize + cheight / 2;
			} else {
				if (yposNew <= 60) {
					yposNew = 560;
				} else {
					yposNew += dy;
				}

			}
		} else if (dy > 0) {
			if (bottomLeftBlocked || bottomRightBlocked
					|| bottomLeftSemiBlocked || bottomRightSemiBlocked) {
				dy = 0;
				falling = false;
				yposNew = (currRow + 1) * tileSize - cheight / 2;
			} else {
				falling = true;
				yposNew += dy;

				if (yposNew >= tileMap.getHeight() - 15) {
					yposNew = 3 * tileMap.getTileSize();
				}
			}

		}

		if (!falling) {
			calculateCorners(xpos, ydest + 1);
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
		calculateCorners(xdest, ypos);
		if (dx < 0) {
			if (topLeftBlocked) {
				dx = 0;
				xposNew = currCol * tileSize + cwidth / 2;
			} else {
				xposNew += dx;
			}
		} else if (dx > 0) {
			if (topRightBlocked) {
				dx = 0;
				xposNew = (currCol + 1) * tileSize - cwidth / 2;
			} else {
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

		topLeftBlocked = tl == Tile.BLOCKED;
		topRightBlocked = tr == Tile.BLOCKED;
		bottomLeftBlocked = bl == Tile.BLOCKED;
		bottomRightBlocked = br == Tile.BLOCKED;
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
	 * @param pDx
	 *            the dx
	 * @param pDy
	 *            the dy
	 */
	public final void setVector(final double pDx, final double pDy) {
		this.dx = pDx;
		this.dy = pDy;
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

	/**
	 * Gets the down.
	 *
	 * @return the down
	 */
	public final boolean getDown() {
		return down;
	}

	/**
	 * Gets the up.
	 *
	 * @return the up
	 */
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

	/**
	 * Sets the dy.
	 *
	 * @param pDy
	 *            the new dy
	 */
	public final void setDy(final int pDy) {
		this.dy = pDy;
	}

	//
	/**
	 * Gets the xpos.
	 *
	 * @return the xpos
	 */
	public final double getXpos() {
		return xpos;
	}

	/**
	 * Gets the ypos.
	 *
	 * @return the ypos
	 */
	public final double getYpos() {
		return ypos;
	}

	/**
	 * Gets the xpos new.
	 *
	 * @return the xpos new
	 */
	public final double getXposNew() {
		return xposNew;
	}

	/**
	 * Gets the ypos new.
	 *
	 * @return the ypos new
	 */
	public final double getYposNew() {
		return yposNew;
	}

	/**
	 * Gets the tile map.
	 *
	 * @return the tile map
	 */
	public final TileMap getTileMap() {
		return tileMap;
	}

	/**
	 * Sets the cwidth.
	 *
	 * @param pCwidth
	 *            the new cwidth
	 */
	public final void setCwidth(final int pCwidth) {
		this.cwidth = pCwidth;
	}

	/**
	 * Sets the cheight.
	 *
	 * @param pCheight
	 *            the new cheight
	 */
	public final void setCheight(final int pCheight) {
		this.cheight = pCheight;
	}

	/**
	 * Checks if is facing right.
	 *
	 * @return true, if is facing right
	 */
	public final boolean isFacingRight() {
		return facingRight;
	}

	//
	/**
	 * Sets the facing right.
	 *
	 * @param pFacingRight
	 *            the new facing right
	 */
	public final void setFacingRight(final boolean pFacingRight) {
		this.facingRight = pFacingRight;
	}

	/**
	 * Checks if is jumping.
	 *
	 * @return true, if is jumping
	 */
	public final boolean isJumping() {
		return jumping;
	}

	/**
	 * Sets the jumping.
	 *
	 * @param pJumping
	 *            the new jumping
	 */
	public final void setJumping(final boolean pJumping) {
		this.jumping = pJumping;
	}

	/**
	 * Checks if is falling.
	 *
	 * @return true, if is falling
	 */
	public final boolean isFalling() {
		return falling;
	}

	/**
	 * Sets the falling.
	 *
	 * @param pFalling
	 *            the new falling
	 */
	public final void setFalling(final boolean pFalling) {
		this.falling = pFalling;
	}

	/**
	 * Gets the mov speed.
	 *
	 * @return the mov speed
	 */
	public final double getMovSpeed() {
		return movSpeed;
	}

	/**
	 * Sets the mov speed.
	 *
	 * @param pMovSpeed
	 *            the new mov speed
	 */
	public final void setMovSpeed(final double pMovSpeed) {
		this.movSpeed = pMovSpeed;
	}

	/**
	 * Gets the max speed.
	 *
	 * @return the max speed
	 */
	public final double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the max speed.
	 *
	 * @param pMaxSpeed
	 *            the new max speed
	 */
	public final void setMaxSpeed(final double pMaxSpeed) {
		this.maxSpeed = pMaxSpeed;
	}

	/**
	 * Gets the stop speed.
	 *
	 * @return the stop speed
	 */
	public final double getStopSpeed() {
		return stopSpeed;
	}

	/**
	 * Sets the stop speed.
	 *
	 * @param pStopSpeed
	 *            the new stop speed
	 */
	public final void setStopSpeed(final double pStopSpeed) {
		this.stopSpeed = pStopSpeed;
	}

	/**
	 * Gets the fall speed.
	 *
	 * @return the fall speed
	 */
	public final double getFallSpeed() {
		return fallSpeed;
	}

	/**
	 * Gets the max fall speed.
	 *
	 * @return the max fall speed
	 */
	public final double getMaxFallSpeed() {
		return maxFallSpeed;
	}

	/**
	 * Sets the fall speed.
	 *
	 * @param pFallSpeed
	 *            the new fall speed
	 */
	public final void setFallSpeed(final double pFallSpeed) {
		this.fallSpeed = pFallSpeed;
	}

	/**
	 * Sets the max fall speed.
	 *
	 * @param pMaxFallSpeed
	 *            the new max fall speed
	 */
	public final void setMaxFallSpeed(final double pMaxFallSpeed) {
		this.maxFallSpeed = pMaxFallSpeed;
	}

	/**
	 * Gets the jump start.
	 *
	 * @return the jump start
	 */
	public final double getJumpStart() {
		return jumpStart;
	}

	//
	/**
	 * Sets the jump start.
	 *
	 * @param pJumpStart
	 *            the new jump start
	 */
	public final void setJumpStart(final double pJumpStart) {
		this.jumpStart = pJumpStart;
	}

	/**
	 * Gets the stop jump speed.
	 *
	 * @return the stop jump speed
	 */
	public final double getStopJumpSpeed() {
		return stopJumpSpeed;
	}

	/**
	 * Sets the stop jump speed.
	 *
	 * @param pStopJumpSpeed
	 *            the new stop jump speed
	 */
	public final void setStopJumpSpeed(final double pStopJumpSpeed) {
		this.stopJumpSpeed = pStopJumpSpeed;
	}

	/**
	 * Sets the sprite.
	 *
	 * @param pSprite
	 *            the new sprite
	 */
	public final void setSprite(final BufferedImage pSprite) {
		this.sprite = pSprite;
	}

	/**
	 * Checks if is left.
	 *
	 * @return true, if is left
	 */
	public final boolean isLeft() {
		return left;
	}

	/**
	 * Checks if is right.
	 *
	 * @return true, if is right
	 */
	public final boolean isRight() {
		return right;
	}

	/**
	 * Checks if is up.
	 *
	 * @return true, if is up
	 */
	public final boolean isUp() {
		return up;
	}

	/**
	 * Checks if is down.
	 *
	 * @return true, if is down
	 */
	public final boolean isDown() {
		return down;
	}

	/**
	 * Sets the dx.
	 *
	 * @param pDx
	 *            the new dx
	 */
	public final void setDx(final double pDx) {
		this.dx = pDx;
	}

	/**
	 * Sets the dy.
	 *
	 * @param pDy
	 *            the new dy
	 */
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

	/**
	 * Sets the alive.
	 *
	 * @param pIsAlive
	 *            the new alive
	 */
	public final void setAlive(final boolean pIsAlive) {
		this.isAlive = pIsAlive;
	}

}
