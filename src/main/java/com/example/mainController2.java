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

        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/calendarScreen.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();

    }

    public void CreateAccount(ActionEvent event) throws IOException {

        Parent CreateAccountParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/CreateNewAccount.fxml")));
        Scene CreateAccountScene = new Scene(CreateAccountParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(CreateAccountScene);
        window.show();
    }

    public void GoToLoginScreen(ActionEvent event) throws IOException {
        Parent LoginParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
        Scene LoginScene = new Scene(LoginParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(LoginScene);
        window.show();
    }
}
