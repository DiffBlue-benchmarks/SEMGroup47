package sem.group47.main;

/**
 * A factory for creating Level objects.
 */
public interface LevelFactory {

	/**
	 * Make level.
	 *
	 * @param file
	 *            the kind of level file
	 * @param multiplayer
	 *            the tells if it is a multiplayer game
	 * @return the level
	 */
	Level makeLevel(String file, boolean multiplayer);
}
