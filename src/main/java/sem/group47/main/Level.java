package sem.group47.main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.audio.AudioPlayer;
import sem.group47.entity.Player;
import sem.group47.entity.PlayerSave;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.Level1Enemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.enemies.ProjectileEnemy;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.Fruit;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.tilemap.TileMap;

public class Level extends DrawComposite{

	private ArrayList<Enemy> enemies;
	private ArrayList<PickupObject> pickups;
	private Magiron aaron;
	private TileMap tileMap;
	private Player player1;
	private boolean multiplayer;
	private Player player2;
	private int levelStepCount;
	/** Time in seconds before Aaron appears. **/
	private static int AARON_APPEAR_DELAY = 45;
	
	public Level() {
		enemies = new ArrayList<Enemy>();
		pickups = new ArrayList<PickupObject>();
		levelStepCount = 0;
	}
	
	
	public void addEnemy(Enemy newEnemy) {
		enemies.add(newEnemy);
		addComponent(newEnemy);
	}
	
	public void addAaron(Magiron newEnemy) {
		aaron = newEnemy;
		addComponent(aaron);
	}
	
	public void addPickup(PickupObject pu) {
		pickups.add(pu);
		addComponent(pu);
	}
	
	
	
	public void update() {
		if (player1.getLives() > 0) {
			player1.update();
			directEnemyCollision(player1);
			player1.indirectEnemyCollision(enemies);
		} else {
			removeComponent(player1);
		}
		if (multiplayer) {
			if (player2.getLives() > 0) {
				player2.update();
				directEnemyCollision(player2);
				player2.indirectEnemyCollision(enemies);
			} else {
				removeComponent(player2);
			}
		}

		if (levelStepCount == GamePanel.FPS * AARON_APPEAR_DELAY) {
			targetAaron();
		}
		aaron.update();
		levelStepCount++;

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
			if (enemies.get(i).projectileCollision(player1)) {
				player1.kill();
			}
			if (multiplayer && enemies.get(i)
					.projectileCollision(player2)) {
				player2.kill();
			}
		}

		for (int i = 0; i < pickups.size(); i++) {
			if (pickups.get(i).checkCollision(player1) || (multiplayer
					&& pickups.get(i).checkCollision(player2))) {
				AudioPlayer.play("extraLife");
				removeComponent(pickups.get(i));
				pickups.remove(i);

				i--;
			} else {
				pickups.get(i).update();
			}
		}
	}
	
	/**
	 * checks what happens when the player directly collides with an enemy.
	 *
	 * @param player
	 *            the Player object to check collisions with
	 */
	public final void directEnemyCollision(final Player player) {
		if (player.intersects(aaron)) {
			player.kill();
			targetAaron();
		}

		for (int i = 0; i < enemies.size(); i++) {

			if (player.intersects(enemies.get(i))) {
				if (enemies.get(i).isCaught()) {
					Fruit fr = new Fruit(tileMap);

					if (enemies.get(i).getXpos() > 400) {
						fr.setPosition(enemies.get(i).getXpos() - 100,
								enemies.get(i).getYpos());
					} else {
						fr.setPosition(enemies.get(i).getXpos() + 100,
								enemies.get(i).getYpos());
					}
					pickups.add(fr);
					addComponent(fr);

					player.setScore(enemies.get(i).getScorePoints());
					removeComponent(enemies.get(i));
					enemies.remove(i);

					Log.info("Player Action",
							"Player collision with Caught Enemy");

				} else if (player.getLives() > 1) {
					player.hit(1);
					Log.info("Player Action",
							"Player collision with Enemy");

				} else {
					AudioPlayer.play("crash");
					player.hit(1);
					Log.info("Player Action",
							"Player collision with Enemy");
				}
			}
		}

	}
	
	/**
	 * Target aaron.
	 */
	private void targetAaron() {
		if (multiplayer) {
			if (player1.getLives() > 0) {
				if (Math.random() > .5d || player2.getLives() <= 0) {
					aaron.setTarget(player1);
				} else {
					aaron.setTarget(player2);
				}
			} else {
				aaron.setTarget(player2);
			}
		} else {
			aaron.setTarget(player1);
		}
	}
	
	/**
	 * checks if the player is dead.
	 */
	public boolean hasLost() {
		if (player1.getLives() <= 0) {
			removeComponent(player1);
			if (!multiplayer || player2.getLives() <= 0) {

				PlayerSave.setScoreP1(player1.getScore());
				if (multiplayer) {
					PlayerSave.setScoreP2(player2.getScore());
				}

				return true;
			}
		} else if (multiplayer && player2.getLives() <= 0) {
			removeComponent(player2);
		}
		return false;
	}

	public boolean hasWon() {
		if (enemies.size() == 0) {
			removeComponent(player1);
			if (multiplayer) {
				removeComponent(player2);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void draw(final Graphics2D gr) {
		drawComponents(gr);
	}

	public Player getPlayer1() {
		return player1;
	}
	
	public void setPlayer1(Player player) {
		if (player1 != null) {
			removeComponent(player1);
		}
		player1 = player;
		addComponent(player);
	}
	
	public void setPlayer2(Player player) {
		if (player2 != null) {
			removeComponent(player1);
		}
		player2 = player;
		addComponent(player);
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public TileMap getTileMap() {
		return tileMap;
	}
	
	public void setTileMap(TileMap tilemap) {
		if (tileMap != null) {
			removeComponent(tileMap);
		}
		tileMap = tilemap;
		addComponent(tileMap);
	}


}
