package sem.group47.entity.enemies;

import javax.imageio.ImageIO;

import sem.group47.entity.MapObject;
import sem.group47.tilemap.TileMap;

/**
 * The Class EnemyProjectile, contains the logic for creating an enemy
 * projectile object.
 */
public class EnemyProjectile extends MapObject {

	/** The life time in ms. */
	private int lifeTime;

	/** The last update time. */
	private long lastUpdateTime;

	/**
	 * Instantiates a new projectile.
	 *
	 * @param tm
	 *            the tm
	 */
	public EnemyProjectile(final TileMap tm) {
		super(tm);
		setAlive(true);
		setWidth(32);
		setHeight(32);
		setCwidth(20);
		setCheight(20);
		setDx(4.7);

		lifeTime = 7500;
		lastUpdateTime = System.currentTimeMillis();

		try {
			this.setSprite(ImageIO.read(getClass().getResourceAsStream(
					"/enemies/enemy2.png")));
			setSprite(getSprite().getSubimage(9 * 36, 0, 36, 36));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update.
	 */
	@Override
	public final void update() {
		long timePassed = System.currentTimeMillis() - lastUpdateTime;
		lifeTime -= timePassed;
		lastUpdateTime = System.currentTimeMillis();
		if (lifeTime <= 0) {
			setAlive(false);
			return;
		}

		if (getDx() > 0) {
			this.setInverseDraw(true);
		} else {
			this.setInverseDraw(false);
		}

		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
		if (getDx() == 0) {
			this.setAlive(false);
		}
	}

}
