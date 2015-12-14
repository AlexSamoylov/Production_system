package org.dnu.samoylov.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dnu.samoylov.storage.FullLabelStorage;
import org.dnu.samoylov.util.txtreader.LabelFileReader;
import org.dnu.samoylov.util.txtreader.RuleReader;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("../fxml/main.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();

        LabelFileReader labelFileReader = new LabelFileReader();
        RuleReader ruleReader = new RuleReader();

        labelFileReader.readFile(getClass().getResource("../label.txt").getPath());
        FullLabelStorage.getInstance().initFromLabelAndEnum();
        ruleReader.readFile(getClass().getResource("../rule.txt").getPath());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
