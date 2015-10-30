package sem.group47.entity;

import org.junit.Before;

public class WaterfallHolderTest extends MapObjectTest {
	
	protected WaterfallHolder waterfallholder;
	
	@Before
	public void setup() {
		waterfallholder = new WaterfallHolder(tileMap, 10, 10);
	}
	
	@Override
	public MapObject supplyMapObject() {
		return waterfallholder;
	}
	
}
