package org.dnu.samoylov.storage;

import org.dnu.samoylov.model.rule.Rule;

public class RuleStorage extends Storage<Rule> {
    public static RuleStorage INSTANCE = new RuleStorage();
    public static RuleStorage getInstance() {
        return INSTANCE;
    }
    private RuleStorage() {
    }
}