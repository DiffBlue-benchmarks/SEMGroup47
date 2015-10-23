package sem.group47.entity.enemies.property;

public class CanFireProperty extends EnemyPropertyDecorator {

	public CanFireProperty(EnemyProperty newProperty) {
		super(newProperty);
	}

	/**
	 * Whether the enemy can fire.
	 * @return
	 * a boolean
	 */
	@Override
	public boolean canFire() {
		return true;
	}
}
