package sem.group47.main;

/**
 * The Drawable may be implemented by any class which
 * can be represented visually using a draw method.
 * @author Karin
 *
 */
public interface Drawable {

	/**
	 * Draws the object on the screen.
	 * @param gr
	 * 		The graphics helper
	 */
	void draw(final java.awt.Graphics2D gr);
}
