package com.example;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static com.example.ControllerCalendar.dateScore;

public class removeAssignmentController {

    // success or error
    public Text error;
    public Text noAssignmentsIn;
    public Text removeSuccess;
    public ComboBox<String> selectedAssignment;

    // Text fields for information
    public Text score;
    public Text name;
    public Text weighting;
    public Text dueDate;


    /*
     Pre: None
     Post: Assigns the values of all assignment names to a comboBox (drop-down menu)
      */
    public void comboBoxAssign() throws GeneralSecurityException, IOException {
        String[][] assignmentInfo = SheetsAPI.PullAssignments();

        ObservableList<String> data = FXCollections.observableArrayList();

        for (int i = 1; i < assignmentInfo.length; i++) {
            data.add(assignmentInfo[i][0]);
        }
        selectedAssignment.setItems(data);
    }

    /*
    Pre: the Assignment name, marks, due-date, hours, and score arrays to have at least one variable
    Post: Selects a assignment, and removed it from all related arrays
     */
    public void removeAssignment() throws GeneralSecurityException, IOException {
        removeSuccess.setVisible(false);
        error.setVisible(false);
        noAssignmentsIn.setVisible(false);
        String choice;
        int id = 0;
        choice = selectedAssignment.getValue();
        String[][] assignmentInfo = SheetsAPI.PullAssignments();


        //Getting the assignment array id
        for (int i = 1; i < assignmentInfo.length; i++) {
            if (assignmentInfo[i][0].equals(choice)) {
                id = i;
                break;
            }
        }
        // Creating info of array to delete
        String[] delete = new String[6];
        delete[0] = assignmentInfo[id][0];
        delete[1] = assignmentInfo[id][1];
        delete[2] = assignmentInfo[id][2];
        delete[3] = assignmentInfo[id][3];
        delete[4] = assignmentInfo[id][4];
        delete[5] = assignmentInfo[id][5];

        // Confirmation that user has removed the selected assignment
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Assignment " + "\"" + assignmentInfo[id][0] + ",\"" + "removed successfully.");
        alert.showAndWait();

        // Updating date score
        for (int i = ControllerCalendar.isolateDays(assignmentInfo[id][4]);
                      i <= ControllerCalendar.isolateDays(String.valueOf(assignmentInfo[id][2])); i++) {
         dateScore[i-1] -= Integer.parseInt(assignmentInfo[id][3]);
         }
        System.out.println(Arrays.toString(dateScore));


       // Reseting assignments
        selectedAssignment.setValue(null);
        name.setText("Name: ");
        weighting.setText("Weighting: ");
        dueDate.setText("Due Date: ");
        score.setText("Score: ");

        String assignmentToDelete = SheetsAPI.DeleteAssignment(delete);

    }

    /*
    Pre: None
    Post: Checks to see if user entered correct information
     */
    public void textCheck() throws GeneralSecurityException, IOException {
        removeSuccess.setVisible(false);
        error.setVisible(false);
        noAssignmentsIn.setVisible(false);
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        String choice = selectedAssignment.getValue();


       // Checking if assignment was entered correctly
        if (choice == null) {
            // error alert.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You have not selected a assignment to remove! \n" +
                    "Select a assignment from the drop-down menu.");
            alert.showAndWait();
        } else {
            removeAssignment();
        }
    }

    public void textInfoSet()throws GeneralSecurityException, IOException {
        String[][] assignmentInfo = SheetsAPI.PullAssignments();


        // Run code below when selecting a assignment
        selectedAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int id = 0;
                String choice = selectedAssignment.getValue();

                //Getting the assignment array id
                for (int i = 1; i < assignmentInfo.length; i++) {
                    if (assignmentInfo[i][0].equals(choice)) {
                        id = i;
                        break;
                    } }

                name.setText("Name: " + assignmentInfo[id][0]);
                weighting.setText("Weighting: " + assignmentInfo[id][1] + "%");
                dueDate.setText("Due-Date: " + assignmentInfo[id][2]);
                score.setText("Score: " +assignmentInfo[id][3]);

            }
        });




    }
}
