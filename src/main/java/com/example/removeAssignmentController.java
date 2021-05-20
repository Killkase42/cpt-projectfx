package com.example;


import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class removeAssignmentController {

    public Text assignmentNames;
    public TextField selectAssignment;
    public Text error;
    public Text noAssignmentsIn;
    public Text removeSuccess;

    /*
    Pre: None
    Post: Simply sets a text to all the assignments
     */
    public void setText() throws IOException {
        assignmentNames.setText(Arrays.toString(ControllerCalendar.assignmentName));
    }

    /*
    Pre: None
    Post:Removes the text from a text Field
     */
    public void removeText() throws IOException {
        selectAssignment.setText("");
    }

    /*
    Pre: the Assignment name, marks, due-date, hours, and score arrays to have at least one variable
    Post: Selects a assignment, and removed it from all related arrays
     */
    public void removeAssignment() throws GeneralSecurityException, IOException {
        String choice;
        int id;
        choice = selectAssignment.getText();

        //Getting the assignment array id
        id = Arrays.asList(ControllerCalendar.assignmentName).indexOf(choice);

        // Deleting assignment from Google Sheets

        String[][] assignmentArray = SheetsAPI.PullAssignments();
        for (int i = 0; i < ControllerCalendar.assignmentName.length; i++){
          System.out.println(Arrays.toString(assignmentArray[i]));
        }

        String[] delete = new String[4];
        delete[0] = ControllerCalendar.assignmentName[id];
        delete[1] = ControllerCalendar.assignmentMarks[id];
        delete[2] = ControllerCalendar.assignmentDueDate[id];
        delete[3] = String.valueOf(ControllerCalendar.assignmentScore[id]);

        removeSuccess.setVisible(true);
        String assignmentToDelete = SheetsAPI.DeleteAssignment(delete);
        System.out.println(assignmentToDelete);

       // ControllerCalendar.assignmentName = ArrayUtils.remove(ControllerCalendar.assignmentName,id);
       // ControllerCalendar.assignmentMarks = ArrayUtils.remove(ControllerCalendar.assignmentMarks,id);
      //  ControllerCalendar.assignmentDueDate = ArrayUtils.remove(ControllerCalendar.assignmentDueDate,id);
       // ControllerCalendar.assignmentHours = ArrayUtils.remove(ControllerCalendar.assignmentHours,id);
        // ControllerCalendar.assignmentScore = ArrayUtils.remove(ControllerCalendar.assignmentScore,id);






       // for (int i = ControllerCalendar.isolateDays(String.valueOf(LocalDate.now()));
          //   i <= ControllerCalendar.isolateDays(String.valueOf(ControllerCalendar.assignmentDueDate[id])); i++) {
          //  ControllerCalendar.dateScore[i] -= ControllerCalendar.assignmentScore[id];
       // }

    }



    /*
    Pre: None
    Post: Checks to see if user entered correct information
     */
    public void textCheck() throws GeneralSecurityException, IOException {
        removeSuccess.setVisible(false);
        error.setVisible(false);
        noAssignmentsIn.setVisible(false);


        String choice = selectAssignment.getText();

        if (ControllerCalendar.assignmentName.length == 0) {
            noAssignmentsIn.setVisible(true);
        }

        for (int i = 0; i < ControllerCalendar.assignmentName.length; i++)
        if (!choice.equals(ControllerCalendar.assignmentName[i])) {
            error.setVisible(true);
        } else {
            removeAssignment();
        }

    }
    }
