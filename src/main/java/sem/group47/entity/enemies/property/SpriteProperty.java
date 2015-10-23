package sem.group47.entity.enemies.property;

/**
 * changes the sprite.
 *
 * @author Christian
 *
 */
public class SpriteProperty extends EnemyPropertyDecorator {
	private int y;

	/**
	 * Constructor.
	 *
	 * @param spriteY
	 *            y coordinate of sprite to use in monster_sprite.png
	 */
	public SpriteProperty(EnemyProperty newProperty, int spriteY) {
		super(newProperty);
		y = spriteY;
	}

	/**
	 * The y coordinate of the sprite to use in enemies/monster_sprite.png.
	 *
	 * @return y coord
	 */
	@Override
	public int getSpriteSheetY() {
		return y;
	}
}
