package sem.group47.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Waterfall class, creates a waterfall object that moves through the stage.
 *
 * @author Bas
 *
 */
public class Waterfall extends MapObject {

	/**
	 * xpos and xpos2.
	 */
	private double xpos, xpos2 = 0;

	/**
	 * Constructor.
	 *
	 * @param tm
	 *            TileMap
	 */
	public Waterfall(final TileMap tm) {
		super(tm);
		setWidth(16);
		setHeight(16);
		setCwidth(16);
		setCheight(16);

		setMovSpeed(0.3);
		setStopSpeed(.4);
		setMaxSpeed(3);
		setFallSpeed(.35);
		setMaxFallSpeed(6.0);
		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/waterfall/waterfall.png"));
			setSprite(spritesheet.getSubimage(68, 0, 15, 15));

		} catch (Exception e) {
			Log.error("IO Read", "Could not file waterfall sprite");
			e.printStackTrace();
		}

	}

	/**
	 * Sets the next X position that the waterfall should move to.
	 */
	private void setNextXPosition() {
		if (isLeft()) {
			getNextLeftXPosition();
		} else if (isRight()) {
			getNextRightXPosition();
		}
	}

	/**
	 * Gets the next left x position.
	 *
	 * @return the next left x position
	 */
	private void getNextLeftXPosition() {
		if (getDx() == 0.3) {
			setDx(0.0);
		}
		xpos2 = 0;
		setDx(getDx() - getMovSpeed());

		if (xpos == getXpos()) {
			setLeft(false);
			setRight(true);
		}
		xpos = getXpos();

		if (getDx() < -getMaxSpeed()) {
			setDx(-getMaxSpeed());
		}
	}

	/**
	 * Gets the next right x position.
	 *
	 * @return the next right x position
	 */
	private void getNextRightXPosition() {
		xpos = 0;
		setDx(getDx() + getMovSpeed());

		if (xpos2 >= getXpos()) {
			setLeft(true);
			setRight(false);
		}
		xpos2 = getXpos();

		if (getDx() > getMaxSpeed()) {
			setDx(getMaxSpeed());
		}
	}

	/**
	 * Sets the next y position that the waterfall should move to.
	 */
	private void setNextYPosition() {
		if (isFalling()) {
			setLeft(true);
			setDx(0);
			setDy(getDy() + getFallSpeed());

			if (getDy() > getMaxFallSpeed()) {
				setDy(getMaxFallSpeed());
			}
		} else {
			setDy(0);
		}
	}

	/**
	 * Lets the player interact with the waterfall.
	 *
	 * @param player
	 *            the player.
	 * @return boolean if playerIteraction is allowed
	 */
	public final boolean playerInteraction(final Player player) {
		if (player.intersects(this)) {
			player.canMove(false);
			player.setPosition(getx(), gety() - 15);
			return true;
		} else {
			player.canMove(true);
			return false;
		}

	}

	@Override
	public final void update() {
		setNextXPosition();
		setNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
	}

}
