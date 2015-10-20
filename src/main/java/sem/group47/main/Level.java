package sem.group47.main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.entity.HUD;
import sem.group47.entity.Player;
import sem.group47.entity.PlayerSave;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.Level1Enemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.enemies.ProjectileEnemy;
import sem.group47.entity.pickups.BubbleSizePowerup;
import sem.group47.entity.pickups.BubbleSpeedPowerup;
import sem.group47.entity.pickups.MovementSpeedPowerup;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.tilemap.TileMap;

public class Level extends DrawComposite{

	private int levelNumber;
	private ArrayList<Enemy> enemies;
	private ArrayList<PickupObject> pickups;
	private Magiron aaron;
	private TileMap tileMap;
	private Player player1;
	
	/** level file names. **/
	private String[] levelFileNames = new String[] {"level1.map",
			"level2.map", "level3.map", "level4.map" };
	
	public Level(int num) {
		levelNumber = num;
		enemies = new ArrayList<Enemy>();
		pickups = new ArrayList<PickupObject>();
		loadTileMap();
		loadPlayers();
		populateEnemies();
		populatePowerups();
	}
	
	private void populateEnemies() {
		ArrayList<int[]> points = tileMap.getEnemyStartLocations();
		Enemy enemy;
		int j = 0;
		for (int i = 0; i < points.size() - 1; i++) {
			switch (points.get(i)[2]) {
			case Enemy.LEVEL1_ENEMY:
				enemy = new Level1Enemy(tileMap);
				break;
			case Enemy.PROJECTILE_ENEMEY:
				enemy = new ProjectileEnemy(tileMap);
				break;
			default:
				enemy = new Level1Enemy(tileMap);
			}
			enemy.setPosition((points.get(i)[0] + .5d) * 30,
					(points.get(i)[1] + 1) * 30
							- .5d * enemy.getCHeight());
			enemies.add(enemy);
			addComponent(enemy);
			j = i;
		}

		aaron = new Magiron(tileMap);
		aaron.setPosition(GamePanel.WIDTH / 2, -150);
		addComponent(aaron);
	};
	
	private void loadTileMap() {
		tileMap = new TileMap(30);
		tileMap.loadTiles("/tiles/Bubble_Tile.gif");
		tileMap.loadMap("/maps/" + levelFileNames[levelNumber]);
	};
	
	public void loadPlayers() {
		player1 = new Player(tileMap);
		player1.setPosition(tileMap.getTileSize() * (2d + .5d) + 5,
				tileMap.getTileSize() * (tileMap.getNumRows() - 2 + .5d));
		player1.setLives(PlayerSave.getLivesP1());

		player1.setExtraLive(PlayerSave.getExtraLiveP1());
		player1.setScore(PlayerSave.getScoreP1());
		addComponent(player1);
	};
	
	/**
	 * loads the powerups.
	 */
	private void populatePowerups() {
		PickupObject po = new MovementSpeedPowerup(tileMap);
		po.setPosition(100, 100);
		pickups.add(po);
		addComponent(po);
		po = new BubbleSizePowerup(tileMap);
		po.setPosition(tileMap.getWidth() - 100, 100);
		pickups.add(po);
		addComponent(po);
		po = new BubbleSpeedPowerup(tileMap);
		po.setPosition(tileMap.getWidth() / 2, 100);
		pickups.add(po);
		addComponent(po);
	}
	
	@Override
	public void draw(final Graphics2D gr) {
		// TODO Auto-generated method stub
	}


}
