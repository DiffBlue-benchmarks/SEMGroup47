package sem.group47.main;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class DrawComponent {
		
	protected ArrayList<Drawable> drawComponents;
	
	public void draw(final java.awt.Graphics2D gr) {
		drawComponents(gr);
	}
	
	public void drawComponents(final java.awt.Graphics2D gr) {
		Iterator<Drawable> iterator = drawComponents.iterator();
		while (iterator.hasNext()) {
			Drawable drawComponent = iterator.next();
			drawComponent.draw(gr); 
		}
	}
	
	public void addComponent(Drawable dr) {
		drawComponents.add(dr);
	}
	
	public void removeComponent(Drawable dr) {
		drawComponents.remove(dr);
	}
	
	public Drawable getChild(int i) {
		if (drawComponents.size() >i) {
			return drawComponents.get(i);
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
		

}
