package sem.group47.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.tilemap.TileMap;

public class WaterfallHolder extends MapObject {

	private static final int LENGTH = 15; 
	ArrayList<Waterfall> parts;
	/**
	 * Constructor
	 * @param tm
	 */
	public WaterfallHolder(TileMap tm, double x, double y) {
		super(tm);
		setPosition(x, y);
		parts = new ArrayList<Waterfall>();
		double yy = 0;
		for(int i = 0; i < LENGTH; i++) {
			Waterfall part = new Waterfall(tm);
			parts.add(part);
			part.setPosition(getx(), gety() + yy);
			yy -= 15;
		}
	}

	@Override
	public void update() {
		for(int i = 0; i < parts.size(); i++) {
			Waterfall part = parts.get(i);
			part.update();
		}
	}
	
	@Override
	public void draw(Graphics2D gr) {
		for (int i = 0; i < parts.size(); i++) {
			parts.get(i).draw(gr);
		}
	}
	
	/**
	 * Lets the player interact with the waterfall.
	 * 
	 * @param player
	 *            .
	 */
	public final void playerInteraction(final Player player) {
		for (int i = 0; i < parts.size(); i++) {
			if (parts.get(i).playerInteraction(player))
				return;
		}
	}
}
