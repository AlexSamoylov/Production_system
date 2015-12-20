package org.dnu.samoylov.mvc;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.model.rule.Rule;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.storage.input.PsEnumStorage;
import org.dnu.samoylov.storage.input.PsLabelStorage;
import org.dnu.samoylov.storage.input.RuleStorage;
import org.dnu.samoylov.util.factory.SelectingViewBuilder;
import org.dnu.samoylov.util.txtreader.LabelFileReader;
import org.dnu.samoylov.util.txtreader.RuleReader;

import java.io.IOException;

public class MainPresenter {

    public void fillStorage() throws IOException {
        LabelFileReader labelFileReader = new LabelFileReader();
        RuleReader ruleReader = new RuleReader();

        labelFileReader.readFile(getClass().getResource("../label.txt").getPath());
        FullLabelStorage.getInstance().initFromLabelAndEnum();
        ruleReader.readFile(getClass().getResource("../rule.txt").getPath());
    }

    public void fillAllLabelsList(ScrollPane allLabelList, ListView<Text> selectedLabelsList) throws IOException {
        final VBox vb = new VBox();

        SelectingViewBuilder selectingViewBuilder = new SelectingViewBuilder(vb, selectedLabelsList);

        for (PsLabel label : PsLabelStorage.getInstance().getList()) {
            selectingViewBuilder.buildView(label);
        }

        for (PsEnumLabel enumLabel : PsEnumStorage.getInstance().getList()) {
            selectingViewBuilder.buildView(enumLabel);
        }

        allLabelList.setContent(vb);
    }

    public void fillRuleList(ListView<Text> rulesList) {
        for (Rule rule : RuleStorage.getInstance().getList()) {
            Text text = new Text();
            text.setText(rule.toString());
            rulesList.getItems().add(text);
        }

    }
}
