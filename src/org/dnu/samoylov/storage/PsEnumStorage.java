package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.PsEnumLabel;

public class PsEnumStorage extends Storage<PsEnumLabel> {
    public static PsEnumStorage INSTANCE = new PsEnumStorage();
    public static PsEnumStorage getInstance() {
        return INSTANCE;
    }
    private PsEnumStorage() {
    }
}
