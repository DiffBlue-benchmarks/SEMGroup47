package sem.group47.highScore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Class HighScoreManager.
 */
public class HighScoreManager {

	/** The scores. */
	private ArrayList<Score> scores;

	/** Max number of highscores you can retrieve from the file. */
	private static final int NUMBER_OF_HIGHSCORES = 10;

	/** The highScore string. */
	private String highScores = "";

	/** The output stream. */
	private ObjectOutputStream outputStream = null;

	/** The input stream. */
	private ObjectInputStream inputStream = null;

	/** The Constant HIGH_SCORE_FILE. */
	private static final String HIGH_SCORE_FILE = "highscore/highscore.dat";

	/**
	 * Instantiates a new high score manager.
	 */
	public HighScoreManager() {
		scores = new ArrayList<Score>();
	}

	/**
	 * Gets the scores.
	 *
	 * @return the scores
	 */
	public final ArrayList<Score> getScores() {
		loadScoreFile();
		sort();
		return scores;
	}

	/**
	 * Sort.
	 */
	private void sort() {
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(scores, comparator);
	}

	/**
	 * Adds the score.
	 *
	 * @param name
	 *            the name
	 * @param score
	 *            the score
	 */
	public final void addScore(final String name, final int score) {
		loadScoreFile();
		scores.add(new Score(name, score));
		updateScoreFile();
	}

	/**
	 * Load score file, reads the inputStream object and casts it to an
	 * ArrayList of Score objects.
	 */
	@SuppressWarnings("unchecked")
	public final void loadScoreFile() {

		try {
			inputStream = new ObjectInputStream(
					new FileInputStream(HIGH_SCORE_FILE));
			scores = (ArrayList<Score>) inputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Update score file, by writing the ArrayList with Score objects to the
	 * file.
	 */
	public final void updateScoreFile() {
		try {
			outputStream = new ObjectOutputStream(
					new FileOutputStream(HIGH_SCORE_FILE));
			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the highscore string.
	 *
	 * @return the highscore string
	 */
	public final String getHighscoreString() {
		getScores();
		int i = 0;
		int x = scores.size();
		if (x > NUMBER_OF_HIGHSCORES) {
			x = NUMBER_OF_HIGHSCORES;
		}
		while (i < x) {
			highScores += (i + 1) + "    " + scores.get(i).getName()
					+ "        " + scores.get(i).getScore() + "\n";
			i++;
		}
		return highScores;
	}
}
