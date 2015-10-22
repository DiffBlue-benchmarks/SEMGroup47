package sem.group47.entity.enemies.property;

public class BonusPointsProperty extends EnemyPropertyDecorator {

	public BonusPointsProperty(EnemyProperty newProperty) {
		super(newProperty);
	}

	/** Gets the point value.
	 * @return
	 *  how much points the enemy is worth
	 **/
	public int getPoints() {
		return tempProperty.getPoints() + 15;
	}
}
