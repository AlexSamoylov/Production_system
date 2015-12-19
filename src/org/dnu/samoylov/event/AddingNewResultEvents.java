package org.dnu.samoylov.event;

import org.dnu.samoylov.model.rule.PsResult;

import java.util.List;

public class AddingNewResultEvents {
    public final List<PsResult> results;

    public AddingNewResultEvents(List<PsResult> results) {
        this.results = results;
    }

    public static AddingNewResultEvents create(List<PsResult> results) {
        return new AddingNewResultEvents(results);
    }
}
