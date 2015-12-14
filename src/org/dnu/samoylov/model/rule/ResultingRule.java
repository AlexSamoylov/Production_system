package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public class ResultingRule extends Rule {
    final String message;

    public ResultingRule(List<PsLabel> inputLabels, String message) {
        super(inputLabels);
        this.message = message;
    }

    public static ResultingRule create(List<PsLabel> inputLabels, String message) {
        return new ResultingRule(inputLabels, message);
    }
}
