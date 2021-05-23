package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class ShowAssignment {
    

    // Displaying assignment Details
    public Text assignmentName;
    public Text assignmentMarks;
    public Text assignmentDate;
    public Text assignmentScore;
    public ComboBox<String> selectedAssignment;

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
       Pre: None
       Post: Finds what assignment user selected, sets the text
        */
    public void setTextDetails() throws IOException, GeneralSecurityException {

        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        int id = 0;
        String choice = selectedAssignment.getValue();

        // Checking if assignment was entered correctly
        if (choice == null) {

            // error alert.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("You have not selected a assignment to view the details of! \n" +
                    "Select a assignment from the drop-down menu.");
            alert.showAndWait();

        } else {
            // Determines the id of the assignment selected
            for (int i = 1; i < assignmentInfo.length; i++) {
                if (assignmentInfo[i][0].equals(choice)) {
                    id = i;
                    break;
                }
            }
            assignmentName.setText("Name: " + assignmentInfo[id][0]);
            assignmentMarks.setText("Marks: " + assignmentInfo[id][1]);
            assignmentDate.setText("Due Date: " + assignmentInfo[id][2]);
            assignmentScore.setText("Score: " + assignmentInfo[id][3]);

        }
        }
}
