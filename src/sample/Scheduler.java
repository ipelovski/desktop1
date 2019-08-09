package sample;

import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private Settings settings;
    private State state;
    private Controller controller;

    public Scheduler(Settings settings, State state, Controller controller) {
        this.settings = settings;
        this.state = state;
        this.controller = controller;
    }

    public void start() {
        LocalTime nextTime = settings.getTimeRanges().get(0).getStart();
        URI soundURI = getRandomSound();
        state.setNext(new State.SoundPlaying(soundURI, nextTime));

        LocalTime localTime = state.getNext().getStartAt();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long seconds = ChronoUnit.SECONDS.between(localTime, LocalTime.now());
        scheduler.schedule(this::playNext, seconds, TimeUnit.SECONDS);
    }

    private URI getRandomSound() {
        String soundsFolderPath = settings.getSoundsFolder();
        File soundsFolder = new File(soundsFolderPath);
        if (soundsFolder.isDirectory()) {
            String[] soundFileNames = soundsFolder.list((dir, name) -> {
                File file = new File(dir, name);
                return file.isFile() && file.getName().endsWith(".mp3");
            });
            if (soundFileNames != null) {
                Random random = new Random();
                int randomFile = random.nextInt(soundFileNames.length);
                return Paths.get(
                    soundsFolder.getAbsolutePath(), soundFileNames[randomFile]
                ).toUri();
            }
        }
        return null;
    }

    private void playNext() {
        state.setCurrent(state.getNext());
        LocalTime nextTime = LocalTime.now().plusMinutes(10);
        LocalTime tillTime = settings.getTimeRanges().get(0).getStart()
            .plus(settings.getTimeRanges().get(0).getDuration());
        if (nextTime.isBefore(tillTime)) {
            state.setNext(
                new State.SoundPlaying(
                    getRandomSound(), nextTime));
            controller.play(state.getCurrent().getSoundFile());
        }
    }
}
