package sem.group47.main;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	
	static int currentLogID = 0;
	
	/**
	 * Enumeration of the logging levels.
	 */
	public enum Level {
		DEBUG("Debug", 0), INFO("Info", 1), WARNING("Warning", 2), ERROR("Error", 3);

		/**
		 * Name to be output when logged.
		 */
		private String name;
		
		/**
		 * Create a new level.
		 *
		 * @param name
		 *            name to be displayed in log messages.
		 * @param value
		 *            priority value (higher value has priority).
		 */
		private Level(String name, int priority) {
			this.name = name;
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
	static PrintStream ps = null;

	/**
	 * Log a message at the DEBUG level.
	 * 
	 * @param module
	 *            module the message originated from.
	 * @param message
	 *            message to be logged.
	 */
	public static void debug(String module, String message) {
		logMessage(Level.DEBUG, module, message);
	}

	/**
	 * Log a message at the INFO level.
	 * 
	 * @param message
	 *            message to be logged.
	 */
	public static void info(String module, String message) {
		logMessage(Level.INFO, module, message);
	}

	/**
	 * Log a message at the WARNING level.
	 * 
	 * @param message
	 *            message to be logged.
	 */
	public static void warning(String module, String message) {
		logMessage(Level.WARNING, module, message);
	}

	/**
	 * Log a message at the ERROR level.
	 * 
	 * @param message
	 *            message to be logged.
	 */
	public static void error(String module, String message) {
		logMessage(Level.ERROR, module, message);
	}

	/**
	 * Returns the print stream.
	 * 
	 * @return the current print stream.
	 */
	public static PrintStream getPrintStream() {
		return Log.ps;
	}

	/**
	 * Set the print stream to write messages to.
	 * 
	 * @param ps
	 *            the desired print stream or null for the console.
	 */
	public static void setPrintStream(PrintStream ps) {
		Log.ps = ps;
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
	public static void logMessage(Level level, String module, String message) {
		
			// construct the message
			String logMessage = "#" + currentLogID + "\t";
			logMessage += new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\t";
			logMessage += level.getName() + "\t";
			logMessage += " (" + module + "): ";
			logMessage += message;
			
			currentLogID++;
			
			ps.println(logMessage);
			ps.flush();
			
	}
}
