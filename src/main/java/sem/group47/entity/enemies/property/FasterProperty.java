package sem.group47.entity.enemies.property;

public class FasterProperty extends EnemyPropertyDecorator {

	public FasterProperty(EnemyProperty newProperty) {
		super(newProperty);
	}

	public double getAngryMovSpeed() {
		return tempProperty.getAngryMovSpeed() + 2f;
	}

	/**
	 *
	 * @return
	 */
	public double getNormalMovSpeed() {
		return tempProperty.getNormalMovSpeed() + .4f;
	}

	/**
	 *
	 * @return
	 */
	public double getMaxMovSpeed() {
		return tempProperty.getMaxMovSpeed() + 3f;
	}

}
