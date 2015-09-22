package sem.group47.main;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class to log actions in a logfile.
 * @author Rogier
 *
 */
public final class Log {

	/**
	 * Constructor will never be called.
	 */
	private Log() {
	}

	/**
	 * Increased for every line in the logfile.
	 */
	private static int currentLogID = 0;

	/**
	 * Enumeration of the logging levels.
	 */
	public enum Level {
		/**
		 * Debug message.
		 */
		DEBUG("Debug", 0),
		/**
		 * Info message.
		 */
		INFO("Info", 1),
		/**
		 * Warnings.
		 */
		WARNING("Warning", 2),
		/**
		 * Error messages.
		 */
		ERROR("Error", 3);

		/**
		 * Name to be output when logged.
		 */
		private String name;

		/**
		 * Create a new level.
		 *
		 * @param nm
		 *            name to be displayed in log messages.
		 * @param priority
		 *            priority value (higher value has priority).
		 */
		private Level(final String nm, final int priority) {
			name = nm;
		}

		/**
		 * Returns the name of the level.
		 *
		 * @return the name of the level.
		 */
		public String getName() {
			return name;
		}

	}

	/**
	 * {@link java.io.PrintStream} to write messages to.
	 */
	private static PrintStream ps = null;

	/**
	 * Log a message at the DEBUG level.
	 *
	 * @param module
	 *            module the message originated from.
	 * @param message
	 *            message to be logged.
	 */
	public static void debug(final String module, final String message) {
		logMessage(Level.DEBUG, module, message);
	}

	/**
	 * Initializes the logfile.
	 */
	public static void setLog() {
		String filename = "log_";
		String datestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
			.format(new Date());
		filename += datestamp;

		File[] files;
		try {
			files = new File("logfiles/").listFiles();
			if (files != null && files.length > 10) {
				File file = new File(files[1].toString());
		    	file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Log.setPrintStream(new PrintStream(
				new File("logfiles/" + filename + ".txt"), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.info("Document name", "LOGFILE");
		Log.info("Program name & version", "Bubble Bobble V0.0.1");
		Log.info("Date & time", datestamp + "\n");
		Log.ps.println(
		"_________________________________________________________");
	}

	/**
	 * Log a message at the INFO level.
	 *
	 * @param module
	 * 			  module the message originated from.
	 * @param message
	 *            message to be logged.
	 *
	 */
	public static void info(final String module, final String message) {
		logMessage(Level.INFO, module, message);
	}

	/**
	 * Log a message at the WARNING level.
	 * @param module
	 * 			  module the message originated from.
	 * @param message
	 *            message to be logged.
	 */
	public static void warning(final String module, final String message) {
		logMessage(Level.WARNING, module, message);
	}

	/**
	 * Log a message at the ERROR level.
	 * @param module
	 * 			  module the message originated from.
	 * @param message
	 *            message to be logged.
	 */
	public static void error(final String module, final String message) {
		logMessage(Level.ERROR, module, message);
	}

	/**
	 * Returns the print stream.
	 *
	 * @return the current print stream.
	 */
	public static PrintStream getPrintStream() {
		return ps;
	}

	/**
	 * Set the print stream to write messages to.
	 *
	 * @param printstream
	 *            the desired print stream or null for the console.
	 */
	public static void setPrintStream(final PrintStream printstream) {
		ps = printstream;
	}

	/**
	 * Log a message at the desired level.
	 *
	 * @param level
	 *            priority level of message.
	 * @param module
	 *            module name.
	 * @param message
	 *            message to be logged.
	 */
	public static void logMessage(final Level level,
			final String module, final String message) {

			// construct the message
			String logMessage = "#" + currentLogID + "\t";
			logMessage +=
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()) + "\t";
			logMessage += level.getName() + "\t";
			logMessage += " (" + module + "): ";
			logMessage += message;

			currentLogID++;

			ps.println(logMessage);
			ps.flush();

	}
}
