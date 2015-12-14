package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public class ClarifyingRule extends Rule {
    final List<PsLabel> out;

    public ClarifyingRule(List<PsLabel> inputLabels, List<PsLabel> outputLabels) {
        super(inputLabels);
        this.out = outputLabels;
    }

    public List<PsLabel> getOut() {
        return out;
    }

    public static ClarifyingRule create(List<PsLabel> inputLabels, List<PsLabel> outputLabels) {
        return new ClarifyingRule(inputLabels, outputLabels);
    }
}
