package org.dnu.samoylov.storage.input;

import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.storage.Storage;

public class PsLabelStorage extends Storage<PsLabel> {
    public static PsLabelStorage INSTANCE = new PsLabelStorage();
    public static PsLabelStorage getInstance() {
        return INSTANCE;
    }
    private PsLabelStorage() {
    }
}
