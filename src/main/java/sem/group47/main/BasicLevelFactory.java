package sem.group47.main;

import java.util.ArrayList;

import sem.group47.entity.Player;
import sem.group47.entity.PlayerSave;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.GroundEnemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.enemies.property.BaseEnemyProperty;
import sem.group47.entity.enemies.property.BonusPointsProperty;
import sem.group47.entity.enemies.property.CanFireProperty;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.entity.enemies.property.FasterProperty;
import sem.group47.entity.enemies.property.SpriteProperty;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.tilemap.TileMap;

/**
 * The basicLevelFactory initialises a standard level.
 * @author Karin
 *
 */
public class BasicLevelFactory implements LevelFactory {

	/** The tileMap being used. */
	private TileMap tileMap;
	private EnemyProperty[] enemyProperties;

	public BasicLevelFactory() {
		enemyProperties = new EnemyProperty[5];
		enemyProperties[0] = new BaseEnemyProperty();
		enemyProperties[1] = new CanFireProperty(
				new SpriteProperty(
						new BaseEnemyProperty(),
						3));
		enemyProperties[2] = new FasterProperty(
				new CanFireProperty(
						new SpriteProperty(
								new BaseEnemyProperty(),
								1)));
		enemyProperties[3] = new BonusPointsProperty(
				new FasterProperty(
						new CanFireProperty(
								new SpriteProperty(
										new BaseEnemyProperty(),
										7))));
		enemyProperties[4] = new FasterProperty(
				new FasterProperty(
						new BonusPointsProperty(
								new FasterProperty(
										new CanFireProperty(
												new SpriteProperty(
														new BaseEnemyProperty(),
														2))))));
	}

	/** method used to make the level.
	 * @param filename - the filename for the tilemap.
	 * @param multiplayer - true if there need to be two players.
	 * @return - the level which has been initialised.
	 */
	public final Level makeLevel(final String filename,
				final boolean multiplayer) {
		Level newlevel = new Level();
		loadTileMap(filename, newlevel);
		loadPlayers(newlevel, multiplayer);
		populateEnemies(newlevel);
		populatePowerups(newlevel);
		return newlevel;
	}

	/**
	 * Loads the tileMap.
	 * @param levelFileName - the filename
	 * @param level - the level to add it to.
	 */
	private void loadTileMap(final String levelFileName,
				final Level level) {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tiles/Bubble_Tile.gif");
		tileMap.loadMap("/maps/" + levelFileName);
		level.setTileMap(tileMap);
	};

	/**
	 * Adds enemies to the level.
	 * @param level - the level to add them to.
	 */
	private void populateEnemies(final Level level) {
		ArrayList<int[]> points = tileMap.getEnemyStartLocations();
		Enemy enemy;
		int j = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			switch (points.get(i)[2]) {
			case Enemy.LEVEL1_ENEMY:
				enemy =
				new GroundEnemy(tileMap,
						enemyProperties[0]);
				break;
			case Enemy.PROJECTILE_ENEMEY:
				int r = (int) Math.round(Math.random()*3);
				enemy =
				new GroundEnemy(tileMap,
								enemyProperties[1+r]);
				break;
			default:
				enemy =
				new GroundEnemy(tileMap,
						new BaseEnemyProperty());
			
			}
			enemy.setPosition((points.get(i)[0] + .5d) * 30,
					(points.get(i)[1] + 1) * 30
							- .5d * enemy.getCHeight());
			level.addEnemy(enemy);
			j = i;
		}

		Magiron aaron = new Magiron(tileMap);
		aaron.setPosition(GamePanel.WIDTH / 2, -150);
		level.addAaron(aaron);
	};

	/**
	 * Loads te players. Two if it is a multiplayer game,
	 * one otherwise.
	 * @param level - the level to add them to.
	 * @param multiplayer - true if two players are required.
	 */
	public void loadPlayers(Level level, boolean multiplayer) {
		Player player1 = new Player(tileMap);
		player1.setPosition(tileMap.getTileSize() * (2d + .5d) + 5,
				tileMap.getTileSize()
					* (tileMap.getNumRows() - 2 + .5d));
		player1.setLives(PlayerSave.getLivesP1());

		player1.setExtraLive(PlayerSave.getExtraLiveP1());
		player1.setScore(PlayerSave.getScoreP1());
		level.setPlayer1(player1);

		if (multiplayer) {
			Player player2 = new Player(tileMap);
			player2.setPosition(
					tileMap.getTileSize()
					* (tileMap.getNumCols() - 3 + .5d) - 5,
					tileMap.getTileSize()
					* (tileMap.getNumRows() - 2 + .5d));
			player2.setLives(PlayerSave.getLivesP2());
			player2.setExtraLive(PlayerSave.getExtraLiveP2());
			player2.setScore(PlayerSave.getScoreP2());
			player2.setFacingRight(false);
			level.setPlayer2(player2);
		}
	};

	/**
	 * loads the powerups.
	 * @param level - the level to add them to.
	 */
	private void populatePowerups(final Level level) {
		PickupObject po = new MovementSpeedPowerup(tileMap);
		po.setPosition(100, 100);
		level.addPickup(po);
		po = new BubbleSizePowerup(tileMap);
		po.setPosition(tileMap.getWidth() - 100, 100);
		level.addPickup(po);
		po = new BubbleSpeedPowerup(tileMap);
		po.setPosition(tileMap.getWidth() / 2, 100);
		level.addPickup(po);
	}

}
