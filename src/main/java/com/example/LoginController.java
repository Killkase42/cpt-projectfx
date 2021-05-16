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
import java.util.Objects;

public class LoginController {

    public TextField UsernameTextField;
    public TextField PasswordTextField;

    //validates login information
    public void Validate(ActionEvent event) {
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
