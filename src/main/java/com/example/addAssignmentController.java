package com.example;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class addAssignmentController {



    // Success or failure text
    public Text notFilledField;
    public Text creationSuccess;
    public Text incorrectField;

    // The different Fields, used for seeing if things are correctly set up
    public TextField nameOfAssignment;
    public TextField marksAssignment;
    public DatePicker dueDateAssignment;
    public TextField hoursOfAssignment;

    //Fields for displaying the info of the assignment
    public Text showAssignmentName;
    public Text showAssignmentDate;
    public Text showAssignmentMarks;
    public Text showAssignmentScore;
    public Text showAssignmentHours;


    List<String> errors = new ArrayList<>();
    /*
    Pre: Text fields for Marks and Hours
    Post: Allows class to change textfield to be only number
     */
    public void numberTextField(TextField textField) {
        // force the field to be numeric only
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", "")); }
        });

    }


   public void changeTextField() {
       numberTextField(hoursOfAssignment);
       numberTextField(marksAssignment);

   }

    /*
     Pre: String (Preferably a scanner string)
     Current: Checks if string has space, or contains numbers
     Post: True if string is a letters, and false is string is not letters
      */
    public static boolean isString (TextField str){
        boolean string;
        try {
            Double.parseDouble(str.getText());
            string = false;
        } catch (NumberFormatException e) {
            string = true;
        }
        return string;
    }


   /*
    Pre: None
    Post: Gives out error message if a field is not filled correctly
     */
    public void error() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("The following errors have been found:");
        StringBuilder newLine = new StringBuilder();

        for (int i = 0; i < errors.size();i++) {
            newLine.append("\n");
            newLine.append(errors.get(i));
        }
        alert.setContentText(newLine.toString());
        alert.showAndWait();
        errors.clear();
    }

    /*
    Pre: None
    Post: Gives success message if assignment has been filled out correctly
     */
    public void success() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Assignment created successfully!");
        alert.showAndWait();
    }


    /*
     Pre: None
     Post:Stores the assignment info into arrays and in online sheets
     */
    public void storeAssignment() throws IOException, GeneralSecurityException {
        String name;
        String marks;
        LocalDate date;
        String hours;
        int score;

        //Expand the array to make more room
        ControllerCalendar.assignmentName = Arrays.copyOf(ControllerCalendar.assignmentName, ControllerCalendar.assignmentName.length + 1);
        ControllerCalendar.assignmentMarks = Arrays.copyOf(ControllerCalendar.assignmentMarks, ControllerCalendar.assignmentMarks.length + 1);
        ControllerCalendar.assignmentDueDate = Arrays.copyOf(ControllerCalendar.assignmentDueDate, ControllerCalendar.assignmentDueDate.length + 1);
        ControllerCalendar.assignmentHours = Arrays.copyOf(ControllerCalendar.assignmentHours, ControllerCalendar.assignmentHours.length + 1);
        ControllerCalendar.assignmentScore = Arrays.copyOf(ControllerCalendar.assignmentScore, ControllerCalendar.assignmentScore.length + 1);

        // adding the variables to the arrays
        ControllerCalendar.assignmentName[ControllerCalendar.assignmentName.length - 1] = nameOfAssignment.getText();
        ControllerCalendar.assignmentMarks[ControllerCalendar.assignmentMarks.length - 1] = marksAssignment.getText();
        ControllerCalendar.assignmentDueDate[ControllerCalendar.assignmentDueDate.length - 1] = String.valueOf(dueDateAssignment.getValue());
        ControllerCalendar.assignmentHours[ControllerCalendar.assignmentHours.length - 1] = hoursOfAssignment.getText();


        //Getting the assignment details in memory
        name = nameOfAssignment.getText();
        marks = marksAssignment.getText();
        date = dueDateAssignment.getValue();
        hours = hoursOfAssignment.getText();
        score = WorkLoadCalculator();
        ControllerCalendar.assignmentScore[ControllerCalendar.assignmentScore.length-1] = (score);


        for (int i = ControllerCalendar.isolateDays(String.valueOf(LocalDate.now()));
        i <= ControllerCalendar.isolateDays(String.valueOf(dueDateAssignment.getValue())); i++) {
            ControllerCalendar.dateScore[i] += score;
        }


        // Showing assignment details
        showAssignmentName.setText(name);
        showAssignmentMarks.setText(marks + "%");
        showAssignmentDate.setText(String.valueOf(date));
        showAssignmentHours.setText(hours + " Hours");
        showAssignmentScore.setText("Score: " + ControllerCalendar.dateScore[ControllerCalendar.isolateDays(String.valueOf(dueDateAssignment.getValue()))]);

        // Adding stuff into one array so that it can upload online

        String[] assignmentInfoUpload = new String[4];
        assignmentInfoUpload[0] = nameOfAssignment.getText();
        assignmentInfoUpload[1] = marksAssignment.getText();
        assignmentInfoUpload[2] = String.valueOf(dueDateAssignment.getValue());
        assignmentInfoUpload[3] = String.valueOf(score);

        // Storing assignment to online stuff
        SheetsAPI.UploadAssignment(assignmentInfoUpload);

    }

    /*
     Pre: None
     Post: Deciding what the "Submit Assignment" button should do
     */
    public void checkFieldStatus(ActionEvent event) throws IOException, GeneralSecurityException {
        notFilledField.setVisible(false);
        creationSuccess.setVisible(false);
        incorrectField.setVisible(false);


        // Error Checking
        // Checks for a field not filled out
        if (nameOfAssignment.getText() == null || nameOfAssignment.getText().isEmpty()) {
            errors.add("The \"Name of assignment\" field has been left blank!");

        } else if (!isString(nameOfAssignment)) {
            errors.add("The name of the assignment cannot be only a number!");

        }if (dueDateAssignment.getValue() == null) {
            errors.add("The \"Assignment Due-Date\" field has been left blank!");

        } if (hoursOfAssignment.getText() == null || hoursOfAssignment.getText().isEmpty() ) {
            errors.add("The \"Hours of Assignment\" field has been left blank!");

        }if (marksAssignment.getText() == null || marksAssignment.getText().isEmpty()) {
            errors.add("The \"Weighting of assignment\" field has been left blank!");

            //Checks if field is filled out incorrectly
        } else if (Integer.parseInt(marksAssignment.getText()) > 10) {
            errors.add("The weighting of the assignment cannot be more than 10!");


        } if (errors.size() > 0) {
            error();

        } else {
            success();
            storeAssignment();
        }
    }






    /*
    Pre: The weight, or marks, hours and due date of a assignment
    Post: Gives score to assignment based on multiple factors
     */
    public int WorkLoadCalculator() throws IOException {
        int temp_score = 0;

        // Adding the worth to the score
        int worth = Integer.parseInt(marksAssignment.getText());
        temp_score += worth;

        // Converting dates into calculator format
        int currentDate = Date_To_Days(deleteYear(String.valueOf(java.time.LocalDate.now())));
        int assignmentDueDate = Date_To_Days(deleteYear(String.valueOf(dueDateAssignment.getValue())));

        // Adding daily hours to score
        // Calculation of Daily Hours
        double totalHours = Double.parseDouble(hoursOfAssignment.getText());
        double daily_Hours = totalHours / (assignmentDueDate - currentDate);

        // Adding hours to score
        if (daily_Hours <= 1) {
            temp_score += 10;
        } else if ((daily_Hours <= 2) & (daily_Hours > 1)) {
            temp_score += 20;
        } else if ((daily_Hours <= 3) & (daily_Hours > 2)) {
            temp_score += 30;
        } else if ((daily_Hours <= 4) & (daily_Hours > 3)) {
            temp_score += 40;
        }

        return temp_score;
    }

    /*
    Pre: A String, that is a date, that does NOT have a year
    Post: Converts the date format, into a int, that is used for calculations
     */
    public static int Date_To_Days(String a) {
        int index = a.indexOf('-');
        int Month = Integer.parseInt(a.substring(0, index));
        int Days = Integer.parseInt(a.substring(index + 1));
        int Total_Days = 0;

        if (Month == 1) {
            Total_Days += 0;
        } else if (Month == 2) {
            Total_Days += 31;
        } else if (Month == 3) {
            Total_Days += 59;
        } else if (Month == 4) {
            Total_Days += 90;
        } else if (Month == 5) {
            Total_Days += 120;
        } else if (Month == 6) {
            Total_Days += 151;
        } else if (Month == 7) {
            Total_Days += 181;
        } else if (Month == 8) {
            Total_Days += 212;
        } else if (Month == 9) {
            Total_Days += 243;
        } else if (Month == 10) {
            Total_Days += 273;
        } else if (Month == 11) {
            Total_Days += 304;
        } else if (Month == 12) {
            Total_Days += 334;
        } else {
            Total_Days += 400;
        }

        Total_Days += Days;
        return Total_Days;

    }

    /*
    Pre: A String, that is a date, that does have its year
    Post: Deletes the year from the String, so that it can be converted into a int
     */
    public static String deleteYear(String year) {
        int index = year.indexOf("-");
        return year.substring(index+1);
    }

}


