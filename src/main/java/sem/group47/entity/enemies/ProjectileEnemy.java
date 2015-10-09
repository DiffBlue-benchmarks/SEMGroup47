package sem.group47.entity.enemies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.MapObject;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

public class ProjectileEnemy extends GroundEnemy {
	
	/** The fire delay. */
	private int fireDelay;
	
	/** The last fire time. */
	private long lastFireTime;
	
	private double projSpeed;
	
	ArrayList<EnemyProjectile> projectiles;
	public ProjectileEnemy(TileMap tm) {
		super(tm);
		setScorePoints(100);
		setWidth(36);
		setHeight(36);
		setCwidth(36);
		setCheight(36);
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

		
		projSpeed = 2.5;
		fireDelay = 1500;
		projectiles = new ArrayList<EnemyProjectile>();

		try {
			this.setSpriteSheet(ImageIO.read(getClass().getResourceAsStream(
					"/enemies/enemy2.png")));
			setSprite(getSpriteSheet().getSubimage(36, 0, 36, 36));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setIsAngry(false);
		setTimeCaught(0);
		setTimeUntillBreakFree(10);
	}
	
	public void update() {
		super.update();
		updateProjectiles();
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
	 * Draw.
	 *
	 * @param gr
	 *            the graphics
	 */
	public void draw(final Graphics2D gr) {
		if (!facingRight) {
			gr.drawImage(getSprite(), (int) (getx() - getWidth() / (double) 2),
					(int) (gety() - getHeight() / (double) 2), null);
		} else {
			gr.drawImage(getSprite(), (int) (getx() + getHeight() / (double) 2),
					(int) (gety() - getHeight() / (double) 2), -getWidth(), getHeight(), null);
		}
		
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(gr);
		}
	}
	
	/**
	 * Sets the caught.
	 * @param isCaught
	 * Whether or not the enemy is caught
	 */
	public void setCaught(boolean isCaught) {
		super.setCaught(isCaught);
		if (isCaught) {
			setSprite(getSpriteSheet().getSubimage(7*36, 0, 36, 36));
		}
	}
	
	/**
	 * Sets the isAngry.
	 * @param angry
	 * whether or not the enemy is angry
	 */
	public void setIsAngry(boolean angry) {
		super.setIsAngry(angry);
		if (angry) {
			setSprite(getSpriteSheet().getSubimage(3*36, 0, 36, 36));
		} else {
			setSprite(getSpriteSheet().getSubimage(36, 0, 36, 36));
		}
	}
	
	/**
	 * Checks if player is in front of us and fires and checks projectile collision
	 */
	public boolean projectileCollision(MapObject o) {
		if (!this.isCaught() && lastFireTime + fireDelay < System.currentTimeMillis()) {
			if(Math.abs(o.gety() - this.gety()) < 30 && 
					((facingRight && o.getx() > this.getx() ) ||
							(!facingRight && o.gety() > this.gety()))) {
				AudioPlayer.play("fire");
				lastFireTime = System.currentTimeMillis();
				EnemyProjectile projectile =
				  new EnemyProjectile(getTileMap());
				projectile.setPosition(getXpos(), getYpos());
				if (!isFacingRight()) {
					projectile.setDx(projSpeed * -1);
				} else {
				 projectile.setDx(projSpeed);
				}
				projectiles.add(projectile);
				Log.info("Enemy Action", "Projectile fired");
			}
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).intersects(o)) {
				return true;
			}
		}
		return false;
	}
	
}
