package sem.group47.main;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Calendar;

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

		String filename = "log_";
		String datestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		filename += datestamp;
		
		File[] files;
		try {
			files = new File("logfiles/").listFiles();
			if(files.length > 10) {				
				File file = new File(files[1].toString());
	    		file.delete();		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Log.setPrintStream(new PrintStream(new File("logfiles/"+filename+".txt")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Log.info("Document name", "LOGFILE");
		Log.info("Program name & version","Bubble Bobble V0.0.1");
		Log.info("Date & time", datestamp+"\n");
		Log.ps.println("______________________________________________________________________");
		
	}

	/**
	 * prevents calls from subclass.
	 */
	protected Game() {
		throw new UnsupportedOperationException();
	}

}
