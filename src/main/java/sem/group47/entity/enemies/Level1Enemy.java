package sem.group47.entity.enemies;

import javax.imageio.ImageIO;

import sem.group47.tilemap.TileMap;

/**
 * The Class Level1Enemy.
 */
public class Level1Enemy extends GroundEnemy {

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
		setNormalMovSpeed(1.2);
		setAngryMovSpeed(3);
		setStopSpeed(.4);

		setFallSpeed(.35);
		setFloatSpeed(.1);
		setMaxFloatSpeed(-4.5);
		setMaxFallSpeed(6.0);
		setJumpStart(-10.0);
		setStopJumpSpeed(.3);
		setFacingRight(true);

		try {
			this.setSpriteSheet(ImageIO.read(getClass()
					.getResourceAsStream("/enemies/level1.gif")));
			setSprite(getSpriteSheet().getSubimage(0, 0, 30, 30));
		} catch (Exception e) {
			e.printStackTrace();
		}

		setIsAngry(false);
		setTimeCaught(0);
		setTimeUntillBreakFree(10);
	}
}
