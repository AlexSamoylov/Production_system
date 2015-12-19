package org.dnu.samoylov.event;

import org.dnu.samoylov.model.rule.Rule;

public class FindSuitableRuleEvent {
    public final Rule rule;

    public FindSuitableRuleEvent(Rule rule) {
        this.rule = rule;
    }

    public static FindSuitableRuleEvent create(Rule rule) {
        return new FindSuitableRuleEvent(rule);
    }
}
