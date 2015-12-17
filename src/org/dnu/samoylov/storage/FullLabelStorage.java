package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.storage.input.PsEnumStorage;
import org.dnu.samoylov.storage.input.PsLabelStorage;
import org.dnu.samoylov.util.PsLabelHelper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FullLabelStorage extends Storage<PsLabel> {
    public static FullLabelStorage INSTANCE = new FullLabelStorage();
    public static FullLabelStorage getInstance() {
        return INSTANCE;
    }
    private FullLabelStorage() {
    }

    public void initFromLabelAndEnum() {
        final List<PsLabel> labelsFromEnum = PsEnumStorage.getInstance().getList().stream()
                .map(PsEnumLabel::getEquivalentLabels).flatMap(Collection::stream).collect(Collectors.toList());

        this.getList().addAll(PsLabelStorage.getInstance().getList());
        this.getList().addAll(labelsFromEnum);
    }
}
