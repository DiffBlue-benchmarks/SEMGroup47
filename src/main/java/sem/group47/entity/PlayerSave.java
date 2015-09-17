package sem.group47.entity;

/**
 * The Class PlayerSave.
 */
public class PlayerSave {

	/** The lives. */
	private static int lives = 3;

	/** The score. */
	private static int score;

	/** The extra live. */
	private static int extraLive = 300;

	/**
	 * Inits the.
	 */
	public static void init() {
		lives = 3;
		score = 0;
		extraLive = 300;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public static int getLives() {
		return lives;
	}

	/**
	 * Sets the lives.
	 *
	 * @param pLives
	 *            the new lives
	 */
	public static void setLives(final int pLives) {
		lives = pLives;
	}

	/**
	 * Sets the score.
	 *
	 * @param pScore
	 *            the new score
	 */
	public static void setScore(final int pScore) {
		score = pScore;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * Sets the extra live.
	 *
	 * @param pExtraLive
	 *            the new extra live
	 */
	public static void setExtraLive(final int pExtraLive) {
		extraLive = pExtraLive;
	}

	/**
	 * Gets the extra live.
	 *
	 * @return the extra live
	 */
	public static int getExtraLive() {
		return extraLive;
	}

}
