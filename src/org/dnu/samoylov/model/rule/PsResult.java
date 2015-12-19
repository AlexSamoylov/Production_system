package org.dnu.samoylov.model.rule;

public class PsResult {
    private final String message;

    public PsResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static PsResult create(String message) {
        return new PsResult(message);
    }
}
