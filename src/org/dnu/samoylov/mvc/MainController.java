package org.dnu.samoylov.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.PsResult;
import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.service.JavaFxBus;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ScrollPane allLabelList;

    public ListView<Text> selectedLabelsList;
    public ListView rulesList;
    public ListView resultList;

    public ListView logList;

    public Button resetBtn;
    public Button stepBtn;
    public Button quickResult;

    MainPresenter presenter = new MainPresenter();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxBus.getInstance().register(this);

        try {
            presenter.fillStorage();
            presenter.fillAllLabelsList(allLabelList, selectedLabelsList);
            presenter.fillRuleList(rulesList);

        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
        }
    }


    public void reset(ActionEvent actionEvent) {

    }

    public void step(ActionEvent actionEvent) {

    }

    public void result(ActionEvent actionEvent) {

    }

    public void markSuitableRule(Rule rule) {

    }

    public void addNewLabel(List<PsLabel> newLabels) {

    }

    public void addNewResult(List<PsResult> results) {

    }
}
