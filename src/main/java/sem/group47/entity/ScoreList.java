package sem.group47.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import sem.group47.main.DrawComposite;

public class ScoreList extends DrawComposite {

	/** The scorelist. */
	private ArrayList<Score> scores;

	/**
	 * Constructor initializes the list.
	 */
	public ScoreList() {
		scores = new ArrayList<Score>();
	}

	/**
	 * Add a Score to the list.
	 *
	 * @param p
	 *            the Score object.
	 */
	public final void addScore(final Score s) {
		scores.add(s);
		addComponent(s);
	}

	/**
	 * Draws the scores.
	 */
	@Override
	public final void draw(final Graphics2D gr) {
		drawComponents(gr);
	}

	/**
	 * Update scorelist.
	 */
	public final void update() {
		for (int i = 0; i < scores.size(); i++) {
			if (scores.get(i).mustBeRemoved()) {
				removeComponent(scores.get(i));
				scores.remove(i);
				i--;
			}
		}
	}

}
