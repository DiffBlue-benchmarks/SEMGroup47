package sem.group47.entity;

import org.junit.Before;

public class WaterfallTest extends MapObjectTest {
	
	protected Waterfall waterfall;
	
	@Override
	public MapObject supplyMapObject() {
		return waterfall;
	}
	
	@Before
	public void setup() {
		waterfall = new Waterfall(tileMap);
		super.setup();
	}
	
}
