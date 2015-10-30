package sem.group47.audio;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * AudioPlayer, utilityClass for playing audio in the game.
 *
 * @author Bas
 */
public final class AudioPlayer {

	/**
	 * Hidden constructor.
	 */
	private AudioPlayer() {
	}

	/** The audio clips. */
	private static HashMap<String, Clip> clips;

	/** The gap. */
	private static int gap;

	/** The mute. */
	private static boolean mute = false;

	/**
	 * Initializes the hashmap and gap.
	 */
	public static void init() {
		clips = new HashMap<String, Clip>();
		gap = 0;
	}

	/**
	 * Loads the music file.
	 *
	 * @param musicFileLocation
	 *            the file path of the music file
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void load(final String musicFileLocation,
			final String musicFileName) {
		try {
			if (clips.get(musicFileName) != null) {
				return;
			}
			Clip clip;
			AudioInputStream ais = AudioSystem
					.getAudioInputStream(AudioPlayer.class
							.getResourceAsStream(musicFileLocation));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			DataLine.Info info = new DataLine.Info(Clip.class, decodeFormat);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(dais);
			clips.put(musicFileName, clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the overloaded play method, that will play the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void play(final String musicFileName) {
		try {
			play(musicFileName, gap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @param pGap
	 *            the gap
	 */
	public static void play(final String musicFileName, final int pGap) {
		try {
			if (mute) {
				return;
			}
			Clip c = clips.get(musicFileName);
			if (c == null) {
				return;
			}
			if (c.isRunning()) {
				c.stop();
			}
			c.setFramePosition(pGap);
			while (!c.isRunning()) {
				c.start();
			}
		} catch (Exception e) {
			// No stacktrace printing to keep tests clean
		}
	}

	/**
	 * Stops the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void stop(final String musicFileName) {
		try {
			if (clips.get(musicFileName) == null) {
				return;
			}
			if (clips.get(musicFileName).isRunning()) {
				clips.get(musicFileName).stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resumes the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void resume(final String musicFileName) {
		try {
			if (mute) {
				return;
			}
			if (clips.get(musicFileName).isRunning()) {
				return;
			}
			clips.get(musicFileName).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resumes the loop.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void resumeLoop(final String musicFileName) {
		try {
			if (mute) {
				return;
			}
			if (clips.get(musicFileName).isRunning()) {
				return;
			}
			loop(musicFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the overloaded loop method that will loop the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void loop(final String musicFileName) {
		try {
			loop(musicFileName, gap, gap, clips.get(musicFileName)
					.getFrameLength() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the overloaded loop method that will loop the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @param frame
	 *            the startframe of the loop
	 */
	public static void loop(final String musicFileName, final int frame) {
		try {
			loop(musicFileName, frame, gap, clips.get(musicFileName)
					.getFrameLength() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Calls the overloaded loop method that will loop the music file.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @param start
	 *            the startpoint of the loop
	 * @param end
	 *            the endpoint of the loop
	 */
	public static void loop(final String musicFileName, final int start,
			final int end) {
		try {
			loop(musicFileName, gap, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loop.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @param frame
	 *            the frame
	 * @param start
	 *            the startpoint of the loop
	 * @param end
	 *            the endpoint of the loop
	 */
	public static void loop(final String musicFileName, final int frame,
			final int start, final int end) {
		try {
			stop(musicFileName);
			if (mute) {
				return;
			}
			clips.get(musicFileName).setLoopPoints(start, end);
			clips.get(musicFileName).setFramePosition(frame);
			clips.get(musicFileName).loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets the music frame position.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @param frame
	 *            the startframe of the loop
	 */
	public static void setPosition(final String musicFileName, final int frame) {
		try {
			clips.get(musicFileName).setFramePosition(frame);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the music frames.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @return the frames
	 */
	public static int getFrames(final String musicFileName) {
		try {
			return clips.get(musicFileName).getFrameLength();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Gets the position of the musicframe.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 * @return the position
	 */
	public static int getPosition(final String musicFileName) {
		try {
			return clips.get(musicFileName).getFramePosition();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Closes all music clips.
	 *
	 * @param musicFileName
	 *            the name of the music file
	 */
	public static void close(final String musicFileName) {
		try {
			stop(musicFileName);
			clips.get(musicFileName).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops all music clips.
	 */
	public static void stopAll() {
		try {
			Iterator<Entry<String, Clip>> it = clips.entrySet().iterator();
			while (it.hasNext()) {
				Clip clip = it.next().getValue();
				if (clip != null && clip.isRunning()) {
					clip.stop();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the audio is muted.
	 *
	 * @return true, if is muted
	 */
	public static boolean isMute() {
		return mute;
	}

	/**
	 * Sets the audio to muted.
	 *
	 * @param pMute
	 *            the new muted value
	 */
	public static void setMute(final boolean pMute) {
		mute = pMute;
	}

}
