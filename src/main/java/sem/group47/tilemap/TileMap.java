package sem.group47.tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import sem.group47.entity.enemies.Enemy;
import sem.group47.main.Drawable;
import sem.group47.main.GamePanel;

/**
 * The Class TileMap. Loads the Tiles and the TileMap and draws it.
 */
public class TileMap implements Drawable {
	/** The x. */
	private double x;
	
	/** The y. */
	private double y;
	
	// MAP
	
	/** The map. */
	private int[][] map;
	
	/** The tile size. */
	private int tileSize;
	
	/** The num rows. */
	private int numRows;
	
	/** The num cols. */
	private int numCols;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;
	
	// TILESET
	
	/** The tileset. */
	private BufferedImage tileset;
	
	/** The num tiles across. */
	private int numTilesAcross;
	
	/** Length num tiles length. */
	private int numTilesLength;
	
	/** The tiles. */
	private Tile[][] tiles;
	
	// DRAWING
	
	/** The num rows to draw. */
	private int numRowsToDraw;
	
	/** The num cols to draw. */
	private int numColsToDraw;
	
	/** Enemy start Locations. **/
	private ArrayList<int[]> enemyStartLocations;
	
	/**
	 * Instantiates a new tile map.
	 * 
	 * @param tsize
	 *           the tile size
	 */
	public TileMap(final int tsize) {
		tileSize = tsize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize;
		numColsToDraw = GamePanel.WIDTH / tileSize;
	}
	
	/**
	 * Load tiles.
	 * 
	 * @param s
	 *           the s
	 */
	public final void loadTiles(final String s) {
		try {
			tileset = ImageIO.read(getClass().getResourceAsStream(s));
			
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesLength = tileset.getHeight() / tileSize;
			
			tiles = new Tile[numTilesLength][numTilesAcross];
			
			BufferedImage subimage;
			
			for (int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(col * tileSize, 0, tileSize,
						tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize,
						tileSize);
				tiles[1][col] = new Tile(subimage, Tile.SEMIBLOCKED);
				subimage = tileset.getSubimage(col * tileSize, tileSize, tileSize,
						tileSize);
				tiles[2][col] = new Tile(subimage, Tile.BLOCKED);
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Load map.
	 * 
	 * @param s
	 *           the s
	 */
	public final void loadMap(final String s) {
		InputStream in = getClass().getResourceAsStream(s);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			enemyStartLocations = new ArrayList<int[]>();
			
			String delims = "\\s+";
			
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				
				String[] tokens = null;
				if (line != null) {
					tokens = line.split(delims);
				}
				
				for (int col = 0; col < numCols; col++) {
					if (tokens[col].equals("e")) {
						enemyStartLocations.add(new int[] { col, row,
								Enemy.LEVEL1_ENEMY });
						map[row][col] = 0;
					} else if (tokens[col].equals("f")) {
						enemyStartLocations.add(new int[] { col, row,
								Enemy.PROJECTILE_ENEMEY });
						map[row][col] = 0;
					} else {
						map[row][col] = Integer.parseInt(tokens[col]);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(br);
		}
	}
	
	/**
	 * returns the enemy start locations.
	 * 
	 * @return enemy start locations
	 */
	public final ArrayList<int[]> getEnemyStartLocations() {
		return enemyStartLocations;
	}
	
	/**
	 * Gets the tile size.
	 * 
	 * @return the tile size
	 */
	public final int getTileSize() {
		return tileSize;
	}
	
	/**
	 * Gets the x.
	 * 
	 * @return the x
	 */
	public final int getx() {
		return (int) x;
	}
	
	/**
	 * Gets the y.
	 * 
	 * @return the y
	 */
	public final int gety() {
		return (int) y;
	}
	
	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}
	
	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}
	
	/**
	 * Gets the type.
	 * 
	 * @param row
	 *           the row
	 * @param col
	 *           the col
	 * @return the type
	 */
	public int getType(final int row, final int col) {
		if (row >= this.getNumRows() || row < 0 || col >= this.getNumCols()
				|| col < 0) {
			return Tile.NORMAL;
		}
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		
		return tiles[r][c].getType();
	}
	
	/**
	 * Draws for every column in every row the tile.
	 * 
	 * @param g
	 *           the g
	 */
	public final void draw(final Graphics2D g) {
		
		for (int row = 0; row < numRowsToDraw; row++) {
			if (row >= numRows) {
				break;
			}
			
			for (int col = 0; col < numColsToDraw; col++) {
				
				if (col >= numCols) {
					break;
				}
				
				if (map[row][col] == 0) {
					continue;
				}
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(tiles[r][c].getImage(), (int) x + col * tileSize,
						(int) y + row * tileSize, null);
				
			}
			
		}
		
	}
	
	/**
	 * Gets the num rows.
	 * 
	 * @return the num rows
	 */
	public final int getNumRows() {
		return numRows;
	}
	
	/**
	 * Gets the num cols.
	 * 
	 * @return the num cols
	 */
	public final int getNumCols() {
		return numCols;
	}
	
	/**
	 * Gets a copy of the map in integer array.
	 * 
	 * @return a copy of the map
	 */
	public final int[][] getMap() {
		return map.clone();
	}
	
	/**
	 * Returns a copy of the tiles array.
	 * 
	 * @return a copy of the tiles array.
	 */
	public final Tile[][] getTiles() {
		return tiles.clone();
	}
	
}
