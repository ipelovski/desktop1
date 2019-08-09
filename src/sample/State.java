package sample;

import java.net.URI;
import java.time.LocalTime;

public class State {

    private SoundPlaying current;
    private SoundPlaying next;

    public State() {

    }

    public SoundPlaying getCurrent() {
        return current;
    }

    public void setCurrent(SoundPlaying current) {
        this.current = current;
    }

    public SoundPlaying getNext() {
        return next;
    }

    public void setNext(SoundPlaying next) {
        this.next = next;
    }

    public static class SoundPlaying {
        private final URI soundFile;
        private LocalTime startAt;

        public SoundPlaying(URI soundFile, LocalTime startAt) {
            this.soundFile = soundFile;
            this.startAt = startAt;
        }

        public URI getSoundFile() {
            return soundFile;
        }

        public LocalTime getStartAt() {
            return startAt;
        }

        public void setStartAt(LocalTime startAt) {
            this.startAt = startAt;
        }
    }
}
