package sem.group47.gamestate;

import static org.junit.Assert.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.junit.Before;
import org.junit.Test;

import sem.group47.gamestate.GameStateManager;

public class GameStateManagerTest {
	
	private Graphics2D g2d;
	
	public GameStateManager gsm;
	
	@Before
	public void setUp() {
		BufferedImage image = new BufferedImage(810, 600, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D) image.getGraphics();
	}
	
	@Test
	public void testGameUpdate() {
		gsm = new GameStateManager();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVEL1STATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVEL1STATE);
		for(int i = 0; i < 6000; i++) {
			gsm.update();
		}
	}
	
	@Test
	public void testGameUpdateAndDraw() {
		gsm = new GameStateManager();
		assertEquals(gsm.getCurrentState(), GameStateManager.MENUSTATE);
		gsm.setState(GameStateManager.LEVEL1STATE);
		assertEquals(gsm.getCurrentState(), GameStateManager.LEVEL1STATE);
		for(int i = 0; i < 6000; i++) {
			gsm.update();
			gsm.draw(g2d);
		}
	}

}
