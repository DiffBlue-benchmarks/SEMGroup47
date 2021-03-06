package sem.group47.entity.enemies.property;

/**
 * The class SpriteProperty, changes the sprite.
 *
 * @author Christian
 *
 */
public class SpriteProperty extends EnemyPropertyDecorator {

	/** The y. */
	private int y;

	/**
	 * Constructor.
	 *
	 * @param newProperty
	 *            the new property
	 * @param spriteY
	 *            y coordinate of sprite to use in monster_sprite.png
	 */
	public SpriteProperty(final EnemyProperty newProperty, final int spriteY) {
		super(newProperty);
		y = spriteY;
	}

	/**
	 * The y coordinate of the sprite to use in enemies/monster_sprite.png.
	 *
	 * @return y coord
	 */
	@Override
	public final int getSpriteSheetY() {
		return y;
	}
}
