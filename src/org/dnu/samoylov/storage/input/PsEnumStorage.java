package org.dnu.samoylov.storage.input;

import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.storage.Storage;

public class PsEnumStorage extends Storage<PsEnumLabel> {
    public static PsEnumStorage INSTANCE = new PsEnumStorage();
    public static PsEnumStorage getInstance() {
        return INSTANCE;
    }
    private PsEnumStorage() {
    }
}
