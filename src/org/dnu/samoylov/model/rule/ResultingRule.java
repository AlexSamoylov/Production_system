package org.dnu.samoylov.model.rule;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public class ResultingRule extends Rule {
    final PsResult result;

    public ResultingRule(String source, List<PsLabel> inputLabels, String message) {
        super(source, inputLabels);
        this.result = PsResult.create(message);
    }

    public static ResultingRule create(String source, List<PsLabel> inputLabels, String message) {
        return new ResultingRule(source, inputLabels, message);
    }

    public PsResult getResult() {
        return result;
    }
}
