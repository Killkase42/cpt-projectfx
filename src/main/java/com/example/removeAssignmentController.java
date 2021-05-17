package com.example;


import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;

public class removeAssignmentController {

    public Text assignmentNames;
    public TextField selectAssignment;

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
    public void removeAssignment() {
        String choice;
        int id;
        choice = selectAssignment.getText();

        //Getting the assignment array id
        id = Arrays.asList(ControllerCalendar.assignmentName).indexOf(choice);

        ControllerCalendar.assignmentName = ArrayUtils.remove(ControllerCalendar.assignmentName,id);
        ControllerCalendar.assignmentMarks = ArrayUtils.remove(ControllerCalendar.assignmentMarks,id);
        ControllerCalendar.assignmentDueDate = ArrayUtils.remove(ControllerCalendar.assignmentDueDate,id);
        ControllerCalendar.assignmentHours = ArrayUtils.remove(ControllerCalendar.assignmentHours,id);
        ControllerCalendar.assignmentScore = ArrayUtils.remove(ControllerCalendar.assignmentScore,id);
    }


    
    }
