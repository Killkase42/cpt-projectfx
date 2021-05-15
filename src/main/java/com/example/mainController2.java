package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class mainController2 {
    
    public void changeScreenButton(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calendarScreen.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();

    }

    public void CreateAccountScreen(ActionEvent event) throws IOException {
        Parent CreateAccountParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CreateNewAccount.fxml")));
        Scene CreateAccountScene = new Scene(CreateAccountParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(CreateAccountScene);
        window.show();
    }
}
