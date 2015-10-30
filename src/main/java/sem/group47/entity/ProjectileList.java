package sem.group47.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.entity.enemies.Enemy;
import sem.group47.main.DrawComposite;
import sem.group47.main.Log;

/**
 * The class ProjectileList, manages drawing and game logic for projectiles from
 * one player.
 * 
 * @author Karin
 *
 */
public class ProjectileList extends DrawComposite {

	/** The projectiles. */
	private ArrayList<Projectile> projectiles;

	/**
	 * Constructor initializes the list.
	 */
	public ProjectileList() {
		projectiles = new ArrayList<Projectile>();
	}

	/**
	 * Draws the projectiles.
	 */
	@Override
	public final void draw(final Graphics2D gr) {
		drawComponents(gr);
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
	 * Update projectiles.
	 */
	public final void update() {
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
	 * Add a projectile to the list.
	 * 
	 * @param p
	 *            the Projectile object.
	 */
	public final void addProjectile(final Projectile p) {
		projectiles.add(p);
		addComponent(p);
	}

	/**
	 * lets the player interact with a projectile, enabling him to jump on it
	 * and lift upwards, or kick against it.
	 * 
	 * @param player
	 *            The player to interact with.
	 */
	public final void playerInteraction(final Player player) {
		for (int j = 0; j < projectiles.size(); j++) {

			if (projectiles.get(j).intersects(player)) {

				if (projectiles.get(j).getFloatDelay() <= 0) {

					if (player.getYpos() <= projectiles.get(j).getYpos()) {
						player.setFalling(false);
						player.setDy((projectiles.get(j).getDy() - 0.1));
					} else if (player.isRight()
							|| (player.isJumping() && player.isRight())) {
						projectiles.get(j).setDx(2);
						projectiles.get(j).setFloatDelay(1000);
					} else if (player.isLeft()
							|| (player.isJumping() && player.isLeft())) {
						projectiles.get(j).setDx(-2);
						projectiles.get(j).setFloatDelay(1000);
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
			for (int j = 0; j < projectiles.size(); j++) {
				if (projectiles.get(j).getDy() == 0
						&& projectiles.get(j).intersects(enemies.get(i))) {
					removeComponent(projectiles.get(j));
					projectiles.remove(j);
					j--;
					Log.info("Player Action", "Fired bubble hit enemy");
					enemies.get(i).setCaught(true);

				}
			}
		}
	}

	/**
	 * returns the size of the list.
	 * 
	 * @return - the size of the list.
	 */
	public final int getSize() {
		return projectiles.size();
	}

}
