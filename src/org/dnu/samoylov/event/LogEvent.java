package org.dnu.samoylov.event;

public class LogEvent {
    public final String message;

    public LogEvent(String message) {
        this.message = message;
    }

    public static LogEvent create(String message) {
        return new LogEvent(message);
    }
}
