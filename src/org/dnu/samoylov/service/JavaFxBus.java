package org.dnu.samoylov.service;

import org.dnu.samoylov.event.AddingNewLabelsEvent;
import org.dnu.samoylov.event.AddingNewResultEvents;
import org.dnu.samoylov.event.FindSuitableRuleEvent;
import org.dnu.samoylov.mvc.MainController;

public class JavaFxBus {
    private MainController mainController;


    public void register(MainController mainController) {
        this.mainController = mainController;
    }





    public static JavaFxBus INSTANCE = new JavaFxBus();
    public static JavaFxBus getInstance() {
        return INSTANCE;
    }
    private JavaFxBus() {
    }

    public void post(FindSuitableRuleEvent suitableRuleEvent) {
        mainController.markSuitableRule(suitableRuleEvent.rule);
    }

    public void post(AddingNewLabelsEvent newLabelsEvent) {
        mainController.addNewLabel(newLabelsEvent.newLabels);
    }

    public void post(AddingNewResultEvents addingNewResultEvents) {
        mainController.addNewResult(addingNewResultEvents.results);
    }
}
