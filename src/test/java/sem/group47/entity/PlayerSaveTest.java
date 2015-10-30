package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class PlayerSaveTest.
 */
public class PlayerSaveTest extends MapObjectTest {

	/** The tile map. */
	private TileMap tileMap;

	/** The tile size. */
	private int tileSize = 30;

	/** The player. */
	private Player player1, player2;

	/** The player save state. */
	private PlayerSave playerSave;

	/** The multiplayer. */
	private boolean multiplayer;

	/**
	 * SetUp.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public final void setUp() throws IOException {

		Log.setLog();
		AudioPlayer.init();

		tileMap = new TileMap(tileSize);

		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		player1 = new Player(tileMap);
		player1.setScore(100);
		player1.setLives(3);

		player2 = new Player(tileMap);
		player2.setScore(50);
		player2.setLives(2);
		multiplayer = true;
	}

	/**
	 * Sets the lives p1 test.
	 */
	@Test
	public final void setLivesP1Test() {
		playerSave.setLivesP1(player1.getLives());
		assertEquals(playerSave.getLivesP1(), 3);
	}

	/**
	 * Sets the lives p2 test.
	 */
	@Test
	public final void setLivesP2Test() {
		playerSave.setLivesP1(player2.getLives());
		assertEquals(playerSave.getLivesP1(), 2);
	}

	/**
	 * Sets the extra lives p1 test.
	 */
	@Test
	public final void setExtraLivesP1Test() {
		playerSave.setExtraLiveP1(player1.getLives());
		assertEquals(playerSave.getLivesP1(), 3);
	}

	/**
	 * Sets the extra lives p2 test.
	 */
	@Test
	public final void setExtraLivesP2Test() {
		playerSave.setExtraLiveP2(player2.getLives());
		assertEquals(playerSave.getLivesP2(), 2);
	}

	/**
	 * Sets the score p1 test.
	 */
	@Test
	public final void setScoreP1Test() {
		playerSave.setScoreP1(player1.getScore());
		assertEquals(playerSave.getScoreP1(), 100);
	}

	/**
	 * Sets the score p2 test.
	 */
	@Test
	public final void setScoreP2Test() {
		playerSave.setScoreP2(player2.getScore());
		assertEquals(playerSave.getScoreP2(), 50);
	}

	/**
	 * Sets the multiplayer enabled test.
	 */
	@Test
	public final void setMultiplayerEnabledTest() {
		playerSave.setMultiplayerEnabled(multiplayer);
		assertTrue(playerSave.getMultiplayerEnabled());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see sem.group47.entity.MapObjectTest#supplyMapObject()
	 */
	@Override
	public MapObject supplyMapObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
