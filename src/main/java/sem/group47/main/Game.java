package sem.group47.main;

import java.io.File;
import java.io.PrintStream;

import javax.swing.JFrame;

/**
 * The Class Game sets up the JFrame window, in which the game will be played.
 * The class JFrame is an extended version of java.awt.Frame that adds support
 * for the JFC/Swing component architecture.
 */
public class Game {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {

		JFrame window = new JFrame("Bubble Bobble");

		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setResizable(false);
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		Log.debug("Example", "This text is logged");
		Log.setMinimumPriorityLevel(Log.Level.INFO);
		Log.debug("Example", "This text is not logged");
		Log.warning("Example", "This text is also logged");
		try {
			Log.setPrintStream(new PrintStream(new File("log.txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.info("Example", "This text has been written to a file");

	}

	/**
	 * prevents calls from subclass.
	 */
	protected Game() {
		throw new UnsupportedOperationException();
	}

}
