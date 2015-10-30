package sem.group47.entity;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import sem.group47.tilemap.TileMap;

/**
 * The Class Projectile.
 */
public class Projectile extends MapObject {
	
	/** The life time in ms. */
	private int lifeTime;
	
	/** The float delay. */
	private int floatDelay;
	
	/** The last update time. */
	private long lastUpdateTime;
	
	/** The floating. */
	private boolean floating;
	
	/** The float speed. */
	private double floatSpeed;
	
	/**
	 * Instantiates a new projectile.
	 * 
	 * @param tm
	 *           the tm
	 */
	public Projectile(final TileMap tm) {
		super(tm);
		setAlive(true);
		setWidth(32);
		setHeight(32);
		setCwidth(20);
		setCheight(20);
		setDx(4.7);
		
		lifeTime = 7500;
		floatDelay = 700;
		lastUpdateTime = System.currentTimeMillis();
		floating = false;
		floatSpeed = .02;
		
		try {
			BufferedImage spritesheet = ImageIO.read(getClass()
					.getResourceAsStream("/player/bubbles.png"));
			setSprite(spritesheet.getSubimage(96, 0, 32, 32));
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
		floatDelay -= timePassed;
		lifeTime -= timePassed;
		lastUpdateTime = System.currentTimeMillis();
		if (floatDelay <= 0) {
			setDx(0);
			setDy(getDy() - floatSpeed);
		}
		if (lifeTime <= 0) {
			setAlive(false);
			return;
		}
		
		checkTileMapCollision();
		setPosition(getXposNew(), getYposNew());
	}
	
	/**
	 * Gets the float delay.
	 * 
	 * @return the float delay
	 */
	public final int getFloatDelay() {
		return floatDelay;
	}
	
	/**
	 * Sets the float delay.
	 * 
	 * @param pFloatDelay
	 *           the new float delay
	 */
	public final void setFloatDelay(final int pFloatDelay) {
		this.floatDelay = pFloatDelay;
	}
	
}
