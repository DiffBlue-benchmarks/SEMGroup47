package sem.group47.main;

import java.awt.Graphics2D;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * This abstract test class must be extended
 * by any test class of a DrawComposite extension.
 * Please note that supplyDrawComposite() must be 
 * implemented and should return the class under test.
 * @author Karin
 *
 */
public abstract class DrawCompositeTest {

	/** The drawables */
	private Drawable d1;
	private Drawable d2;
	/** A graphics driver to be passed */
	private Graphics2D gr;
	/** The class under test */
	private DrawComposite comp;
	
	/**
	 * Collects the class under test and
	 * mocks all required objects.
	 */
	@Before
	public void setUp() {
		comp = supplyDrawComposite();
		d1 = mock(Drawable.class);
		d2 = mock(Drawable.class);
		gr = mock(Graphics2D.class);
	}
	
	/**
	 * Should be implemented by concrete test class.
	 * @return
	 * 		An object of the class being tested.
	 */
	abstract DrawComposite supplyDrawComposite();
	
	/**
	 * Tests adding components and checks that
	 * they are being drawn.
	 */
	@Test
	public void testAddDraw() {
		comp.addComponent(d1);
		comp.addComponent(d2);
		comp.drawComponents(gr);
		verify(d1).draw(gr);
		verify(d2).draw(gr);
	}
	
	/**
	 * Tests the possibility to remove components
	 * and checks that they are not drawn.
	 */
	@Test
	public void testRemoveDraw() {
		comp.addComponent(d1);
		comp.removeComponent(d1);
		comp.drawComponents(gr);
		verify(d1, never()).draw(gr);
	}
	
	/** 
	 * Tests the ability to get a child component.
	 */
	@Test
	public void testGetChild() {
		comp.addComponent(d1);
		Drawable child = comp.getChild(0);
		assertEquals(child, d1);
	}
	
	/**
	 * Checks the result of asking for a child
	 * that does not exist.
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testGetChildWrong() {
		comp.getChild(0);
	}
	
	/**
	 * No teardown necessary for now.
	 */
	@After
	public void tearDown() {}
}
