package org.dnu.samoylov.service;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import org.dnu.samoylov.event.*;
import org.dnu.samoylov.mvc.MainController;

public class JavaFxBus {
    private MainController mainController;


    public void register(MainController mainController) {
        this.mainController = mainController;
    }

    public void post(FindSuitableRuleEvent suitableRuleEvent) {
        Platform.runLater(() ->
                mainController.markSuitableRule(suitableRuleEvent.rule));
    }

    public void post(AddingNewLabelsEvent newLabelsEvent) {
        Platform.runLater(() ->
                     mainController.addNewLabel(newLabelsEvent.newLabels));
    }

    public void post(AddingNewResultEvents addingNewResultEvents) {
        Platform.runLater(() ->
                     mainController.addNewResult(addingNewResultEvents.result));
    }

    public void post(SelectFinalResultEvent selectFinalResultEvent) {
        Platform.runLater(() ->
                     mainController.selectFinalResult(selectFinalResultEvent.result));
    }

    public void post(LogEvent logEvent) {
        Platform.runLater(() ->
                     mainController.addToLog(logEvent.message, Color.GRAY));
    }



    public static JavaFxBus INSTANCE = new JavaFxBus();
    public static JavaFxBus getInstance() {
        return INSTANCE;
    }
    private JavaFxBus() {
    }
}
