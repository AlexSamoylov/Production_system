package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public class ResultingRule extends Rule {
    final String message;

    public ResultingRule(String source, List<PsLabel> inputLabels, String message) {
        super(source, inputLabels);
        this.message = message;
    }

    public static ResultingRule create(String source, List<PsLabel> inputLabels, String message) {
        return new ResultingRule(source, inputLabels, message);
    }
}
