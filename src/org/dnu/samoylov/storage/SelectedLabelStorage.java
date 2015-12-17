package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.PsLabel;

public class SelectedLabelStorage  extends Storage<PsLabel> {
    public static SelectedLabelStorage INSTANCE = new SelectedLabelStorage();

    public static SelectedLabelStorage getInstance() {
        return INSTANCE;
    }

    private SelectedLabelStorage() {
    }
}