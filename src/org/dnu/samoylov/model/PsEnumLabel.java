package org.dnu.samoylov.model;

import java.util.Arrays;
import java.util.List;

public class PsEnumLabel {
    private String prefix;
    private final List<String> names;


    public PsEnumLabel(String prefix, String... names) {
        this.names = Arrays.asList(names);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<String> getNames() {
        return names;
    }

    public static PsEnumLabel create(String prefix, String... names) {
        return new PsEnumLabel(prefix, names);
    }
}
