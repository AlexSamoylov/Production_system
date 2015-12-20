package org.dnu.samoylov.event;

import org.dnu.samoylov.model.rule.PsResult;

public class SelectFinalResultEvent {
    public final PsResult result;

    public SelectFinalResultEvent(PsResult result) {
        this.result = result;
    }

    public static SelectFinalResultEvent create(PsResult result) {
        return new SelectFinalResultEvent(result);
    }
}
