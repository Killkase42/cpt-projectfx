package com.example;


import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
    public void setText() throws IOException, GeneralSecurityException {

        StringBuilder text = new StringBuilder();
        String[][] assignmentInfo = SheetsAPI.PullAssignments();

        for (int i = 1; i < assignmentInfo.length; i++) {
            text.append(", ");
            text.append(assignmentInfo[i][0]);

        }
        assignmentNames.setText(text.toString());
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
        removeSuccess.setVisible(false);
        error.setVisible(false);
        noAssignmentsIn.setVisible(false);
        String choice;
        int id = 0;
        choice = selectAssignment.getText();
        String[][] assignmentInfo = SheetsAPI.PullAssignments();


        //Getting the assignment array id
        for (int i = 1; i < assignmentInfo.length; i++) {
            if (assignmentInfo[i][0].equals(choice)) {
                id = i;
                break;
            }
        }

        System.out.println(id);
        // Deleting assignment from Google Sheets


        String[] delete = new String[4];
        delete[0] = assignmentInfo[id][0];
        delete[1] = assignmentInfo[id][1];
        delete[2] = assignmentInfo[id][2];
        delete[3] = assignmentInfo[id][3];

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
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        String choice = selectAssignment.getText();

        if (assignmentInfo[1][0].isEmpty()) {
            noAssignmentsIn.setVisible(true);
        }

        for (int i = 1; i < assignmentInfo.length; i++)
        if (!choice.equals(assignmentInfo[i][0])) {
            error.setVisible(true);
        } else {
            removeAssignment();
        }

    }
    }
