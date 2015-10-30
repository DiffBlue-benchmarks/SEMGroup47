package sem.group47.entity;

import java.awt.image.BufferedImage;

/**
 * The Class Animation, which adds animations to the entities.
 */
public class Animation {

	/** Array of frames. */
	private BufferedImage[] frames;

	/** The current Frame number. */
	private int currentFrame;

	/** The startTime. */
	private long startTime;

	/** The delay period between frames. */
	private long delay;

	/** Whether the animation has played once or not. */
	private boolean playedOnce;

	/**
	 * Constructor.
	 */
	public final void animation() {
		playedOnce = false;
	}

	/**
	 * Set the frames.
	 *
	 * @param pframes
	 *            the frames
	 */
	public final void setFrames(final BufferedImage[] pframes) {
		this.frames = pframes;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}

	/**
	 * Sets the delay for the animation.
	 *
	 * @param d
	 *            the delay
	 */
	public final void setDelay(final long d) {
		this.delay = d;
	}

	/**
	 * Sets the current frame number.
	 *
	 * @param i
	 *            the frame number
	 */
	public final void setFrame(final int i) {
		this.currentFrame = i;
	}

	/**
	 * Update function for the animation.
	 */
	public final void update() {

		if (delay == -1) {
			return;
		}

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if (currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}

	}

	/**
	 * Gets the current frame.
	 *
	 * @return the current frame
	 */
	public final int getFrame() {
		return currentFrame;
	}

	/**
	 * Gets the animation frames.
	 *
	 * @return the frames of the current animation
	 */
	public final BufferedImage getImage() {
		return frames[currentFrame];
	}

	/**
	 * Gets if the animation has played once.
	 *
	 * @return the if the animation has played once
	 */
	public final boolean hasPlayedOnce() {
		return playedOnce;
	}

}
