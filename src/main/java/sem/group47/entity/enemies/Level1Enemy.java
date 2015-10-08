package sem.group47.entity.enemies;

import java.awt.Graphics2D;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.Enemy;
import sem.group47.tilemap.TileMap;

/**
 * The Class Level1Enemy.
 */
public class Level1Enemy extends Enemy {

	/**
	 * Instantiates a new level1 enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Level1Enemy(final TileMap tm) {
		super(tm);

		setScorePoints(100);
		setWidth(30);
		setHeight(30);
		setCwidth(30);
		setCheight(30);
		setMovSpeed(0.3);
		setMaxSpeed(1.6);
		setStopSpeed(.4);

		setFallSpeed(.35);
		setFloatSpeed(.1);
		setMaxFloatSpeed(-4.5);
		setMaxFallSpeed(6.0);
		setJumpStart(-10.0);
		setStopJumpSpeed(.3);
		setFacingRight(true);

		setIsAngry(false);
		setTimeCaught(0);
		setTimeUntillBreakFree(10);

		if (Math.round(Math.random()) == 0) {
			setLeft(true);
		} else {
			setRight(true);
		}

	}

	/**
	 * Gets the next position.
	 *
	 */
	private void getNextXPosition() {
		if (isLeft()) {
			setDx(getDx() - getMovSpeed());
			if (getDx() < -getMaxSpeed()) {
				setDx(-getMaxSpeed());
			}
		} else if (isRight()) {
			setDx(getDx() + getMovSpeed());
			if (getDx() > getMaxSpeed()) {
				setDx(getMaxSpeed());
			}
		} else {
			if (getDx() > 0) {
				setDx(getDx() - getStopSpeed());
				if (getDx() < 0) {
					setDx(0);
				}
			} else if (getDx() < 0) {
				setDx(getDx() + getStopSpeed());
				if (getDx() > 0) {
					setDx(0);
				}
			}
		}
		if (getDx() > 0) {
			setFacingRight(true);
		} else if (getDx() < 0) {
			setFacingRight(false);
		}

	}

	/**
	 * Gets the next y position.
	 *
	 */
	private void getNextYPosition() {
		if (getUp()) {
			setJumping(true);
		}
		if (isJumping() && !isFalling()) {
			setDy(getJumpStart());
			setFalling(true);
			AudioPlayer.play("jump");
		}
		if (isCaught()) {
			setDy(getDy() - getFloatSpeed());
			if (getDy() < getMaxFloatSpeed())
			 setDy(getMaxFloatSpeed());
			setDx(0);
		} else if (isFalling()) {
			setDy(getDy() + getFallSpeed());
			if (getDy() > 0) {
				setJumping(false);
			}
			if (getDy() < 0 && !isJumping()) {
				setDy(getDy() + getStopJumpSpeed());
			}
			if (getDy() > getMaxFallSpeed()) {
				setDy(getMaxFallSpeed());
			}
		} else {
			setDy(0);
		}
	}

	/**
	 * Movement dice.
	 */
	private void movementDice() {
		double dice = Math.random() * 1000;
		if (dice < 5) {
			setUp(true);
		} else if (dice < 15) {
			setLeft(true);
			setRight(false);
			setUp(false);
		} else if (dice < 25) {
			setLeft(false);
			setRight(true);
			setUp(false);
		} else {
			setUp(false);
		}
	}

	/*
	 * Update
	 */
	@Override
	public final void update() {
		movementDice();
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
		if(isCaught() && (System.nanoTime() - getTimeCaught())/1000000000 > getTimeUntillBreakFree()) {
			setCaught(false);
		}
	}

	/*
	 * Draw
	 */
	@Override
	public final void draw(final Graphics2D g) {
		super.draw(g);
	}

}
