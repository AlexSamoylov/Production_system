package org.dnu.samoylov.event;

import org.dnu.samoylov.model.rule.PsResult;

import java.util.List;

public class AddingNewResultEvents {
    public final PsResult result;

    public AddingNewResultEvents(PsResult result) {
        this.result = result;
    }

    public static AddingNewResultEvents create(PsResult result) {
        return new AddingNewResultEvents(result);
    }
}
