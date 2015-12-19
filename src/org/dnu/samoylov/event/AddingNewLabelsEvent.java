package org.dnu.samoylov.event;

import org.dnu.samoylov.model.PsLabel;

import java.util.List;

public class AddingNewLabelsEvent {
    public final List<PsLabel> newLabels;

    public AddingNewLabelsEvent(List<PsLabel> newLabels) {
        this.newLabels = newLabels;
    }

    public static AddingNewLabelsEvent create(List<PsLabel> newLabels) {
        return new AddingNewLabelsEvent(newLabels);
    }
}