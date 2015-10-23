package sem.group47.entity.enemies.property;

public class CanFireProperty extends EnemyPropertyDecorator {

	public CanFireProperty(EnemyProperty newProperty) {
		super(newProperty);
	}
	
	/**
	 * Wether the enemy can fire.
	 * @return
	 * a boolean
	 */
	public boolean canFire() {
		return true;
	}
}
