package org.dnu.samoylov.storage.input;

import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.storage.Storage;

public class RuleStorage extends Storage<Rule> {
    public static RuleStorage INSTANCE = new RuleStorage();
    public static RuleStorage getInstance() {
        return INSTANCE;
    }
    private RuleStorage() {
    }
}