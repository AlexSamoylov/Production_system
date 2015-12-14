package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.util.PsLabelHelper;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FullLabelStorage extends Storage<PsLabel> {
    public static FullLabelStorage INSTANCE = new FullLabelStorage();
    public static FullLabelStorage getInstance() {
        return INSTANCE;
    }
    private FullLabelStorage() {
    }

    public void initFromLabelAndEnum() {
        final List<List<PsLabel>> labelsFromEnum = PsEnumStorage.getInstance().getList().stream()
                .map(enumLabel -> PsLabelHelper.getInstance().createLabelsFromEnum(enumLabel)).collect(Collectors.toList());

        this.getList().addAll(PsLabelStorage.getInstance().getList());
        for (List<PsLabel> psLabels : labelsFromEnum) {
            this.getList().addAll(psLabels);
        }
    }
}
