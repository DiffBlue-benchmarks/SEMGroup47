package sem.group47.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// TODO: Auto-generated Javadoc
/**
 * AudioPlayer.
 * 
 * @author Bas
 *
 */
public class AudioPlayer {

	/** The clip. */
	private Clip clip;

	/**
	 * AudioPlayer constructor.
	 * 
	 * @param s
	 *            audiofile.
	 */
	public AudioPlayer(final String s) {

		try {

			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass()
					.getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(
					decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Play the clip.
	 */
	public final void play() {
		if (clip == null) {
			return;
		}
		stop();
		clip.setFramePosition(0);
		clip.start();
	}

	/**
	 * Stop the clip.
	 */
	public final void stop() {
		if (clip.isRunning()) {
			clip.stop();
		}
	}

	/**
	 * Close the clip.
	 */
	public final void close() {
		stop();
		clip.close();
	}
}
