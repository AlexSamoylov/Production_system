package org.dnu.samoylov.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    public CalculateSystemService calculateSystemService = null;

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


    @FXML
    public void reset() throws IOException {
        SelectedLabelStorage.getInstance().getList().clear();
        selectedLabelsList.getItems().clear();
        resultList.getItems().clear();
        rulesList.getItems()
                .forEach(text1 -> text1.setFill(Color.BLACK));

        allLabelList.setContent(null);
        presenter.fillAllLabelsList(allLabelList, selectedLabelsList);
        allLabelList.setVisible(true);

    }

    @FXML
    public void step() {
        allLabelList.setVisible(false);
        if (calculateSystemService==null) {
            calculateSystemService = new CalculateSystemService();
            calculateSystemService.runCalculateByStep();
        }

        calculateSystemService.goNextStep();
    }

    @FXML
    public void result() {
        allLabelList.setVisible(false);

        calculateSystemService = new CalculateSystemService();
        calculateSystemService.runQuickCalculate();
        calculateSystemService = null;
    }

    public void markSuitableRule(Rule rule) {
        rulesList.getItems()
                .filtered(text -> text.getText().equals(rule.toString()))
                .forEach(text1 -> text1.setFill(Color.GREEN));

        addToLog("испльзовано правило:" + rule.toString(), Color.GREEN);
    }

    public void addNewLabel(List<PsLabel> newLabels) {
        newLabels.stream().forEach(psLabel -> {
                    Text text = new Text(psLabel.getName());
                    text.setFill(Color.ORANGE);
                    selectedLabelsList.getItems().add(text);

                    addToLog("отыскана новая метка: " + psLabel.getName(), Color.ORANGE);
                }
        );
    }

    public void addNewResult(PsResult result) {
        Text text = new Text(result.getMessage());
        text.setFill(Color.DARKCYAN);
        resultList.getItems().add(text);

        addToLog("найден возможный результат: " + result.toString(), Color.DARKCYAN);
    }

    public void selectFinalResult(PsResult result) {
        resultList.getItems().filtered(text -> text.getText().equals(result.getMessage())).forEach(text1 -> text1.setFill(Color.BLUE));

        addToLog("выбран окончательный результат!  " + result.getMessage(), Color.BLUE);
        addToLog("---------------------------------", Color.BLACK);
        calculateSystemService = null;
    }

    public void addToLog(String message, Paint color) {
        Text text = new Text(message);
        text.setFill(color);
        logList.getItems().add(text);
    }
}
