package sem.group47.entity.enemies;

import sem.group47.entity.Enemy;
import sem.group47.tilemap.TileMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/**
 * The Class Level1Enemy.
 */
public class Level1Enemy extends Enemy {

	/** The spritesheet. */
	BufferedImage spritesheet;

	/**
	 * Instantiates a new level1 enemy.
	 *
	 * @param tm
	 *            the tm
	 */
	public Level1Enemy(TileMap tm) {
		super(tm);

		scorePoints = 100;
		width = 30;
		height = 30;
		cwidth = 30;
		cheight = 30;
		movSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = .4;

		fallSpeed = .35;
		floatSpeed = .1;
		maxFallSpeed = 6.0;
		jumpStart = -10.0;
		stopJumpSpeed = .3;

		facingRight = true;
		if (Math.round(Math.random()) == 0)
			left = true;
		else
			right = true;

		// Load sprite

		try {
			spritesheet = ImageIO.read(getClass().getResourceAsStream(
					"/Enemies/Level1.gif"));
			sprite = spritesheet.getSubimage(0, 0, 30, 30);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the next position.
	 *
	 * @return the next position
	 */
	private void getNextPosition() {
		double dice = Math.random() * 1000;
		if (dice < 5) {
			up = true;
		} else if (dice < 15) {
			left = true;
			right = false;
			up = false;
		} else if (dice < 25) {
			left = false;
			right = true;
			up = false;
		} else {
			up = false;
		}

		if (left) { // If move left is pressed
			dx -= movSpeed;
			if (dx < -maxSpeed)
				dx = -maxSpeed;
		} else if (right) { // If move right is pressed
			dx += movSpeed;
			if (dx > maxSpeed)
				dx = maxSpeed;
		} else {
			if (dx > 0) {
				dx -= stopSpeed;
				if (dx < 0)
					dx = 0;
			} else if (dx < 0) {
				dx += stopSpeed;
				if (dx > 0)
					dx = 0;
			}
		}

		if (dx > 0)
			facingRight = true;
		else if (dx < 0)
			facingRight = false;

		if (up)
			jumping = true; // If jump is pressed

		if (jumping && !falling) {
			dy = jumpStart;
			falling = true;
		}

		if (caught) {
			dy -= floatSpeed;
			dx = 0;
		} else if (falling) {
			dy += fallSpeed;
			if (dy > 0)
				jumping = false;
			if (dy < 0 && !jumping)
				dy += stopJumpSpeed;
			if (dy > maxFallSpeed)
				dy = maxFallSpeed;
		} else {
			dy = 0;
		}
	}

	/*
	 * Update
	 */
	@Override
	public void update() {

		// update position
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);

	}

	/*
	 * Draw
	 */
	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

	/*
	 * SetCaught
	 */
	@Override
	public void setCaught() {
		caught = true;
		sprite = spritesheet.getSubimage(90, 0, 30, 30);
	}

}
