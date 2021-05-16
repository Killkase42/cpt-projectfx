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


public class CreateNewAccountController extends SheetsAPI{
    public TextField UsernameTextField;
    public TextField PasswordTextField;





    public void CreateAccount(ActionEvent event)  throws IOException, GeneralSecurityException {
        //gets info from text field
        String name = UsernameTextField.getText();
        String passw = PasswordTextField.getText();



        //if the fields are empty and tries to validate. Returns an error
        if (name.isEmpty() || passw.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fields left Empty");
            alert.showAndWait();
        } else {
            DataWriting(name, passw);
            Parent SuccessAccountParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SuccessfulCreateAccount.fxml")));
            Scene SuccessAccountScene = new Scene(SuccessAccountParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(SuccessAccountScene);
            window.show();



        }

    }


    public void GoToMainMenu(ActionEvent event) throws IOException {
        Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene MainScene = new Scene(MainParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(MainScene);
        window.show();


    }
}
