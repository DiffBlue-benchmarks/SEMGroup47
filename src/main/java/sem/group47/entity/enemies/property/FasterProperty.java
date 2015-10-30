package sem.group47.entity.enemies.property;

/**
 * The Class FasterProperty.
 */
public class FasterProperty extends EnemyPropertyDecorator {

	/**
	 * Instantiates a new faster property.
	 *
	 * @param newProperty
	 *            the new property
	 */
	public FasterProperty(final EnemyProperty newProperty) {
		super(newProperty);
	}

	@Override
	public final double getAngryMovSpeed() {
		return tempProperty.getAngryMovSpeed() + 2f;
	}

	@Override
	public final double getNormalMovSpeed() {
		return tempProperty.getNormalMovSpeed() + .4f;
	}

	@Override
	public final double getMaxMovSpeed() {
		return tempProperty.getMaxMovSpeed() + 3f;
	}

}
