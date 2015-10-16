package sem.group47.highScore;

import java.util.Comparator;

/**
 * The Class ScoreComparator, which tells Java how to compare two objects of the
 * type score.
 * 
 * @author Bas
 */
public class ScoreComparator implements Comparator<Score> {

	/**
	 * Compare method for comparing score1 with score2.
	 */
	public int compare(Score score1, Score score2) {
		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		if (sc1 > sc2) {
			return -1;
		} else if (sc1 < sc2) {
			return 1;
		} else {
			return 0;
		}
	}

}
