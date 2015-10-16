package sem.group47.main;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Keeps a list of elements to draw, which may be added or removed.
 *
 * @author Karin
 *
 */
public abstract class DrawComposite implements Drawable {

	/** The list of Drawable components. */
	private ArrayList<Drawable> drawComponents;

	/**
	 * Draw all components to the screen. Must be implemented locally.
	 *
	 * @param gr
	 *            The graphics helper.
	 */
	public abstract void draw(final java.awt.Graphics2D gr);

	/**
	 * Draw all components to the screen. Call this when a subclass needs
	 * additional logic in the draw method.
	 *
	 * @param gr
	 *            The graphics helper.
	 */
	public final void drawComponents(final java.awt.Graphics2D gr) {
		if (drawComponents == null) {
			return;
		}
		Iterator<Drawable> iterator = drawComponents.iterator();
		while (iterator.hasNext()) {
			Drawable drawComponent = iterator.next();
			drawComponent.draw(gr);
		}
	}

	/**
	 * Add an item to the list.
	 *
	 * @param dr
	 *            Any class that implements Drawable.
	 */
	public final void addComponent(final Drawable dr) {
		if (drawComponents == null) {
			drawComponents = new ArrayList<Drawable>();
		}
		drawComponents.add(dr);
	}

	/**
	 * Remove an element from the list.
	 *
	 * @param dr
	 *            Any class that implements Drawable.
	 */
	public final void removeComponent(final Drawable dr) {
		if (drawComponents == null) {
			return;
		}
		drawComponents.remove(dr);
	}

	/**
	 * Clears the entire drawable list.
	 */
	public final void clearComponents() {
		if (drawComponents == null) {
			return;
		}
		drawComponents.clear();
	}

	/**
	 * Get the i'th element of the list.
	 *
	 * @param i
	 *            The index of the element.
	 * @return The drawable on index i.
	 */
	public final Drawable getChild(final int i) {
		if (drawComponents == null || drawComponents.size() < i) {
			throw new IndexOutOfBoundsException();
		} else {
			return drawComponents.get(i);
		}
	}

}
