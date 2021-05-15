package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class mainController2 {

    public void changeScreenButton(ActionEvent event) throws IOException, GeneralSecurityException {

        // This calls one of my methods from the SheetsAPI class and makes a new line on the sheet.
        SheetsAPI.DataWriting("doneFrom", "otherFile");

        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calendarScreen.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();

    }
}
