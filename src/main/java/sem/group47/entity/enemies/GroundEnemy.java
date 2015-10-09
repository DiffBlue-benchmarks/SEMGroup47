package sem.group47.entity.enemies;

import sem.group47.audio.AudioPlayer;
import sem.group47.tilemap.TileMap;


public class GroundEnemy extends Enemy {

	public GroundEnemy(TileMap tm) {
		super(tm);
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
			if (getDy() < getMaxFloatSpeed()) {
			 setDy(getMaxFloatSpeed());
			}
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
	public void update() {
		movementDice();
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
		updateStates();
	}

	public final void updateStates() {
		if(isCaught() && (System.nanoTime() - getTimeCaught())/1000000000.0d > getTimeUntillBreakFree()) {
			setCaught(false);
		}
		if(isAngry() && (System.nanoTime() - getAngryTime())/1000000000.0d > 10) {
			setIsAngry(false);
		}
	}
}
