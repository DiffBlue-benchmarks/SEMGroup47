package sem.group47.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import sem.group47.entity.Player;
import sem.group47.entity.WaterfallHolder;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.enemies.property.EnemyProperty;
import sem.group47.entity.pickups.PickupObject;
import sem.group47.tilemap.TileMap;

public class LevelTest extends DrawCompositeTest {
	
	private Level level;
	private TileMap tm;
	private Magiron magiron;
	private Enemy enemy;
	private Player player;
	/** A graphics driver to be passed */
	private Graphics2D gr;
	private PickupObject pickup;
	
	@Override
	public DrawComposite supplyDrawComposite() {
		return new Level();
	}
	
	@Before
	public void setUpLevelTest() {
		gr = mock(Graphics2D.class);
		tm = mock(TileMap.class);
		setUp();
		level = new Level();
		level.setTileMap(tm);
		Log.setLog();
		magiron = mock(Magiron.class);
		enemy = mock(Enemy.class);
		player = mock(Player.class);
		pickup = mock(PickupObject.class);
	}
	
	@Test
	public void addEnemyTest() {
		level.addEnemy(enemy);
		ArrayList<Enemy> enemies = level.getEnemies();
		level.draw(gr);
		assertEquals(enemies.get(0), enemy);
		verify(enemy).draw(gr);
	}
	
	@Test
	public void addAaronTest() {
		level.addMagiron(magiron);
		assertEquals(level.getMagiron(), magiron);
	}
	
	@Test
	public void addWaterfallTest() {
		WaterfallHolder waterfall = mock(WaterfallHolder.class);
		level.addWaterfall(waterfall);
		assertEquals(level.getWaterfall(), waterfall);
	}
	
	@Test
	public void addPickupTest() {
		level.addPickup(pickup);
		level.draw(gr);
		assertEquals(level.getPickups().get(0), pickup);
		verify(pickup).draw(gr);
	}
	
	@Test
	public void hasWonFalseTest() {
		level.addEnemy(mock(Enemy.class));
		assertFalse(level.hasWon());
	}
	
	@Test
	public void hasWonTrueTest() {
		level.addEnemy(mock(Enemy.class));
		level.getEnemies().remove(0);
		assertTrue(level.hasWon());
	}
	
	@Test
	public void setPlayer1Test() {
		level.setPlayer1(player);
		assertEquals(level.getPlayer1(), player);
	}
	
	@Test
	public void setPlayer2Test() {
		level.setPlayer2(player);
		assertEquals(level.getPlayer2(), player);
	}
	
	@Test
	public void hasLostFalseTest1Player() {
		level.setPlayer1(player);
		stub(player.getLives()).toReturn(1);
		assertFalse(level.hasLost());
	}
	
	@Test
	public void hasLostTrueTest1Player() {
		level.setPlayer1(player);
		stub(player.getLives()).toReturn(0);
		assertTrue(level.hasLost());
	}
	
	@Test
	public void directEnemyCollisionTest() {
		level.setPlayer1(player);
		level.addEnemy(enemy);
		level.addMagiron(magiron);
		stub(player.intersects(enemy)).toReturn(true);
		stub(enemy.isCaught()).toReturn(true);
		EnemyProperty property = mock(EnemyProperty.class);
		stub(enemy.getProperties()).toReturn(property);
		stub(property.getPoints()).toReturn(1);
		level.directEnemyCollision(player);
		verify(player).setScore(1);
	}
	
	@Test
	public void directEnemyCollisionMagironTest() {
		level.setPlayer1(player);
		level.addEnemy(enemy);
		level.addMagiron(magiron);
		stub(player.intersects(magiron)).toReturn(true);
		stub(player.intersects(enemy)).toReturn(false);
		level.directEnemyCollision(player);
		verify(player).kill();
		verify(magiron).setTarget(player);
	}
	
	@Test
	public void directEnemyCollisionHitTest() {
		level.setPlayer1(player);
		level.addEnemy(enemy);
		level.addMagiron(magiron);
		stub(player.intersects(magiron)).toReturn(false);
		stub(player.intersects(enemy)).toReturn(true);
		stub(enemy.isCaught()).toReturn(false);
		stub(player.getLives()).toReturn(2);
		level.directEnemyCollision(player);
		verify(player).hit(1);
	}
	
	@Test
	public void directEnemyCollisionHitKillTest() {
		level.setPlayer1(player);
		level.addEnemy(enemy);
		level.addMagiron(magiron);
		stub(player.intersects(magiron)).toReturn(false);
		stub(player.intersects(enemy)).toReturn(true);
		stub(enemy.isCaught()).toReturn(false);
		stub(player.getLives()).toReturn(1);
		level.directEnemyCollision(player);
		verify(player).hit(1);
	}
	
	@Test
	public void updateTest() {
		level.setPlayer1(player);
		level.addPickup(pickup);
		level.addEnemy(enemy);
		level.addMagiron(magiron);
		WaterfallHolder waterfall = mock(WaterfallHolder.class);
		level.addWaterfall(waterfall);
		stub(player.getLives()).toReturn(1);
		level.update();
		verify(player).update();
		verify(pickup).update();
		verify(enemy).update();
		verify(magiron).update();
		verify(waterfall).update();
	}
	
	@Test
	public void getTilemapTest() {
		assertEquals(level.getTileMap(), tm);
	}
	
	@Test
	public void setTileMapTest() {
		TileMap newTM = mock(TileMap.class);
		level.setTileMap(newTM);
		level.draw(gr);
		verify(tm, never()).draw(gr);
	}
	
	@Test
	public void setNewPlayer1Test() {
		Player newPlayer = mock(Player.class);
		level.setPlayer1(newPlayer);
		level.draw(gr);
		verify(player, never()).draw(gr);
	}
	
	@Test
	public void setNewPlayer2Test() {
		Player player2 = mock(Player.class);
		level.setPlayer2(player2);
		Player newPlayer = mock(Player.class);
		level.setPlayer2(newPlayer);
		level.draw(gr);
		verify(player2, never()).draw(gr);
	}
	
}
