package org.dnu.samoylov.util;

import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.model.PsLabel;

import java.util.ArrayList;
import java.util.List;

public class PsLabelHelper {
    public static PsLabelHelper INSTANCE = new PsLabelHelper();
    public static PsLabelHelper getInstance() {
        return INSTANCE;
    }
    private PsLabelHelper() {
    }

    public List<PsLabel> createLabelsFromEnum(PsEnumLabel enumLabel) {
        final List<String> names = enumLabel.getNames();
        final String prefix = enumLabel.getPrefix();

        ArrayList<PsLabel> labels = new ArrayList<>(names.size());
        for (String name : names) {
            final PsLabel newLabel = createLabel(prefix, name);
            labels.add(newLabel);
        }
        return labels;
    }

    private PsLabel createLabel(String prefix, String name) {
        return new PsLabel(prefix + ":" + name);
    }
}
