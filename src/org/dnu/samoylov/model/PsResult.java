package org.dnu.samoylov.model;

public class PsResult {
    String text;

    public PsResult(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public static PsResult create(String text) {
        return new PsResult(text);
    }
}