package sem.group47.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sem.group47.gamestate.GameStateManager;
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
	private int fireDelay; // ms

	/** The projectiles. */
	private ArrayList<Projectile> projectiles;

	/**
	 * Instantiates a new player.
	 *
	 * @param tm
	 *            the tm
	 */
	public Player(TileMap tm) {
		super(tm);
		width = 38;
		height = 32;
		cwidth = 38;
		cheight = 32;
		movSpeed = 2.5;
		maxSpeed = 2.5;
		stopSpeed = 2.5;

		fallSpeed = .35;
		maxFallSpeed = 6.0;
		jumpStart = -10;
		stopJumpSpeed = .3;

		facingRight = true;

		lives = maxLives = 3;
		extraLive = 300;
		fireDelay = 500;

		projectiles = new ArrayList<Projectile>();

		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/player/player.png"));
			sprite = spritesheet.getSubimage(0, 0, 38, 32);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the projectiles.
	 *
	 * @return the projectiles
	 */
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Update. Called every frame. Updates player position, looks for collision
	 * and then puts the player in the new position
	 */
	public void update() {
		updateProjectiles();
		getNextXPosition();
		getNextYPosition();
		checkTileMapCollision();
		setPosition(xposNew, yposNew);
		fireProjectile();
		interactWithProjectile();
		flinching();

	}

	/**
	 * Update projectiles.
	 */
	public void updateProjectiles() {
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
	 * Fire projectile.
	 */
	public void fireProjectile() {
		if (down) {
			if (lastFireTime + fireDelay < System.currentTimeMillis()) {
				lastFireTime = System.currentTimeMillis();
				Projectile projectile = new Projectile(tileMap);
				projectile.setPosition(xpos, ypos);
				if (!facingRight)
					projectile.dx *= -1;
				projectiles.add(projectile);
			}
		}
	}

	/**
	 * checks what happens when the player directly collides with an enemy
	 * 
	 * @param enemies
	 */
	public void directEnemyCollision(ArrayList<Enemy> enemies,
			GameStateManager gsm) {
		for (int i = 0; i < enemies.size(); i++) {
			if (intersects(enemies.get(i))) {
				if (enemies.get(i).isCaught()) {

					setScore(enemies.get(i).getScorePoints());
					enemies.remove(i);
				} else if (getLives() > 1) {
					hit(1);
				} else {

					gsm.setState(GameStateManager.GAMEOVER);
					return;

				}
			}
		}

	}

	/**
	 * lets the player interact with a projectile, enabling him to jump on it
	 * and lift upwards, or kick against it.
	 */
	public void interactWithProjectile() {
		for (int j = 0; j < getProjectiles().size(); j++) {

			if (intersects(getProjectiles().get(j))) {

				if (getProjectiles().get(j).getFloatDelay() <= 0) {

					if (this.ypos <= getProjectiles().get(j).ypos) {
						falling = false;
						dy = getProjectiles().get(j).getDy() - 0.1;

					}

					else if (right || (jumping && right)) {
						getProjectiles().get(j).setDx(2);
						getProjectiles().get(j).setFloatDelay(1000);
					} else if (left || (jumping && left)) {
						getProjectiles().get(j).setDx(-2);
						getProjectiles().get(j).setFloatDelay(1000);
					}

				}

			}
		}
	}

	/**
	 * checks what happens when the player indirectly (projectile) collides with
	 * an enemy
	 * 
	 * @param enemies
	 */
	public void indirectEnemyCollision(ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < getProjectiles().size(); j++) {
				if (getProjectiles().get(j).intersects(enemies.get(i))) {
					getProjectiles().remove(j);
					j--;
					enemies.get(i).setCaught();

				}
			}
		}
	}

	/**
	 * Flinching.
	 */
	public void flinching() {
		if (flinching) {
			long elapsed = (System.nanoTime() - flinchTimer) / 1000000;
			if (elapsed > 1000) {
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
	public void hit(int damage) {
		if (flinching) {
			return;
		}
		lives -= damage;
		if (lives < 0) {
			lives = 0;
		}
		if (lives == 0) {
			isAlive = false;
		}

		setPosition(100d, 100d);
		flinching = true;
		flinchTimer = System.nanoTime();

	}

	/**
	 * Gets the next x position.
	 *
	 * @return the next x position
	 */
	public void getNextXPosition() {
		if (left) {
			dx -= movSpeed;
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} else if (right) {
			dx += movSpeed;
			if (dx > maxSpeed)
				dx = maxSpeed;
		} else {
			dx = 0;
		}
		if (dx > 0) {
			facingRight = true;
		} else if (dx < 0) {
			facingRight = false;
		}
	}

	/**
	 * Gets the next y position.
	 *
	 * @return the next y position
	 */
	public void getNextYPosition() {
		if (up) {
			jumping = true;
		}
		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}
		if (falling) {
			dy += fallSpeed;
			if (dy > 0) {
				jumping = false;
			}
			if (dy < 0 && !jumping) {
				dy += stopJumpSpeed;
			}
			if (dy > maxFallSpeed) {
				dy = maxFallSpeed;
			}
		}
	}

	/*
	 * Draws the player
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see sem.group47.entity.MapObject#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(g);
		}
	}

	/**
	 * Sets the score.
	 *
	 * @param points
	 *            the new score
	 */
	public void setScore(int points) {
		score += points;
		if (score == extraLive) {
			lives++;
			extraLive = 600;
		}
	}

	/**
	 * Gets the extra live.
	 *
	 * @return the extra live
	 */
	public int getExtraLive() {
		return extraLive;
	}

	/**
	 * Sets the flinch.
	 *
	 * @param b
	 *            the new flinch
	 */
	public void setFlinch(boolean b) {
		flinching = b;
	}

	/**
	 * Gets the mov speed.
	 *
	 * @return the mov speed
	 */
	public double getMovSpeed() {
		return movSpeed;
	}

	/**
	 * Sets the mov speed.
	 *
	 * @param movSpeed
	 *            the new mov speed
	 */
	public void setMovSpeed(double movSpeed) {
		this.movSpeed = movSpeed;
	}

	/**
	 * Gets the max speed.
	 *
	 * @return the max speed
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the max speed.
	 *
	 * @param maxSpeed
	 *            the new max speed
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * Sets the fall speed.
	 *
	 * @param fallSpeed
	 *            the new fall speed
	 */
	public void setFallSpeed(double fallSpeed) {
		this.fallSpeed = fallSpeed;
	}

	/**
	 * Gets the jumping.
	 *
	 * @return the jumping
	 */
	public boolean getJumping() {
		return jumping;
	}

	/**
	 * Sets the falling.
	 *
	 * @param fall
	 *            the new falling
	 */
	public void setFalling(boolean fall) {
		falling = fall;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Gets the max lives.
	 *
	 * @return the max lives
	 */
	public int getMaxLives() {
		return maxLives;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

}
