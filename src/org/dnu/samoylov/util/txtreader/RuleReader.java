package org.dnu.samoylov.util.txtreader;

import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.ClarifyingRule;
import org.dnu.samoylov.model.rule.ResultingRule;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.storage.input.RuleStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuleReader extends TxtReader {
    private static final String START_PREDICATE = "ЕСЛИ";
    private static final String IN_OUT_SEPARATOR = "ТО";
    private static final String RESULTING_MARKER = "return";
    private static final String PRIORITY_MARKER = "priority";

    @Override
    protected void handleLine(String line) {
        assert line.startsWith(START_PREDICATE) : "other line must be start with " + START_PREDICATE;

        final int indexInOutSeparator = line.indexOf(IN_OUT_SEPARATOR);

        assert indexInOutSeparator>START_PREDICATE.length() : "use " + IN_OUT_SEPARATOR;

        String inputDataStr = getInputDataString(line, indexInOutSeparator);
        String outputDataStr = getOutputDataString(line, indexInOutSeparator);

        final List<PsLabel> inputLabels = readLabel(inputDataStr);

        final int resultingMarkerIndex = outputDataStr.indexOf(RESULTING_MARKER);

        if(resultingMarkerIndex < 0) {
            final List<PsLabel> outputLabels = readLabel(outputDataStr);
            RuleStorage.getInstance().add(ClarifyingRule.create(line, inputLabels, outputLabels));
        } else {
            final int priorityMarkerIndex = outputDataStr.indexOf(PRIORITY_MARKER);
            if(priorityMarkerIndex<0) {
                final String resultRule = outputDataStr.substring(resultingMarkerIndex + RESULTING_MARKER.length());
                RuleStorage.getInstance().add(ResultingRule.create(line, inputLabels, resultRule));
            } else {
                final String resultRule = outputDataStr.substring(resultingMarkerIndex + RESULTING_MARKER.length(), priorityMarkerIndex-1);
                final String priorityString = outputDataStr.substring(priorityMarkerIndex + PRIORITY_MARKER.length()+1);
                int priority = Integer.valueOf(priorityString);
                RuleStorage.getInstance().add(ResultingRule.create(line, inputLabels, resultRule, priority));
            }

        }
    }


    private List<PsLabel> readLabel(String labelsStr) {

        final String[] labels = labelsStr.split(", ");
        List<PsLabel> psLabels = new ArrayList<>(labels.length);
        for (String neededFindLabel : labels) {
            try {
                final PsLabel foundLabel = FullLabelStorage.getInstance().getList().stream().
                        filter(psLabel -> psLabel.getName().equals(neededFindLabel)).findFirst().get();
                psLabels.add(foundLabel);
            } catch (java.util.NoSuchElementException e) {
                //throw new MissingFormatArgumentException("labels " + neededFindLabel + "not found");
                return Collections.emptyList();
            }
        }

        return psLabels;
    }

    private String getOutputDataString(String line, int indexInOutSeparator) {
        return (indexInOutSeparator + IN_OUT_SEPARATOR.length() + 1 < line.length()) ?
                line.substring(indexInOutSeparator + IN_OUT_SEPARATOR.length() + 1) : "";
    }

    private String getInputDataString(String line, int indexInOutSeparator) {
        return (START_PREDICATE.length() + 1  < indexInOutSeparator - 1)?
                line.substring(START_PREDICATE.length() + 1, indexInOutSeparator - 1) : "";
    }
}
