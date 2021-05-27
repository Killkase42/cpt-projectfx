package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

public class LoginController extends SheetsAPI{

    //variables
    public TextField UsernameTextField;
    public TextField PasswordTextField;
    public TextField ShowPasswordTextField;
    public Button ShowPasswordButton;
    public Label ErrorMessage;
    public Label ErrorMessage1;

    static String welcome = "";
    static boolean showingPass = false;

    static String Username1;

    /*
       Pre: None
       Post: reveals the letters of the password that user entered
        */
    public void ShowPassword(ActionEvent event) {
        if (!showingPass){
            ShowPasswordTextField.setText(PasswordTextField.getText());
            PasswordTextField.setVisible(false);
            ShowPasswordTextField.setVisible(true);
            showingPass = true;
            ShowPasswordButton.setText("Hide");
        } else {
            PasswordTextField.setText(ShowPasswordTextField.getText());
            PasswordTextField.setVisible(true);
            ShowPasswordTextField.setVisible(false);
            showingPass = false;
            ShowPasswordButton.setText("Show");
        }
    }

    /*
   Pre: None
   Post: validates user login credentials
    */
    public void Validate(ActionEvent event) throws IOException, GeneralSecurityException {
        if (showingPass && !ShowPasswordTextField.getText().equals("")){
            PasswordTextField.setText(ShowPasswordTextField.getText());
        }

        //gets info from text field
        String Username = UsernameTextField.getText();
        String Password = PasswordTextField.getText();

        // Reset the visibility of the error message.
        ErrorMessage.setVisible(false);
        ErrorMessage1.setVisible(false);

        //if the fields are empty and tries to validate. Returns an error
        if (Username.isEmpty() || Password.isEmpty()) {
            ErrorMessage1.setVisible(false);
            ErrorMessage.setVisible(true);
        } else {
            String Result = ConfirmUserCredentials(Username,Password);
            //succesfully logins and sends user to calender screen
            if (Result.equals("Account found, logging you in...")) {
                Username1 = Username;
                Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/calendarScreen.fxml")));
                Scene MainScene = new Scene(MainParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(MainScene);
                window.show();
            } else {
                ErrorMessage.setVisible(false);
                ErrorMessage1.setVisible(true);
            }
        }
    }

    /*
   Pre: None
   Post: brings user to screen to create account
    */
    public void ToCreateAccount(ActionEvent event) throws IOException {

        Parent MainMenuParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/CreateNewAccount.fxml")));
        Scene MainMenuScene = new Scene(MainMenuParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(MainMenuScene);
        window.show();
    }
}
