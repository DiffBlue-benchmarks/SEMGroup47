package sem.group47.entity.enemies.property;

/**
 * The Class CanFireProperty.
 */
public class CanFireProperty extends EnemyPropertyDecorator {

	/**
	 * Instantiates a new can fire property.
	 *
	 * @param newProperty
	 *            the new property
	 */
	public CanFireProperty(final EnemyProperty newProperty) {
		super(newProperty);
	}

	@Override
	public final boolean canFire() {
		return true;
	}
}
