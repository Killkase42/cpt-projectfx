package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ControllerCalendar.dateScore;
import static com.example.ControllerCalendar.isolateDays;


public class addAssignmentController {


    // May 1
    LocalDate currentDate = LocalDate.parse("2021-05-01");


    // Success or failure text
    public Text notFilledField;
    public Text creationSuccess;
    public Text incorrectField;
    public Text errorsList;

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
    public Text showHoursPerDay;
    public Text assignmentScore;
    public Button closeButton;



    double daily_Hours;
    // Errors list
    List<String> errors = new ArrayList<>();

    //--------------------------------------------------------------------------------------------------------------
    //TEXT FIELD CONVERSIONS TO NUMBER ONLY



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

    /*
    Pre: NumberTextField
    Post: changes text fields so they are only number
     */
   public void changeTextField() {
       numberTextField(hoursOfAssignment);
       numberTextField(marksAssignment);

   }

    /*
     Pre: String
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


    //--------------------------------------------------------------------------------------------------------------
    // ERROR/SUCCESS SCREENS


    /*
    Pre: None
    Post: Gives out error message if a field is not filled correctly
     */
    public void error() throws IOException {

        StringBuilder newLine = new StringBuilder();
// Adding a space for every error found
        for (int i = 0; i < errors.size();i++) {
            newLine.append(" ");
            newLine.append(errors.get(i));
        }
        errorsList.setText(newLine.toString());
       // clearing error list
        errors.clear();

        // Clearing Fields
        nameOfAssignment.setText(null);
        marksAssignment.setText(null);
        dueDateAssignment.setValue(null);
        hoursOfAssignment.setText(null);

        // Clearing assignment details showing
        showAssignmentName.setText("Name: ");
        showAssignmentDate.setText("Due Date: ");
        showAssignmentHours.setText("Total Hours: ");
        showAssignmentMarks.setText("Weighting: ");
        showAssignmentScore.setText("Date Score on Due Date: ");
        showHoursPerDay.setText("Daily Hours Per Day: ");
        assignmentScore.setText("Score: ");

    }

    /*
    Pre: None
    Post: Gives success message if assignment has been filled out correctly
     */
    public void success() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Assignment Added");
        alert.showAndWait();
    }


    //--------------------------------------------------------------------------------------------------------------
    // STORING/CHECKING ASSIGNMENT DETAILS


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

        //Getting the assignment details in memory
        name = nameOfAssignment.getText();
        marks = marksAssignment.getText();
        date = dueDateAssignment.getValue();
        hours = hoursOfAssignment.getText();
        score = WorkLoadCalculator();

        // Updating the date score
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        ControllerCalendar.updateDateScore();


        for (int i = isolateDays(String.valueOf(currentDate));
             i <= isolateDays(String.valueOf(dueDateAssignment.getValue())); i++) {
         dateScore[i-1] += score;
         }

        int printDateScore = isolateDays(String.valueOf(dueDateAssignment.getValue()));


        // Showing assignment details
        showAssignmentName.setText("Name: "+name);
        showAssignmentMarks.setText("Weighting: " + marks + "%");
        showAssignmentDate.setText("Due Date: " + date + " | " +
                Math.abs(currentDate.getDayOfMonth() - date.getDayOfMonth()) + " days until assignment is due." );
        showAssignmentHours.setText("Total Hours: " + hours);
        showAssignmentScore.setText("Date Score for " + dueDateAssignment.getValue() + ": " + dateScore[printDateScore-1]);
        showHoursPerDay.setText("Hours per day: "+ Math.round(daily_Hours));
        assignmentScore.setText("Score:" + score);


        // Adding information into one array so that it can upload online

        String[] assignmentInfoUpload = new String[6];
        assignmentInfoUpload[0] = nameOfAssignment.getText();
        assignmentInfoUpload[1] = marksAssignment.getText();
        assignmentInfoUpload[2] = String.valueOf(dueDateAssignment.getValue());
        assignmentInfoUpload[3] = String.valueOf(score);
        assignmentInfoUpload[4] = String.valueOf(currentDate);
        assignmentInfoUpload[5] = hours;

        // Storing assignment to online stuff
        SheetsAPI.UploadAssignment(assignmentInfoUpload);

    }

    /*
     Pre: None
     Post: Deciding what the "Submit Assignment" button should do
     */
    public void checkFieldStatus() throws IOException, GeneralSecurityException {
        int numberOfAssignments = 0;


        notFilledField.setVisible(false);
        creationSuccess.setVisible(false);
        incorrectField.setVisible(false);
        String[][] assignmentInfo = SheetsAPI.PullAssignments();

        // Error Checking
        // Assignment due-date left blank
        if (dueDateAssignment.getValue() == null) {
        errors.add("The \"Assignment Due-Date\" field has been left blank!");

    // Assignment in the past
        } else if (dueDateAssignment.getValue().isBefore(currentDate) || dueDateAssignment.getValue().equals(currentDate)) {
        errors.add("You cannot have a assignment be due today, or a day in the past.");

        // Checking for multiple assignments on selected date
        } else {
            for (int i = 1; i < assignmentInfo.length; i++) {
                if (dueDateAssignment.getValue().equals(LocalDate.parse(assignmentInfo[i][2]))) {
                    numberOfAssignments += 1;
                } } }

        // Name of assignment left blank
        if (nameOfAssignment.getText() == null || nameOfAssignment.getText().isEmpty()) {
            errors.add("The \"Name of assignment\" field has been left blank!");

       //Name of assignment a number only
        } else if (!isString(nameOfAssignment)) {
            errors.add("The name of the assignment cannot be only a number!");

        // Assignment the same name as another assignment
        } else {
            for (int i = 1; i < assignmentInfo.length; i++) {
             if (nameOfAssignment.getText().equals(assignmentInfo[i][0])) {
                 errors.add("There is already a assignment with the name \"" + (nameOfAssignment.getText()) + "\"."); } }

       // Hours left blank
        } if (hoursOfAssignment.getText() == null || hoursOfAssignment.getText().isEmpty() ) {
            errors.add("The \"Hours of Assignment\" field has been left blank!");

        // Hours left at 0
        } else if (hoursOfAssignment.getText().equals("0")) {
            errors.add("The hours to complete the assignment cannot be 0!");

        // Marks assignment left blank
        }if (marksAssignment.getText() == null || marksAssignment.getText().isEmpty()) {
            errors.add("The \"Weighting of assignment\" field has been left blank!");

        // Marks assignment 0
        } else if (marksAssignment.getText().equals("0")) {
            errors.add("The weighting of the assignment cannot be 0!");

        // Marks greater than 10
        } else if (Integer.parseInt(marksAssignment.getText()) > 10) {
            errors.add("The weighting of the assignment cannot be more than 10!");

       // Number of assignments on specific day more than 3
        }  if (numberOfAssignments > 3) {
            errors.add("The date you have selected already has 3 assignments. Please pick another day");

            // checking if any errors happened
        }  if (errors.size() > 0) {
            error();

        } else {
            success();
            storeAssignment();
        }
    }


    //--------------------------------------------------------------------------------------------------------------
    // WORKLOAD CALCULATOR
    /*
    Pre: The weight, or marks, hours and due date of a assignment
    Post: Gives score to assignment based on multiple factors
     */
    public int WorkLoadCalculator() throws IOException {
        int temp_score = 0;
        // May 1
        String CurrentDate = ("2021-05-01");

        // Adding the worth to the score
        int worth = Integer.parseInt(marksAssignment.getText());
        temp_score += worth;

        // Converting dates into calculator format
        int currentDate = Date_To_Days(deleteYear(CurrentDate));
        int assignmentDueDate = Date_To_Days(deleteYear(String.valueOf(dueDateAssignment.getValue())));

        // Adding daily hours to score
        // Calculation of Daily Hours
        double totalHours = Double.parseDouble(hoursOfAssignment.getText());
        daily_Hours = totalHours / (assignmentDueDate - currentDate);

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

    //--------------------------------------------------------------------------------------------------------------
    // SCREEN EDITS

    /*
    Pre: None
    Post: Goes to score help screen
    */
    public void goToScoreHelp(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/dateScoreVsScore.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = new Stage();

        window.setScene(addAssignmentScene);
        window.show();
    }

    /*
    Pre: None
    Post: Closes current screen
     */
    public void closeButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}


