package org.dnu.samoylov.mvc;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ScrollPane allLabelList;
    public ListView<Text> selectedLabelsList;
    public ListView rulesList;
    public ListView logList;
    public Button resetBtn;
    public Button stepBtn;
    public Button quickResult;

    FXMLLoader fxmlLoader = new FXMLLoader();

    MainPresenter presenter = new MainPresenter();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


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
}
