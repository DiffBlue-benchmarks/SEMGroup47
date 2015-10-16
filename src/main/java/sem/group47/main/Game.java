package sem.group47.main;

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

		Log.setLog();

	}

	/**
	 * prevents calls from subclass.
	 */
	protected Game() {
		throw new UnsupportedOperationException();
	}

}
