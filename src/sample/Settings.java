package sample;

import java.time.Duration;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

public class Settings {

    private boolean playAllDay;
    private List<TimeRange> timeRanges = new LinkedList<>();
    private String soundsFolder;

    public Settings() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
//        prefs.
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public void setTimeRanges(List<TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
    }

    public boolean isPlayAllDay() {
        return playAllDay;
    }

    public void setPlayAllDay(boolean playAllDay) {
        this.playAllDay = playAllDay;
    }

    public String getSoundsFolder() {
        return soundsFolder;
    }

    public void setSoundsFolder(String soundsFolder) {
        this.soundsFolder = soundsFolder;
    }

    public static class TimeRange {
        private final LocalTime start;
        private final Duration duration;

        public TimeRange(LocalTime start, Duration duration) {
            this.start = start;
            this.duration = duration;
        }

        public LocalTime getStart() {
            return start;
        }

        public Duration getDuration() {
            return duration;
        }
    }
}
