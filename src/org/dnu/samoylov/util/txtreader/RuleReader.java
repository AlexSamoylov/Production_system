package org.dnu.samoylov.util.txtreader;

import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.ClarifyingRule;
import org.dnu.samoylov.model.rule.ResultingRule;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.storage.PsLabelStorage;
import org.dnu.samoylov.storage.RuleStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

public class RuleReader extends TxtReader {
    private static final String START_PREDICATE = "ЕСЛИ";
    private static final String IN_OUT_SEPARATOR = "ТО";
    private static final String RESULTING_RULE_MARKER = "return";

    @Override
    protected void handleLine(String line) {
        assert line.startsWith(START_PREDICATE) : "other line must be start with " + START_PREDICATE;

        final int indexInOutSeparator = line.indexOf(IN_OUT_SEPARATOR);
        assert indexInOutSeparator>START_PREDICATE.length() : "use " + IN_OUT_SEPARATOR;
        String inputDataStr = line.substring(START_PREDICATE.length() + 1, indexInOutSeparator - 1);
        String outputDataStr = line.substring(indexInOutSeparator + IN_OUT_SEPARATOR.length() + 1);


        final List<PsLabel> inputLabels = readLabel(inputDataStr);

        final int resultingMarkerIndex = outputDataStr.indexOf(RESULTING_RULE_MARKER);
        if(resultingMarkerIndex < 0) {
            final List<PsLabel> outputLabels = readLabel(outputDataStr);
            RuleStorage.getInstance().add(ClarifyingRule.create(inputLabels, outputLabels));
        } else {
            final String resultRule = outputDataStr.substring(resultingMarkerIndex + RESULTING_RULE_MARKER.length());
            RuleStorage.getInstance().add(ResultingRule.create(inputLabels, resultRule));
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
                throw new MissingFormatArgumentException("labels " + neededFindLabel + "not found");
            }
        }

        return psLabels;

    }
}
