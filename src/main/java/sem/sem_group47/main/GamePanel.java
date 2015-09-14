package sem.sem_group47.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import sem.sem_group47.gamestate.GameStateManager;

/**
 * The Class GamePanel, which extends the super class JPanel and implements the
 * interfaces Runnable and KeyListener. JPanel is a container inside JFrame.
 * 
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	// DIMENSIONS

	/** The Constant WIDTH. */
	public static final int WIDTH = 810;

	/** The Constant HEIGHT. */
	public static final int HEIGHT = 600;

	// GAME THREAD

	/** The thread. */
	private Thread thread;

	/** The running. */
	private boolean running;

	/** The desired Frames per seconds */
	private final static int FPS = 60;

	/** The target time/ frame period */
	private final static long FRAME_PERIOD = 1000 / FPS;

	// IMAGE

	/** The image. */
	private BufferedImage image;

	/** The g. */
	private Graphics2D g;

	/** The game state manager */
	private GameStateManager gsm;

	/**
	 * Instantiates a new game panel.
	 */
	public GamePanel() {
		// calls parent (JPanel) constructor
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		// makes sure that keyboard input is recognized without having to click
		// inside the JPanel first
		setFocusable(true);
		requestFocus();
	}

	/**
	 * Gamepanel is loaded, so start thread, runs automagically
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			// create a new thread instance, provide the task that we want to
			// run (this)
			thread = new Thread(this);
			addKeyListener(this);

			// results in call of run() method
			thread.start();
		}
	}

	/**
	 * Initializes everything separately from the constructor
	 */
	private void init() {

		// BufferedImage allows us to operate directly with image data
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();

		running = true;

		gsm = new GameStateManager();
	}

	/**
	 * Run method (Game Loop)
	 * 
	 * The game loop is the central code of the game. The initialize phase is
	 * used to do any necessary game setup and prepare the environment for the
	 * update and draw phases. Here you should create your main entities,
	 * prepare the menu, detect default hardware capabilities, and so on.
	 * 
	 * The main purpose of the update phase is to prepare all objects to be
	 * drawn, so this is where all the physics code, coordinate updates, health
	 * points changes, char upgrades, damage dealt and other similar operations
	 * belong. This is also where the input will be captured and processed.
	 * 
	 * When everything is properly updated and ready, we enter the draw phase
	 * where all this information is put on the screen. This function should
	 * contain all the code to manage and draw the levels, layers, chars, HUD
	 * and so on.
	 */
	public void run() {

		init();

		long beginTime;
		long elapsedTime;
		long sleepTime;

		// game loop
		while (running) {

			// save start time
			beginTime = System.nanoTime();

			// update the game state
			update();
			// render state to the screen
			draw();
			// draws the canvas on the panel
			drawToScreen();

			// calculate how long the cycle took
			elapsedTime = System.nanoTime() - beginTime;

			// calculate sleep time
			sleepTime = FRAME_PERIOD - elapsedTime / 1000000;

			if (sleepTime < 0) {
				sleepTime = 5;
			}

			// send the thread to sleep for a short period
			try {
				Thread.sleep(sleepTime);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * Update.
	 */
	private void update() {
		gsm.update();
	}

	/**
	 * Draw.
	 */
	private void draw() {
		gsm.draw(g);
	}

	/**
	 * Draw to screen.
	 */
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		g2.dispose();
	}

	/*
	 * keyTyped
	 */

	public void keyTyped(KeyEvent key) {
	}

	/*
	 * keyPressed
	 */
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}

	/*
	 * keyReleased
	 */
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

}
