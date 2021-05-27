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


public class CreateNewAccountController extends SheetsAPI{

    //variables
    public TextField UsernameTextField;
    public TextField PasswordTextField;
    public TextField ShowPasswordTextField;
    public Button ShowPasswordButton;
    public Label ErrorMessage;
    public Label ErrorMessage1;

    static boolean showingPass = false;


    /*
   Pre: None
   Post: reveals
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


    public void CreateAccount(ActionEvent event)  throws IOException, GeneralSecurityException {
        if (showingPass && !ShowPasswordTextField.getText().equals("")){
            PasswordTextField.setText(ShowPasswordTextField.getText());
        }

        //gets info from text field
        String name = UsernameTextField.getText();
        String passw = PasswordTextField.getText();

        ErrorMessage.setVisible(false); // Reset the visibility of the error message.
        ErrorMessage1.setVisible(false);

        //if the fields are empty and tries to validate. Returns an error
        if (name.isEmpty() || passw.isEmpty()) {
            ErrorMessage1.setVisible(false);
            ErrorMessage.setVisible(true);
        } else {
            String Result = UploadAccount(name, passw);
            if (Result == "Account name taken"){
                ErrorMessage.setVisible(false);
                ErrorMessage1.setVisible(true);
            } else if (Result == "Account successfully created") {
                Parent SuccessAccountParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/calendarScreen.fxml")));
                Scene SuccessAccountScene = new Scene(SuccessAccountParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(SuccessAccountScene);
                window.show();


            }
        }

    }


    public void GoToLogin(ActionEvent event) throws IOException {
        Parent MainParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
        Scene MainScene = new Scene(MainParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(MainScene);
        window.show();


    }
}
