package sem.group47.entity.enemies.property;

/**
 * The Class BonusPointsProperty.
 */
public class BonusPointsProperty extends EnemyPropertyDecorator {

	/**
	 * Instantiates a new bonus points property.
	 *
	 * @param newProperty
	 *            the new property
	 */
	public BonusPointsProperty(final EnemyProperty newProperty) {
		super(newProperty);
	}

	@Override
	public final int getPoints() {
		return tempProperty.getPoints() + 15;
	}
}
