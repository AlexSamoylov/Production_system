package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public abstract class Rule {
    private final List<PsLabel> input;

    public Rule(List<PsLabel> inputLabels) {
        this.input = inputLabels;
    }

    public List<PsLabel> getInput() {
        return input;
    }
}
