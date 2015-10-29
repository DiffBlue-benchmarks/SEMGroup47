package sem.group47.entity.enemies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.MapObject;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class GroundEnemy, contains the logic for creating a ground enemy object.
 */
public class GroundEnemy extends Enemy {

	/** The last fire time. */
	private long lastFireTime;

	/** The projectiles. */
	private ArrayList<EnemyProjectile> projectiles;

	/** The current sprite. */
	int currentSprite;

	/** The animation Delay. */
	long animationChangeTime;

	/**
	 * Instantiates a new ground enemy.
	 *
	 * @param tm
	 *            the tm
	 * @param properties
	 *            the properties
	 */
	public GroundEnemy(final TileMap tm, final EnemyProperty properties) {
		super(tm);
		projectiles = new ArrayList<EnemyProjectile>();

		currentSprite = 0;
		animationChangeTime = System.nanoTime();

		setProperties(properties);
		setTimeCaught(0);
		this.setIsAngry(false);
		if (Math.round(Math.random()) == 0) {
			setLeft(true);
		} else {
			setRight(true);
		}
	}

	/**
	 * Gets the next position.
	 *
	 * @return the next x position
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
	 * @return the next y position
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
			setDy(getDy() - getProperties().getFloatSpeed());
			if (getDy() < getProperties().getMaxFloatSpeed()) {
				setDy(getProperties().getMaxFloatSpeed());
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

	@Override
	public void update() {
		movementDice();
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
		updateStates();
		updateAnimation();
		updateProjectiles();
	}

	/**
	 * Update the to be loaded sprite.
	 */
	private void updateAnimation() {
		if (!isCaught() && !isAngry()) {
			if (System.nanoTime() - 1e8 > animationChangeTime) {
				animationChangeTime = System.nanoTime();
				currentSprite += 1;
				if (currentSprite > 2) {
					currentSprite = 1;
					setSprite(getSpriteSheet().getSubimage(currentSprite * 36,
							getProperties().getSpriteSheetY() * 36, 36, 36));
					setInverseDraw(true);
				}
			}
		}

		if (!isCaught() && isAngry()) {
			if (System.nanoTime() - 1e8 > animationChangeTime) {
				animationChangeTime = System.nanoTime();
				currentSprite = ((currentSprite - 3 + 1) % 2) + 3;
				setSprite(getSpriteSheet().getSubimage(currentSprite * 36,
						getProperties().getSpriteSheetY() * 36, 36, 36));
				setInverseDraw(true);
			}
		}
	}

	/**
	 * Update projectiles.
	 */
	public final void updateProjectiles() {
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).getIsAlive()) {
				projectiles.get(i).update();
			} else {
				projectiles.remove(i);
				i--;
			}
		}
	}

	/**
	 * Draws the projectile.
	 *
	 * @param gr
	 *            the graphics
	 */
	@Override
	public final void draw(final Graphics2D gr) {
		super.draw(gr);

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(gr);
		}
	}

	/**
	 * Checks if player is in front of us and fires and checks projectile
	 * collision.
	 *
	 * @param o
	 *            the o
	 * @return true, if successful
	 */
	@Override
	public final boolean projectileCollision(final MapObject o) {
		if (getProperties().canFire() == false) {
			return false;
		}

		if (!this.isCaught()
				&& lastFireTime + getProperties().getFireDelay() < System
						.currentTimeMillis()) {
			if (Math.abs(o.gety() - this.gety()) < 30
					&& ((facingRight && o.getx() > this.getx()) || (!facingRight && o
							.gety() > this.gety()))) {
				AudioPlayer.play("fire");
				lastFireTime = System.currentTimeMillis();
				EnemyProjectile projectile = new EnemyProjectile(getTileMap());
				projectile.setSprite(getSpriteSheet().getSubimage(9 * 36,
						getProperties().getSpriteSheetY() * 36, 36, 36));
				projectile.setPosition(getXpos(), getYpos());
				if (!isFacingRight()) {
					projectile.setDx(getProperties().getProjectileSpeed() * -1);
				} else {
					projectile.setDx(getProperties().getProjectileSpeed());
				}
				projectiles.add(projectile);
				Log.info("Enemy Action", "Projectile fired");
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).intersects(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Update states.
	 */
	public final void updateStates() {
		if (isCaught()
				&& (System.nanoTime() - getTimeCaught()) / 1000000000.0d > getProperties()
						.getBreakFreeTime()) {
			setCaught(false);
		}
		if (isAngry()
				&& (System.nanoTime() - getAngryStartTime()) / 1000000000.0d > 10) {
			setIsAngry(false);
		}
	}
}
