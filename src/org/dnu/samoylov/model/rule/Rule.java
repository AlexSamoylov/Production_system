package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public abstract class Rule {
    private final String source;
    private final List<PsLabel> input;

    public Rule(String source, List<PsLabel> inputLabels) {
        this.source = source;
        this.input = inputLabels;
    }

    public List<PsLabel> getInput() {
        return input;
    }

    @Override
    public String toString() {
        return source;
    }
}
