package sem.group47.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * Waterfall class.
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
	 * constructor.
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
	 * Gets the next position.
	 *
	 */
	private void getNextXPosition() {

		if (isLeft()) {
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
		} else if (isRight()) {
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

	}

	/**
	 * Gets the next y position.
	 *
	 */
	private void getNextYPosition() {
		if (isFalling()) {
			setRight(true);
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
	 *            .
	 */
	public final boolean playerInteraction(final Player player) {

		if (player.intersects(this)) {
			player.canMove(false);
			player.setPosition(getx(), gety()-15);
			return true;
		} else {
			player.canMove(true);
			return false;
		}

	}

	@Override
	public final void update() {
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
	}

}
