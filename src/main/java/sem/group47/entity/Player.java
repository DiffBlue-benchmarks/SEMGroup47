package sem.group47.entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sem.group47.gamestate.GameStateManager;
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
	
	/** If the player is attacking or not */
	private boolean isAttacking;	
	
	/** If the player is idle or not */
	private boolean isIdle;	
	
	/** The projectiles. */
	private ArrayList<Projectile> projectiles;
	
	/** The animation. */
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {
		3,2,3	
	};

	/** Animation actions*/
	public static final int IDLE = 0;
	public static final int WALKING = 1;
	public static final int ATTACK = 2;

	/**
	 * Instantiates a new player.
	 *
	 * @param tm
	 *            the tm
	 */
	public Player(final TileMap tm) {
		super(tm);
		
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

		setFacingRight(true);

		// lives = 3;
		maxLives = lives;
		extraLive = 300;
		fireDelay = 500;
		isAttacking = false;

		projectiles = new ArrayList<Projectile>();

		try {
			
			BufferedImage spritesheet = ImageIO.read(
				getClass().getResourceAsStream("/player/playerv2.png")
			);
			
			sprites = new ArrayList<BufferedImage[]>();
			for(int i = 0; i < 3; i++) {
				
				BufferedImage[] bi =
					new BufferedImage[numFrames[i]];
				
				for(int j = 0; j < numFrames[i]; j++) {
						bi[j] = spritesheet.getSubimage(
								j * getWidth(),
								i * getHeight(),
								getWidth(),
								getHeight()
						);
					
					
					
				}
				
				sprites.add(bi);
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
		Log.info("Player action", "Player instance created");
	}

	/**
	 * Gets the projectiles.
	 *
	 * @return the projectiles
	 */
	public final ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Update. Called every frame. Updates player position, looks for collision
	 * and then puts the player in the new position
	 */
	public final void update() {
		updateProjectiles();
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
	 * Fire projectile.
	 */
	public final void fireProjectile() {
		if (getDown()) {
			if (lastFireTime + fireDelay < System.currentTimeMillis()) {
				lastFireTime = System.currentTimeMillis();
				Projectile projectile = new Projectile(getTileMap());
				projectile.setPosition(getXpos(), getYpos());
				if (!isFacingRight()) {
					projectile.setDx(projectile.getDx() * -1);
				}
				isAttacking = true;
				isIdle = false;
				projectiles.add(projectile);
				Log.info("Player Action", "Bubble fired");
			}
		}
		
	}

	/**
	 * checks what happens when the player directly collides with an enemy.
	 *
	 * @param enemies
	 *            enemies
	 * @param gsm
	 *            the gsm
	 */
	public final void directEnemyCollision(final ArrayList<Enemy> enemies,
			final GameStateManager gsm) {

		for (int i = 0; i < enemies.size(); i++) {
			if (intersects(enemies.get(i))) {
				if (enemies.get(i).isCaught()) {

					setScore(enemies.get(i).getScorePoints());
					enemies.remove(i);
					Log.info("Player Action", "Player collision with Caught Enemy");
				} else if (getLives() > 1) {
					hit(1);
					Log.info("Player Action", "Player collision with Enemy");
				} else {

					gsm.setState(GameStateManager.GAMEOVERSTATE);
					Log.info("Player Action", "Game over");

					return;

				}
			}
		}

	}

	/**
	 * lets the player interact with a projectile, enabling him to jump on it
	 * and lift upwards, or kick against it.
	 */
	public final void interactWithProjectile() {
		for (int j = 0; j < getProjectiles().size(); j++) {

			if (intersects(getProjectiles().get(j))) {

				if (getProjectiles().get(j).getFloatDelay() <= 0) {

					if (getYpos() <= getProjectiles().get(j).getYpos()) {
						setFalling(false);
						setDy((getProjectiles().get(j).getDy() - 0.1));
					} else if (isRight() || (isJumping() && isRight())) {
						getProjectiles().get(j).setDx(2);
						getProjectiles().get(j).setFloatDelay(1000);
					} else if (isLeft() || (isJumping() && isLeft())) {
						getProjectiles().get(j).setDx(-2);
						getProjectiles().get(j).setFloatDelay(1000);
					}

				}

			}
		}
	}

	/**
	 * checks what happens when the player indirectly (projectile) collides with
	 * an enemy.
	 * 
	 * @param enemies
	 *            enemies
	 */
	public final void indirectEnemyCollision(final ArrayList<Enemy> enemies) {
		for (int i = 0; i < enemies.size(); i++) {
			for (int j = 0; j < getProjectiles().size(); j++) {
				if (getProjectiles().get(j).intersects(enemies.get(i))) {
					getProjectiles().remove(j);
					j--;
					Log.info("Player Action", "Fired bubble hit enemy");
					enemies.get(i).setCaught();

				}
			}
		}
	}

	/**
	 * Flinching.
	 */
	public final void flinching() {
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
	public final void hit(final int damage) {
		if (flinching) {
			return;
		}
		lives -= damage;
		Log.info("Player Action", "Player lost a life");
		if (lives < 0) {
			lives = 0;
			Log.warning("Player info wrong", "Amount of lives of player was <0. Set back to 0");
		}
		if (lives == 0) {
			setAlive(false);
			Log.info("Player Action", "Player died");
		}

		setPosition(100d, 100d);
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

	public final void updateAnimation() {
		
		if(isAttacking) {
			if(currentAction != ATTACK) {
				currentAction = ATTACK;
				animation.setFrames(sprites.get(ATTACK));
				animation.setDelay(100);
			}
			if(animation.hasPlayedOnce()) {
				isIdle = true;
				isAttacking = false;
			}
		} else if(isIdle) {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
			}
		}
		else if(isLeft() || isRight()) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(200);
			}
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
		if (facingRight) {
			g.drawImage(animation.getImage(), (int) (getXpos() - getWidth() / (double) 2),
					(int) (getYpos() - getHeight() / (double) 2), null);
		} else {
			g.drawImage(animation.getImage(), (int) (getXpos() + getWidth() / (double) 2),
					(int) (getYpos() - getHeight() / (double) 2), -getWidth(), getHeight(), null);
		}
			
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
	public final void setScore(final int points) {
		score += points;
		Log.info("Player Action", "Player received "+points+" points");
		if (score == extraLive) {
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

}
