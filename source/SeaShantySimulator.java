import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 *  Class that handles game sounds and music.
 *  @author Daumantas Balakauskas
 *  @version 1.0
 */

public class SeaShantySimulator {
    private static final float BG_MUSIC_VOLUME = 0.02f;
    private MediaPlayer mediaPlayer;

    /**
     * Initialises the media player and loads the music file for background
     * music.
     */

    public void initialize() {
        String musicFile = "source/bgMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(BG_MUSIC_VOLUME);

    }

    /**
     * Starts playing the background music.
     */

    public void play() {
        mediaPlayer.play();
    }

    /**
     * Plays a short sound
     *
     * @param path path to the mp3 file.
     * @param volume sets volume. 0.1 = 10% ect.
     */

    public void playAudioClip(String path, double volume) {
        AudioClip soundToPlay = new AudioClip(
                new File(path).toURI().toString());
        soundToPlay.setVolume(volume);
        soundToPlay.play();
    }
}

