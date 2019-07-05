package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private Main main;
    @FXML private TextField fromHourText;
    @FXML private TextField musicFolderText;
    @FXML private Text playingText;
    @FXML private MediaView mediaView;

    Controller(Main main) {
        this.main = main;
    }

    @FXML
    protected void handleBrowse(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            musicFolderText.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    protected void start(ActionEvent event) {
        int hour = 1;
        try {
            hour = Integer.parseInt(fromHourText.getText());
        } catch (NumberFormatException e) {

        }
        Timer timer = new Timer();
        Date date = new Date(119, 6, 4, hour, 0);
        Date now = new Date();
        if (date.before(now)) {
            date = now;
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                playRandomMusic();
            }
        }, date);
    }

    private void playRandomMusic() {
        String musicFolderPath = musicFolderText.getText();
        File musicFolder = new File(musicFolderPath);
        if (musicFolder.isDirectory()) {
            String[] musicFileNames = musicFolder.list((dir, name) -> {
                File file = new File(dir, name);
                return file.isFile() && file.getName().endsWith(".mp3");
            });
            Random random = new Random();
            int randomFile = random.nextInt(musicFileNames.length);
            Media media = new Media(
                Paths.get(
                    musicFolder.getAbsolutePath(), musicFileNames[randomFile]
                ).toUri().toString());
            playingText.setText(media.getSource());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(1.0);
            mediaPlayer.play();
            mediaPlayer.setOnStopped(() -> playingText.setText(""));
            mediaView.setMediaPlayer(mediaPlayer);
        }
    }
}
