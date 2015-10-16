package sem.group47.highScore;

import java.io.Serializable;

/**
 * The Class Score, used as an Score object type for the ArrayList in the
 * HighScoreManager class.
 *
 * @author Bas
 */
@SuppressWarnings("serial")
public class Score implements Serializable {

	/** The score. */
	private int score;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new score.
	 *
	 * @param pName
	 *            the name
	 * @param pScore
	 *            the score
	 */
	public Score(final String pName, final int pScore) {
		this.score = pScore;
		this.name = pName;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public final int getScore() {
		return score;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

}
