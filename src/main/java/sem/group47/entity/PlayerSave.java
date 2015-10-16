package sem.group47.entity;

/**
 * The Class PlayerSave.
 */
public class PlayerSave {

	/** The lives. */
	private static int livesp1 = 3;

	/** The livesp2. */
	private static int livesp2 = 3;

	/** The score. */
	private static int scorep1;

	/** The scorep2. */
	private static int scorep2;

	/** The extra live. */
	private static int extraLivep1;

	/** The extra livep2. */
	private static int extraLivep2;

	/** Whether multiplayer is enabled. **/
	private static boolean multiplayer = false;

	/**
	 * Inits the.
	 */
	public static void init() {
		livesp1 = 3;
		scorep1 = 0;
		extraLivep1 = 300;
		livesp2 = 3;
		scorep2 = 0;
		extraLivep2 = 300;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public static int getLivesP1() {
		return livesp1;
	}

	/**
	 * Sets the lives.
	 *
	 * @param pLives
	 *            the new lives
	 */
	public static void setLivesP1(final int pLives) {
		livesp1 = pLives;
	}

	/**
	 * Sets the score.
	 *
	 * @param pScore
	 *            the new score
	 */
	public static void setScoreP1(final int pScore) {
		scorep1 = pScore;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public static int getScoreP1() {
		return scorep1;
	}

	/**
	 * Sets the extra live.
	 *
	 * @param pExtraLive
	 *            the new extra live
	 */
	public static void setExtraLiveP1(final int pExtraLive) {
		extraLivep1 = pExtraLive;
	}

	/**
	 * Gets the extra live.
	 *
	 * @return the extra live
	 */
	public static int getExtraLiveP1() {
		return extraLivep1;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public static int getLivesP2() {
		return livesp2;
	}

	/**
	 * Sets the lives.
	 *
	 * @param pLives
	 *            the new lives
	 */
	public static void setLivesP2(final int pLives) {
		livesp2 = pLives;
	}

	/**
	 * Sets the score.
	 *
	 * @param pScore
	 *            the new score
	 */
	public static void setScoreP2(final int pScore) {
		scorep2 = pScore;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public static int getScoreP2() {
		return scorep2;
	}

	/**
	 * Sets the extra live.
	 *
	 * @param pExtraLive
	 *            the new extra live
	 */
	public static void setExtraLiveP2(final int pExtraLive) {
		extraLivep2 = pExtraLive;
	}

	/**
	 * Gets the extra live.
	 *
	 * @return the extra live
	 */
	public static int getExtraLiveP2() {
		return extraLivep2;
	}

	/**
	 * returns whether multiplayer mode is enabled.
	 *
	 * @return true if multiplayer is enabled
	 */
	public static boolean getMultiplayerEnabled() {
		return multiplayer;
	}

	/**
	 * sets the mutli player mode.
	 *
	 * @param b
	 *            the new multiplayer enabled
	 */
	public static void setMultiplayerEnabled(final boolean b) {
		multiplayer = b;
	}
}