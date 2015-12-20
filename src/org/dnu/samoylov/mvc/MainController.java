package org.dnu.samoylov.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.PsResult;
import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.service.CalculateSystemService;
import org.dnu.samoylov.service.JavaFxBus;
import org.dnu.samoylov.storage.SelectedLabelStorage;
import org.dnu.samoylov.util.factory.SelectingViewBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ScrollPane allLabelList;

    public ListView<Text> selectedLabelsList;
    public ListView<Text> rulesList;
    public ListView<Text> resultList;

    public ListView<Text> logList;

    public Button resetBtn;
    public Button stepBtn;
    public Button quickResult;

    MainPresenter presenter = new MainPresenter();
    public CalculateSystemService calculateSystemService = new CalculateSystemService();

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


    public void reset(ActionEvent actionEvent) throws IOException {
        SelectedLabelStorage.getInstance().getList().clear();
        selectedLabelsList.getItems().clear();
        resultList.getItems().clear();
        rulesList.getItems()
                .forEach(text1 -> text1.setFill(Color.BLACK));

        allLabelList.setContent(null);
        presenter.fillAllLabelsList(allLabelList, selectedLabelsList);
        allLabelList.setVisible(true);

    }

    public void step(ActionEvent actionEvent) {
        allLabelList.setVisible(false);
    }

    public void result(ActionEvent actionEvent) {
        allLabelList.setVisible(false);

        calculateSystemService = new CalculateSystemService();
        calculateSystemService.process();
    }

    public void markSuitableRule(Rule rule) {
        rulesList.getItems()
                .filtered(text -> text.getText().equals(rule.toString()))
                .forEach(text1 -> text1.setFill(Color.GREEN));
    }

    public void addNewLabel(List<PsLabel> newLabels) {
        newLabels.stream().forEach(psLabel -> {
                    Text text = new Text(psLabel.getName());
                    selectedLabelsList.getItems().add(text);
                }
        );
    }

    public void addNewResult(PsResult result) {
        Text text = new Text(result.getMessage());
        resultList.getItems().add(text);
    }

    public void selectFinalResult(PsResult result) {
        resultList.getItems().filtered(text -> text.getText().equals(result.getMessage())).forEach(text1 -> text1.setFill(Color.BLUE));
    }
}
