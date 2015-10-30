package sem.group47.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.tilemap.TileMap;

/**
 * The Class WaterfallHolder, which acts as a holder for multiple Waterfall
 * objects.
 */
public class WaterfallHolder extends MapObject {

	/** The Constant LENGTH. */
	private static final int LENGTH = 15;

	/** The Waterfall object parts. */
	private ArrayList<Waterfall> parts;

	/**
	 * Constructor, initializes new ArrayList of Waterfall parts.
	 *
	 * @param tm
	 *            the tileMap.
	 * @param x
	 *            the x position.
	 * @param y
	 *            the y position.
	 */
	public WaterfallHolder(final TileMap tm, final double x, final double y) {
		super(tm);
		setPosition(x, y);
		parts = new ArrayList<Waterfall>();
		double yy = 0;
		for (int i = 0; i < LENGTH; i++) {
			Waterfall part = new Waterfall(tm);
			parts.add(part);
			part.setPosition(getx(), gety() + yy);
			yy -= 15;
		}
	}

	@Override
	public final void update() {
		for (int i = 0; i < parts.size(); i++) {
			Waterfall part = parts.get(i);
			part.update();
		}
	}

	@Override
	public final void draw(final Graphics2D gr) {
		for (int i = 0; i < parts.size(); i++) {
			parts.get(i).draw(gr);
		}
	}

	/**
	 * Player interaction.
	 *
	 * @param player
	 *            the player
	 */
	public final void playerInteraction(final Player player) {
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).playerInteraction(player)) {
				return;
			}
		}
	}
}
