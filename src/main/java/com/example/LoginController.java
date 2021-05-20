package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class LoginController extends SheetsAPI{

    public TextField UsernameTextField;
    public TextField PasswordTextField;

    //validates login information
    public void Validate(ActionEvent event) throws IOException, GeneralSecurityException {
        //gets info from text field
        String Username = UsernameTextField.getText();
        String Password = PasswordTextField.getText();




        //if the fields are empty and tries to validate. Returns an error
        if (Username.isEmpty() || Password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fields left Empty");
            alert.showAndWait();
        } else {
            String Result = ConfirmUserCredentials(Username,Password);
            if (Result == "Account found, logging you in...") {
                Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calendarScreen.fxml")));
                Scene MainScene = new Scene(MainParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(MainScene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Credentials are Incorrect");
                alert.showAndWait();
            }

        }
    }

    //button to go back to main menu
    public void BackToMainMenu(ActionEvent event) throws IOException {

        Parent MainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene MainMenuScene = new Scene(MainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }
}
