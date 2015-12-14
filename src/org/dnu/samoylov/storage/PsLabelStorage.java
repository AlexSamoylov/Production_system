package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.PsLabel;

public class PsLabelStorage extends Storage<PsLabel> {
    public static PsLabelStorage INSTANCE = new PsLabelStorage();
    public static PsLabelStorage getInstance() {
        return INSTANCE;
    }
    private PsLabelStorage() {
    }
}
