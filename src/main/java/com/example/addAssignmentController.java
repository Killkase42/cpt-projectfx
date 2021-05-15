package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class addAssignmentController {


    // Success or failure text
    public Text notFilledField;
    public Text creationSuccess;
    public Text incorrectField;

   // The different Fields, used for seeing if things are correctly set up
    public TextField nameOfAssignment;
    public TextField marksAssignment;
    public DatePicker dueDateAssignment;

    //Fields for displaying the info of the assignment
    public Text showAssignmentName;
    public Text showAssignmentDate;
    public Text showAssignmentMarks;


    //Arrays that will be used to store the data of the assignments
    public static String[] assignmentName = new String[0];
    public static String[] assignmentMarks = new String[0];
    public static String[] assignmentDueDate = new String[0];





    // Changes screen to the main calendar scree
    public void changeScreenButton(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calendarScreen.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addAssignmentScene);
        window.show();

    }

    // Gives out Error when not all fields are filled out
    public void fieldNotFilled() throws IOException {
        notFilledField.setVisible(true);
        }

       // Gives error if a field is filled incorrectly
        public void fieldFilledIncorrect() throws IOException {
        incorrectField.setVisible(true);
        }

        // Stores the assignment info
        public void storeAssignment() throws IOException {
        String name;
        String marks;
        LocalDate date;


        //Expand the array to make more room
        assignmentName = Arrays.copyOf(assignmentName,assignmentName.length+1);
        assignmentMarks = Arrays.copyOf(assignmentMarks,assignmentMarks.length+1);
        assignmentDueDate = Arrays.copyOf(assignmentDueDate,assignmentDueDate.length+1);

        // adding the variables to the arrays
        assignmentName[assignmentName.length-1] = nameOfAssignment.getText();
        assignmentMarks[assignmentMarks.length-1] = marksAssignment.getText();
        assignmentDueDate[assignmentDueDate.length-1] = String.valueOf(dueDateAssignment.getValue());

        //Getting the assignment details in memory
        name = nameOfAssignment.getText();
        marks = marksAssignment.getText();
        date = dueDateAssignment.getValue();

        showAssignmentName.setText(name);
        showAssignmentMarks.setText(marks + "%");
        showAssignmentDate.setText(String.valueOf(date));



    }

        // Deciding what the "Submit Assignment" button should do
        public void checkFieldStatus(ActionEvent event) throws IOException {
        notFilledField.setVisible(false);
        creationSuccess.setVisible(false);
        incorrectField.setVisible(false);

        boolean done;
            if (nameOfAssignment.getText() == null || nameOfAssignment.getText().isEmpty()) {
                fieldNotFilled();
                done = true;
            }
            else if (marksAssignment.getText() == null || marksAssignment.getText().isEmpty()) {
                fieldNotFilled();
                done = true;
            }
            else if (dueDateAssignment.getValue() == null) {
                fieldNotFilled();

            } else {
                creationSuccess.setVisible(true);
                storeAssignment();
            }
            done = true;
        }
}

