package sem.group47.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.enemies.Enemy;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class Player.
 */
public class Player extends MapObject {

	/** The lives. */
	private int lives;

	/** The max lives. */
	private int maxLives;

	/** The score. */
	private int score;

	/** The extra live. */
	private int extraLive;

	/** The flinching. */
	private boolean flinching;

	/** The flinch timer. */
	private long flinchTimer;

	/** The last fire time. */
	private long lastFireTime;

	/** The fire delay. */
	private int fireDelay;

	/** If the player is attacking or not. */
	private boolean isAttacking;

	/** If the player is idle or not. */
	private boolean isIdle;

	/** Projectile list contained in ProjectileList class. */
	private ProjectileList projectileList;

	/** The animation. */
	private ArrayList<BufferedImage[]> sprites;

	/** Number of frames for each animation action in order. */
	private final int[] numFrames = { 3, 2, 3 };

	/** Animation actions # for idle state. */
	public static final int IDLE = 0;

	/** Animation actions # for walking state. */
	public static final int WALKING = 1;

	/** Animation actions # for attacking state. */
	public static final int ATTACK = 2;

	/** The speed at which bubbles fire. */
	private double bubbleSpeed;

	/** The size of bubbles fired. **/
	private int bubbleSize;

	/**
	 * Instantiates a new player.
	 *
	 * @param tm
	 *            the tm
	 */
	public Player(final TileMap tm) {
		super(tm);

		Log.info("Player action", "Player instance created");

		setWidth(38);
		setHeight(32);
		setCwidth(38);
		setCheight(32);
		setMovSpeed(2.5);
		setMaxSpeed(2.5);
		setStopSpeed(2.5);

		setFallSpeed(.35);
		setMaxFallSpeed(6.0);
		setJumpStart(-10);
		setStopJumpSpeed(.3);
		setBubbleSpeed(4.7);
		setBubbleSize(32);

		setFacingRight(true);

		// lives = 3;
		maxLives = lives;
		extraLive = 300;
		fireDelay = 500;
		isAttacking = false;

		projectileList = new ProjectileList();

		try {

			BufferedImage spritesheet = ImageIO.read(getClass()
				.getResourceAsStream("/player/playerv2.png"));

			sprites = new ArrayList<BufferedImage[]>();
			for (int i = 0; i < 3; i++) {

				BufferedImage[] bi =
						new BufferedImage[numFrames[i]];

				for (int j = 0; j < numFrames[i]; j++) {
					bi[j] = spritesheet.getSubimage(
							j * getWidth(),	i * getHeight(),
							getWidth(), getHeight());
				}
				sprites.add(bi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		Log.info("Player action", "Player instance created");
	}

	/**
	 * Update. Called every frame. Updates player position,
	 * looks for collision and then puts the player in the new position
	 */
	@Override
	public final void update() {
		projectileList.update();
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
		updateAnimation();
		fireProjectile();
		interactWithProjectile();
		flinching();
	}

	/**
	 * Checks collision for all projectiles.
	 *
	 * @param enemies
	 *            enemies
	 */
	public final void indirectEnemyCollision(
			final ArrayList<Enemy> enemies) {
		projectileList.indirectEnemyCollision(enemies);
	}

	/**
	 * Fire projectile.
	 */
	public final void fireProjectile() {
		if (getDown()) {
			if (lastFireTime + fireDelay
					< System.currentTimeMillis()) {
				AudioPlayer.play("fire");
				lastFireTime = System.currentTimeMillis();
				Projectile projectile = new Projectile(
						getTileMap());
				projectile.setPosition(getXpos(), getYpos());
				if (!isFacingRight()) {
					projectile.setDx(bubbleSpeed * -1);
				} else {
					projectile.setDx(bubbleSpeed);
				}

				isAttacking = true;
				isIdle = false;

				projectile.setWidth(bubbleSize);
				projectile.setHeight(bubbleSize);
				projectile.setCwidth((int) (bubbleSize / 1.6f));
				projectile.setCheight((int)
						(bubbleSize / 1.6f));

				projectileList.addProjectile(projectile);
				Log.info("Player Action", "Bubble fired");
			}
		}
	}

	/**
	 * takes a life, or ends the game.
	 */
	public final void kill() {
		if (getLives() > 1) {
			hit(1);
			Log.info("Player Action",
					"Player collision with Enemy");

		} else {
			AudioPlayer.play("crash");
			hit(1);
			Log.info("Player Action",
					"Player collision with Enemy");
		}
	}

	/**
	 * lets the player interact with a projectile,
	 * enabling him to jump on it
	 * and lift upwards, or kick against it.
	 */
	public final void interactWithProjectile() {
		projectileList.playerInteraction(this);
	}

	/**
	 * Flinching.
	 */
	public final void flinching() {
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer)
					/ 1000000;
			if (elapsed > 2500) {
				flinching = false;
			}
		}
	}

	/**
	 * If player gets hit, one live will be removed and player gets into a
	 * flinching state. When in flinching state he cannot take damage.
	 *
	 * @param damage
	 *            the damage
	 */
	public final void hit(final int damage) {
		if (flinching) {
			return;
		}
		lives -= damage;
		Log.info("Player Action", "Player lost a life");
		if (lives < 0) {
			lives = 0;
			Log.warning("Player info wrong",
				"Amount of lives of player was <0. "
				+ "Set back to 0");
		}
		if (lives == 0) {
			AudioPlayer.stopAll();
			AudioPlayer.play("dead");
			setAlive(false);
			Log.info("Player Action", "Player died");
		}

		setPosition(getTileMap().getWidth() / 2,
				getTileMap().getHeight() / 2);
		setVector(0, 0);
		flinching = true;
		flinchTimer = System.nanoTime();

	}

	/**
	 * Gets the next x position.
	 *
	 */
	public final void getNextXPosition() {
		if (isLeft()) {
			isIdle = false;
			Log.info("Player Action", "Player moved left");
			setDx(getDx() - getMovSpeed());
			if (getDx() < -getMaxSpeed()) {
				setDx(-getMaxSpeed());
			}
		} else if (isRight()) {
			isIdle = false;
			Log.info("Player Action", "Player moved right");
			setDx(getDx() + getMovSpeed());
			if (getDx() > getMaxSpeed()) {
				setDx(getMaxSpeed());
			}
		} else {
			isIdle = true;
			setDx(0);
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
	public final void getNextYPosition() {
		if (isUp()) {
			setJumping(true);
			Log.info("Player Action", "Player jumped");
		}
		if (isJumping() && !isFalling()) {
			AudioPlayer.play("jump");
			setDy(getJumpStart());
			setFalling(true);
		}
		if (isFalling()) {
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
		}
	}

	/**
	 * Update function for the animations that are rendered.
	 */
	public final void updateAnimation() {

		if (isAttacking) {
			if (currentAction != ATTACK) {
				currentAction = ATTACK;
				animation.setFrames(sprites.get(ATTACK));
				animation.setDelay(100);
			}
			if (animation.hasPlayedOnce()) {
				isIdle = true;
				isAttacking = false;
			}
		} else if (isIdle && currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
		} else if ((isLeft() || isRight())
				&& currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(200);
			}

		animation.update();
	}

	/**
	 * Draws the player.
	 *
	 * @param g
	 *            the g
	 */
	@Override
	public final void draw(final Graphics2D g) {
		if (!flinching || Math.round(Math.random() * 1) == 0) {
			if (facingRight) {
				g.drawImage(animation.getImage(),
						(int) (getXpos() - getWidth()
						/ (double) 2),
						(int) (getYpos() - getHeight()
						/ (double) 2), null);
			} else {
				g.drawImage(animation.getImage(),
						(int) (getXpos() + getWidth()
						/ (double) 2),
						(int) (getYpos() - getHeight()
						/ (double) 2),
						-getWidth(), getHeight(), null);
			}
		}

		projectileList.draw(g);
	}

	/**
	 * Sets the score.
	 *
	 * @param points
	 *            the new score
	 */
	public final void setScore(final int points) {

		score += points;

		if (score != 0) {
			AudioPlayer.play("bubblePop");
		}

		Log.info("Player Action", "Player received "
				+ points + " points");
		if (score >= extraLive) {
			AudioPlayer.play("extraLife");
			lives++;
			extraLive += 300;
			Log.info("Player Action", "Player received a new life");
		}
	}

	/**
	 * Gets the extra live.
	 *
	 * @return the extra live
	 */
	public final int getExtraLive() {
		return extraLive;
	}

	/**
	 * Sets the flinch.
	 *
	 * @param b
	 *            the new flinch
	 */
	public final void setFlinch(final boolean b) {
		flinching = b;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public final int getLives() {
		return lives;
	}

	/**
	 * Gets the max lives.
	 *
	 * @return the max lives
	 */
	public final int getMaxLives() {
		return maxLives;
	}

	/**
	 * Gets the bubble speed.
	 * @return the bubble speed
	 */
	public final double getBubbleSpeed() {
		return bubbleSpeed;
	}

	/**
	 * Gets the bubble size.
	 * @return the bubble size.
	 */
	public final int getBubbleSize() {
		return bubbleSize;
	}


	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * Sets the lives.
	 *
	 * @param pLives
	 *            the new lives
	 */
	public final void setLives(final int pLives) {
		this.lives = pLives;
	}

	/**
	 * Sets the extra live.
	 *
	 * @param pExtraLive
	 *            the new extra live
	 */
	public final void setExtraLive(final int pExtraLive) {
		this.extraLive = pExtraLive;
	}

	/**
	 * Sets the bubble speed.
	 *
	 * @param speed
	 *            The bubble speed
	 */
	public final void setBubbleSpeed(final double speed) {
		this.bubbleSpeed = speed;
	}



	/**
	 * Sets the bubble size.
	 *
	 * @param size
	 *            The bubble size
	 */
	public final void setBubbleSize(final int size) {
		bubbleSize = size;
	}

}
