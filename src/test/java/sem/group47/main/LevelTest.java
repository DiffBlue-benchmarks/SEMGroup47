package sem.group47.main;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import sem.group47.entity.Player;
import sem.group47.entity.WaterfallHolder;
import sem.group47.entity.enemies.Enemy;
import sem.group47.entity.enemies.Magiron;
import sem.group47.entity.pickups.PickupObject;


public class LevelTest extends DrawCompositeTest {
	
	private Level level;
	/** A graphics driver to be passed */
	private Graphics2D gr;
	
	@Override
	public DrawComposite supplyDrawComposite() {
		return new Level();
	}
	
	@Before
	public void setUpLevelTest() {
		gr = mock(Graphics2D.class);
		setUp();
		level = new Level();
	}
	
	@Test
	public void addEnemyTest() {
		Enemy enemy = mock(Enemy.class);
		level.addEnemy(enemy);
		ArrayList<Enemy> enemies = level.getEnemies();
		level.draw(gr);
		assertEquals(enemies.get(0), enemy);
		verify(enemy).draw(gr);
	}
	
	@Test
	public void addAaronTest() {
		Magiron aaron = mock(Magiron.class);
		level.addAaron(aaron);
		assertEquals(level.getMagiron(), aaron);
	}
	
	@Test
	public void addWaterfallTest() {
		WaterfallHolder waterfall = mock(WaterfallHolder.class);
		level.addWaterfall(waterfall);
		level.draw(gr);
		assertEquals(level.getWaterfall(), waterfall);
		verify(waterfall).draw(gr);
	}
	
	@Test
	public void addPickupTest() {
		PickupObject pickup = mock(PickupObject.class);
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
		Player player = mock(Player.class);
		level.setPlayer1(player);
		assertEquals(level.getPlayer1(), player);
	}
	
	@Test
	public void setPlayer2Test() {
		Player player = mock(Player.class);
		level.setPlayer2(player);
		assertEquals(level.getPlayer2(), player);
	}
	
	@Test
	public void hasLostFalseTest1Player() {
		Player player = mock(Player.class);
		level.setPlayer1(player);
		stub(player.getLives()).toReturn(1);
		assertFalse(level.hasLost());
	}
	
	@Test
	public void hasLostTrueTest1Player() {
		Player player = mock(Player.class);
		level.setPlayer1(player);
		stub(player.getLives()).toReturn(0);
		assertTrue(level.hasLost());
	}
	
}
