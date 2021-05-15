package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ControllerCalendar {

    public void changeScreenButton(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addAsignment.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();
}
    public void changeScreenButton2(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();
    }

    public void changeScreenButton3(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("removeAssignment.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();
    }

}
