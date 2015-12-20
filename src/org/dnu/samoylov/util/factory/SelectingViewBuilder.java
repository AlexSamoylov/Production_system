package org.dnu.samoylov.util.factory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.dnu.samoylov.model.PsEnumLabel;
import org.dnu.samoylov.model.PsLabel;
import org.dnu.samoylov.storage.SelectedLabelStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SelectingViewBuilder {
    private final VBox parentView;
    private final ListView selectedLabelsList;

    public SelectingViewBuilder(VBox parentView, ListView selectedLabelsList) {
        this.parentView = parentView;
        this.selectedLabelsList = selectedLabelsList;
    }



    public void buildView(PsLabel psLabel) throws IOException {
        CheckBox labelCheckBox = new CheckBox();

        labelCheckBox.setText(psLabel.getName());
        labelCheckBox.selectedProperty().addListener((ov, old_val, new_val) -> {

            if (new_val) {
                putPsLabelToSelectedBro(psLabel);
            } else {
                popPsLabelWithSelectedBro(psLabel);
            }
        });

        addView(labelCheckBox);
    }

    public void buildView(PsEnumLabel psEnum) throws IOException {
        final VBox parent = new VBox();
        final Label nameView = new Label(psEnum.getPrefix());
        final List<RadioButton> group = createRadioButton(psEnum);

        parent.getChildren().add(nameView);
        parent.getChildren().addAll(group);

        addView(parent);
    }

    private void addView(Region node) {
        node.setPadding(new Insets(10,10,10,10));
        parentView.getChildren().add(node);
        addSeparator();
    }

    private void addSeparator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        parentView.getChildren().add(separator);
    }


    public void popPsLabelWithSelectedBro(PsLabel psLabel) {
        selectedLabelsList.getItems().remove(psLabel.getName());
        SelectedLabelStorage.getInstance().getList().remove(psLabel);
    }

    public void putPsLabelToSelectedBro(PsLabel psLabel) {
        selectedLabelsList.getItems().add(psLabel.getName());
        SelectedLabelStorage.getInstance().getList().add(psLabel);
    }

    private List<RadioButton> createRadioButton(PsEnumLabel psEnum) {
        List<RadioButton> radioButtons = new ArrayList<>(psEnum.getNames().size());
        final ToggleGroup group = new ToggleGroup();
        final List<String> names = psEnum.getNames();
        for (int i = 0; i < names.size(); i++) {
            final RadioButton rb = new RadioButton(names.get(i));
            rb.setUserData(psEnum.getEquivalentLabels().get(i));

            rb.setToggleGroup(group);
            radioButtons.add(rb);
        }

        final RadioButton emptyRb = new RadioButton("");
        emptyRb.setToggleGroup(group);
        radioButtons.add(emptyRb);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (old_toggle != null && !emptyRb.equals(old_toggle)) {
                popPsLabelWithSelectedBro((PsLabel) old_toggle.getUserData());
            }
            if (new_toggle != null && !emptyRb.equals(new_toggle)) {
                putPsLabelToSelectedBro((PsLabel) new_toggle.getUserData());
            }
        });
        return radioButtons;
    }


}
