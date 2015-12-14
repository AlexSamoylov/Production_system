package org.dnu.samoylov.model;

public class PsLabel {
    private String name;

    public PsLabel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public static PsLabel create(String name) {
        return new PsLabel(name);
    }
}
