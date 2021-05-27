package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;

import static com.example.addAssignmentController.Date_To_Days;
import static com.example.addAssignmentController.deleteYear;

public class ShowAssignment {
    

    // Displaying assignment Details
    public Text assignmentName;
    public Text assignmentMarks;
    public Text assignmentDate;
    public Text assignmentScore;
    public Text dateScore;
    public Text totalHours;
    public Text dailyHours;

    public Text assigned;

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
        ControllerCalendar.updateDateScore();

        //once user selects assignment
        selectedAssignment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int id = 0;



                String choice = selectedAssignment.getValue();
                System.out.println(selectedAssignment.getValue());

                    // Determines the id of the assignment selected
                    for (int i = 1; i < assignmentInfo.length; i++) {
                        if (assignmentInfo[i][0].equals(choice)) {
                            id = i;
                            break;
                        } }

                // Turning date selected into a number that can be identified to datescore
                int dateScoreIndex = Date_To_Days(deleteYear(assignmentInfo[id][2])) - 120;

                // variables
                String name = assignmentInfo[id][0];
                String weighting = assignmentInfo[id][1];
                LocalDate due_date = LocalDate.parse(assignmentInfo[id][2]);
                String score = assignmentInfo[id][3];
                String dateAssigned = assignmentInfo[id][4];
                int totalHourss = Integer.parseInt(assignmentInfo[id][5]);

                // Finding out daily hours
                int currentDate = Date_To_Days(deleteYear(String.valueOf(java.time.LocalDate.now())));
                int assignmentDueDate = Date_To_Days(deleteYear(String.valueOf(due_date)));
                int daily_Hours = totalHourss / (assignmentDueDate - currentDate);

                assignmentName.setText("Name: " + name);
                assignmentMarks.setText("Weighting: " + weighting);
                assignmentDate.setText("Due Date: " + due_date + " | " + Math.abs(LocalDate.now().getDayOfMonth()
                - due_date.getDayOfMonth()) + " days until due-date." );
                assignmentScore.setText("Score: " + score);
                assigned.setText("Assigned: " + dateAssigned);
                dateScore.setText("Date Score on due date: " + ControllerCalendar.dateScore[dateScoreIndex-1]);
                totalHours.setText("Total hours to complete: " + totalHourss);
                dailyHours.setText("Daily hours per day: " + daily_Hours);


                }
        });
        }

}








