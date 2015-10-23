package sem.group47.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

import sem.group47.main.Drawable;

/**
 * The Class Score.
 */
public class Score implements Drawable  {
	
	/** The visible time. */
	private long timeVisible;
	
	/** The creation time. */
	private long timeOfBirth;

	/** The to be visualized score. */
	private int score;

	/** The x position. */
	private double x;
	
	/** The y position. */
	private double y;
	
	public Score(int points, double x, double y) {
		score = points;
		this.x = x-18;
		this.y = y-36;
		timeOfBirth = System.nanoTime();
		timeVisible = (long) 2e9;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public final long getScore() {
		return score;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public final double getx() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return y
	 */
	public final double gety() {
		return y;
	}
	
	/**
	 * If the score must disappear or not.
	 *
	 * @return if the score must disappear or not
	 */
	public boolean mustBeRemoved() {
		if (System.nanoTime() - timeOfBirth > timeVisible) {
			return true;
		}
		if (score == 0 || score > 100) {
			return true;
		}
		return false;
	}

	/**
	 * Draw the score on the screen.
	 *
	 * @param gr
	 * 			the graphics
	 */
	public void draw(Graphics2D gr) {
		gr.setFont(new Font("Arial", Font.PLAIN, 20));
		gr.getFont().deriveFont(Font.BOLD);
		gr.setColor(Color.RED);
		gr.drawString(Integer.toString(score), (int) x, (int) y);
	}


}
