package sem.group47.tilemap;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import sem.group47.main.GamePanel;

/**
 * The Class TileMap. Loads the Tiles and the TileMap and draws it.
 */
public class TileMap {
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
	private ArrayList<Point> enemyStartLocations;

	/**
	 * Instantiates a new tile map.
	 *
	 * @param tileSize
	 *            the tile size
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
	 *            the s
	 */
	public final void loadTiles(final String s) {
		try {

			// get the tileset image which has 2 rows of 2 tiles of 30
			// pixels each
			tileset = ImageIO.read(getClass().getResourceAsStream(s));

			// 2 tiles across
			numTilesAcross = tileset.getWidth() / tileSize;
			numTilesLength = tileset.getHeight() / tileSize;

			// Creates a matrix from the tilesheet
			tiles = new Tile[numTilesLength][numTilesAcross];

			BufferedImage subimage;

			// first row of tiles becomes normal typed (not able to collide)
			// second row of tiles becomes semiblocked typed (able to collide on
			// top)
			// third row of tiles becomes blocked typed (able to collide)
			for (int col = 0; col < numTilesAcross; col++) {
				// gets the image based on its position inside the .gif
				subimage = tileset.getSubimage(
				  col * tileSize, 0, tileSize,
						tileSize);
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				subimage = tileset.getSubimage(
				  col * tileSize, tileSize,
						tileSize, tileSize);
    tiles[1][col] =
				  new Tile(subimage, Tile.SEMIBLOCKED);
				subimage = tileset.getSubimage(
				  col * tileSize, tileSize,
						tileSize, tileSize);
				tiles[2][col] =
				  new Tile(subimage, Tile.BLOCKED);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Load map.
	 *
	 * @param s
	 *            the s
	 */
	public final void loadMap(final String s) {
		InputStream in = null;
		try {

			// get the map layout
			in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			// first row is the num of col
			numCols = Integer.parseInt(br.readLine());
			// second row is the num of rows
			numRows = Integer.parseInt(br.readLine());

			// map matrix
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;

			// enemies
			enemyStartLocations = new ArrayList<Point>();
			
			// white spaces
			String delims = "\\s+";

			// read all lines
			for (int row = 0; row < numRows; row++) {
				String line = br.readLine();
				// use white spaces to split as tokens
				String[] tokens = null;
				if (line != null) {
					tokens = line.split(delims);
				} 
				
				// put the values in the map matrix
				for (int col = 0; col < numCols; col++) {
				 if(tokens[col].equals("e")) {
				  enemyStartLocations.add(new Point(col, row));
				  map[row][col] = 0;
				 }
				 else {
				  map[row][col] = Integer.parseInt(tokens[col]);
				 }
				}
			}
			
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * returns the enemy start locations.
	 * @return enemy start locations
	 */

	public final ArrayList<Point> getEnemyStartLocations() {
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
	 *            the row
	 * @param col
	 *            the col
	 * @return the type
	 */
	public final int getType(final int row, final int col) {
		// returns the value inside the multidimensional array e.g. tile 2

		if (row >= this.getNumRows() || row < 0 || col >= this.getNumCols() || col < 0) {
			return Tile.NORMAL;
		}
		int rc = map[row][col];

		// 2 / 2 = 1
		int r = rc / numTilesAcross;

		// 2 % 2 = 0
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}

	/**
	 * Draws for every column in every row the tile.
	 *
	 * @param g
	 *            the g
	 */
	public final void draw(final Graphics2D g) {

		for (int row = 0; row < numRowsToDraw; row++) {

			// all rows are drawn
			if (row >= numRows)
				break;

			for (int col = 0; col < numColsToDraw; col++) {

				// all columns are drawn
				if (col >= numCols)
					break;

				// don't bother drawing it, cause its transparent (first block
				// in .gif is a transparent image)
				if (map[row][col] == 0)
					continue;

				// translate the tile at the map coordinate into a coordinate in
				// the tiles coordinate
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

	public final int[][] getMap() {
		return map.clone();
	}

	public final Tile[][] getTiles() {
		return tiles.clone();
	}

}
