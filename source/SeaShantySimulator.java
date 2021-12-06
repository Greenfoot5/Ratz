import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SeaShantySimulator {
    private MediaPlayer mediaPlayer;

    public void initialize() {
        String musicFile = "resources/bgMusic.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(0.3);

    }

    public void play() {
        mediaPlayer.play();
    }

    public void playAudioClip(String path, double volume) {
        AudioClip soundToPlay = new AudioClip(
                new File(path).toURI().toString());
        soundToPlay.setVolume(volume);
        soundToPlay.play();
    }

}

