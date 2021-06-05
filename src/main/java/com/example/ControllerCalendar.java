package com.example;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Objects;

import static com.example.addAssignmentController.Date_To_Days;
import static com.example.addAssignmentController.deleteYear;


public class ControllerCalendar {

    public static Text welcomeText;
    public Label ChangeableNameDisplay;

    public static int[] dateScore = new int[61];

    // For showing details on specific day
    public DatePicker datePicker;
    public Label assignmentOnDate;
    public Label DateScoreOnDate;
    public Label hoursOnDay;
    public Label rating;

    // Labels for all the dates
    public Label May_1;public Label May_2;public Label May_3;public Label May_4;public Label May_5;
    public Label May_6;public Label May_7;public Label May_8;public Label May_9;public Label May_10;
    public Label May_11;public Label May_12;public Label May_13;public Label May_14;public Label May_15;
    public Label May_16;public Label May_17;public Label May_18;public Label May_19;public Label May_20;
    public Label May_21;public Label May_22;public Label May_23;public Label May_24;public Label May_25;
    public Label May_26;public Label May_27;public Label May_28;public Label May_29;public Label May_30;
    public Label May_31;

    // ToolTips
    public Tooltip TP_may1; public Tooltip TP_may2; public Tooltip TP_may3; public Tooltip TP_may4; public Tooltip TP_may5;
    public Tooltip TP_may6; public Tooltip TP_may7; public Tooltip TP_may8; public Tooltip TP_may9; public Tooltip TP_may10;
    public Tooltip TP_may11; public Tooltip TP_may12; public Tooltip TP_may13; public Tooltip TP_may14; public Tooltip TP_may15;
    public Tooltip TP_may16; public Tooltip TP_may17; public Tooltip TP_may18; public Tooltip TP_may19; public Tooltip TP_may20;
    public Tooltip TP_may21; public Tooltip TP_may22; public Tooltip TP_may23; public Tooltip TP_may24; public Tooltip TP_may25;
    public Tooltip TP_may26; public Tooltip TP_may27; public Tooltip TP_may28; public Tooltip TP_may29; public Tooltip TP_may30;
    public Tooltip TP_may31;

    /*
    Pre: None
    Post: Sets a welcome message to the main calendar screen
    NOT DONE YET
     */
    public void setText(String username) {
        welcomeText.setText(username);
    }

    /*
    Pre: None
    Post: Sets error message if no assignment has been added
     */
    public void errorNoAssignment() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("There are no assignment currently added." + "\n" +
                "Please add a assignment by clicking the \"Add Assignment\" button. ");
        alert.showAndWait();
    }



    //--------------------------------------------------------------------------------------------------------------
    //POPUP ASPECTS


    /*
    Pre:None
    Post: Opens the add-assignment pop-up menu
     */
    public void addAssignmentPopUp() throws IOException {

        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/addAsignment.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = new Stage();

        window.setScene(addAssignmentScene);
        window.show();
    }

    /*
    Pre: None
    Post: Posts the remove-assignment pop up enu
     */
    public void RemoveAssignmentPopUp() throws IOException, GeneralSecurityException {
        // checking if there is no assignment
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        try {
            if (assignmentInfo[1][0].isEmpty()) {}

        } catch (ArrayIndexOutOfBoundsException e) {
            errorNoAssignment();
        return;
        }

            Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/removeAssignment.fxml")));
            Scene addAssignmentScene = new Scene(addAssignmentParent);

            Stage window = new Stage();

            window.setScene(addAssignmentScene);
            window.show();
        }

    /*
    Pre: None
    Post: Shows a help screen popup
     */
    public void helpPopUp() throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Help.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = new Stage();

        window.setScene(addAssignmentScene);
        window.show();
    }


    /*
    Pre: None
    Post: Goes back to the account screen menu
    */
    public void BackToAccountScreen(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();
    }



    //--------------------------------------------------------------------------------------------------------------
    //CALENDAR DETAIL AND VISUAL ASPECTS



    /*
     NOTICE: LOTS OF VARIABLES AHEAD
     Pre: None:
     Post: Adds any assignments to the calendar
     */
    public void addAssignmentToCalendar() throws IOException, GeneralSecurityException {
        // THIS IS WHAT CHANGES THE NAME FOR THE PERSON THAT LOGGED IN
        ChangeableNameDisplay.setText(LoginController.nameAccount);

        updateDateScore();
        String[][] assignmentInfo = SheetsAPI.PullAssignments();

        // welcomeText.setText(LoginController.welcome);
        May_1.setText("");May_2.setText("");May_3.setText("");May_4.setText("");May_5.setText("");May_6.setText("");
        May_7.setText("");May_8.setText("");May_9.setText("");May_10.setText("");May_11.setText("");May_12.setText("");
        May_13.setText("");May_14.setText("");May_15.setText("");May_16.setText("");May_17.setText("");May_18.setText("");May_19.setText("");
        May_20.setText("");May_21.setText("");May_22.setText("");May_23.setText("");May_24.setText("");May_25.setText("");May_26.setText("");
        May_27.setText("");May_28.setText("");May_29.setText("");May_30.setText(""); May_31.setText("");

        StringBuilder newLine = new StringBuilder();StringBuilder newLine2 = new StringBuilder(); StringBuilder newLine3 = new StringBuilder();
        StringBuilder newLine4 = new StringBuilder();StringBuilder newLine5 = new StringBuilder();StringBuilder newLine6 = new StringBuilder();
        StringBuilder newLine7 = new StringBuilder();StringBuilder newLine8 = new StringBuilder();StringBuilder newLine9 = new StringBuilder();
        StringBuilder newLine10 = new StringBuilder();StringBuilder newLine11 = new StringBuilder();StringBuilder newLine12 = new StringBuilder();
        StringBuilder newLine13 = new StringBuilder();StringBuilder newLine14 = new StringBuilder();StringBuilder newLine15 = new StringBuilder();StringBuilder newLine16 = new StringBuilder();
        StringBuilder newLine17 = new StringBuilder();StringBuilder newLine18 = new StringBuilder();StringBuilder newLine19 = new StringBuilder();StringBuilder newLine20 = new StringBuilder();
        StringBuilder newLine21 = new StringBuilder();StringBuilder newLine22 = new StringBuilder();StringBuilder newLine23 = new StringBuilder();
        StringBuilder newLine24 = new StringBuilder();StringBuilder newLine25 = new StringBuilder();StringBuilder newLine26 = new StringBuilder();StringBuilder newLine27 = new StringBuilder();
        StringBuilder newLine28 = new StringBuilder();StringBuilder newLine29 = new StringBuilder();StringBuilder newLine30 = new StringBuilder();StringBuilder newLine31 = new StringBuilder();



        for (int i = 1; i < assignmentInfo.length; i++) {

            if (Objects.equals(assignmentInfo[i][2], "2021-05-01")) {
                newLine.append("\n");
                newLine.append(assignmentInfo[i][0]);
            } else if (Objects.equals(assignmentInfo[i][2], "2021-05-02")) {
                newLine2.append("\n");
                newLine2.append(assignmentInfo[i][0]);
            } else if (Objects.equals(assignmentInfo[i][2], "2021-05-03")) {
                newLine3.append("\n");
                newLine3.append(assignmentInfo[i][0]);
            } else if (Objects.equals(assignmentInfo[i][2], "2021-05-04")) {
                newLine4.append("\n");
                newLine4.append(assignmentInfo[i][0]);
            }
             else if (Objects.equals(assignmentInfo[i][2],("2021-05-05"))) {
                newLine5.append("\n");
                newLine5.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-06"))) {
                newLine6.append("\n");
                newLine6.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-07"))) {
                newLine7.append("\n");
                newLine7.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-08"))) {
                newLine8.append("\n");
                newLine8.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-09"))) {
                newLine9.append("\n");
                newLine9.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-10"))) {
                newLine10.append("\n");
                newLine10.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-11"))) {
                newLine11.append("\n");
                newLine11.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-12"))) {
                newLine12.append("\n");
                newLine12.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-13"))) {
                newLine13.append("\n");
                newLine13.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-14"))) {
                newLine14.append("\n");
                newLine14.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-15"))) {
                newLine15.append("\n");
                newLine15.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-16"))) {
                newLine16.append("\n");
                newLine16.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-17"))) {
                newLine17.append("\n");
                newLine17.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-18"))) {
                newLine18.append("\n");
                newLine18.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-19"))) {
                newLine19.append("\n");
                newLine19.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-20"))) {
                newLine20.append("\n");
                newLine20.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-21"))) {
                newLine21.append("\n");
                newLine21.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-22"))) {
                newLine22.append("\n");
                newLine22.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-23"))) {
                newLine23.append("\n");
                newLine23.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-24"))) {
                newLine24.append("\n");
                newLine24.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-25"))) {
                newLine25.append("\n");
                newLine25.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-26"))) {
                newLine26.append("\n");
                newLine26.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-27"))) {
                newLine27.append("\n");
                newLine27.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-28"))) {
                newLine28.append("\n");
                newLine28.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-29"))) {
                newLine29.append("\n");
                newLine29.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-30"))) {
                newLine30.append("\n");
                newLine30.append(assignmentInfo[i][0]);
            }
            else if (Objects.equals(assignmentInfo[i][2],("2021-05-31"))) {
                newLine31.append("\n");
                newLine31.append(assignmentInfo[i][0]);
            }
        }

        May_1.setText(newLine.toString());  May_2.setText(newLine2.toString());  May_3.setText(newLine3.toString());  May_4.setText(newLine4.toString());
        May_5.setText(newLine5.toString());  May_6.setText(newLine6.toString());  May_7.setText(newLine7.toString());  May_8.setText(newLine8.toString());
        May_9.setText(newLine9.toString());  May_10.setText(newLine10.toString()); May_11.setText(newLine11.toString()); May_12.setText(newLine12.toString());
        May_13.setText(newLine13.toString());  May_14.setText(newLine14.toString());May_15.setText(newLine15.toString()); May_16.setText(newLine16.toString());
        May_17.setText(newLine17.toString());  May_18.setText(newLine18.toString());  May_19.setText(newLine19.toString()); May_20.setText(newLine20.toString());
        May_21.setText(newLine21.toString());  May_22.setText(newLine22.toString());  May_23.setText(newLine23.toString());  May_24.setText(newLine24.toString());
        May_25.setText(newLine25.toString());  May_26.setText(newLine26.toString());  May_27.setText(newLine27.toString());  May_28.setText(newLine28.toString());
        May_29.setText(newLine29.toString());  May_30.setText(newLine30.toString());  May_31.setText(newLine31.toString());
    }

    /*
    Pre: None
    Post: Show the date score and the assignments on a specific date
     */
    public void showAssignmentName_dateScore() throws GeneralSecurityException, IOException {


        StringBuilder assignmentNames = new StringBuilder();


        // Does action when user picks a date
        datePicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //pulls assignment
                String[][] assignmentInfo = new String[0][];
                try {
                    assignmentInfo = SheetsAPI.PullAssignments();
                } catch (IOException | GeneralSecurityException e) {
                    e.printStackTrace();
                }

                String date_selected = String.valueOf(datePicker.getValue());

               //updates dateScore
                try {
                    updateDateScore();
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }
                // Turning date selected into a number that can be identified to datescore
                int date_selected_number = addAssignmentController
                        .Date_To_Days(addAssignmentController.deleteYear(date_selected)) - 120;
                // printing datescore out
                DateScoreOnDate.setText("Date score: " + dateScore[date_selected_number-1]);


                int hours = 0;
                // May 1
                String CurrentDate = ("2021-05-01");

                // Finding out what assignments on specefic date
                for (int i = 1; i < assignmentInfo.length; i++) {
                    if (date_selected.equals(assignmentInfo[i][2])) {
                        // adding it to the text
                        assignmentNames.append(assignmentInfo[i][0]);
                        assignmentNames.append(", ");

                        // calculating the daily hours
                        int currentDate = Date_To_Days(deleteYear((CurrentDate)));
                        int assignmentDueDate = Date_To_Days(deleteYear(String.valueOf(assignmentInfo[i][2])));
                        int daily_Hours = Integer.parseInt(assignmentInfo[i][5]) / (assignmentDueDate - currentDate);
                        hours += daily_Hours;
                        System.out.println(hours);
                        hoursOnDay.setText("Daily hours: " + hours);

                    }
                }
                // selecting rating
                if (dateScore[date_selected_number-1] <= 20) {
                    rating.setText("Rating: Very easy");
                } else if (dateScore[date_selected_number-1] > 20 & dateScore[date_selected_number-1] <= 30) {
                    rating.setText("Rating: Easy");
                } else if (dateScore[date_selected_number-1] > 30 & dateScore[date_selected_number-1] <= 40) {
                    rating.setText("Rating: Medium");
                } else if (dateScore[date_selected_number-1] > 40 & dateScore[date_selected_number-1] <= 50) {
                    rating.setText("Rating: Hard");
                } else if (dateScore[date_selected_number-1] > 50 & dateScore[date_selected_number-1] <= 60) {
                    rating.setText("Rating: Very Hard");
                } else if (dateScore[date_selected_number-1] > 60) {
                    rating.setText("Rating: Extreme (recommend removing/reassigning a assignment)");
                }


                assignmentOnDate.setText("Assignments: " + assignmentNames); }});




    }



    //--------------------------------------------------------------------------------------------------------------
    //DATE SCORE ASPECTS


    /*
    Pre: a date
    Post: isolates the days
     */
    public static int isolateDays(String date) {
        int index = date.lastIndexOf("-");
        return Integer.parseInt(date.substring(index + 1));
    }

    /*
    Pre: None
    Post: Updates the date score
     */
    public static void updateDateScore() throws GeneralSecurityException, IOException {
        Arrays.fill(dateScore, 0);
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        for (int i = 1; i < assignmentInfo.length; i++) {
            // Variables
            String dateAssigned = assignmentInfo[i][4];
            String dateDue = assignmentInfo[i][2];
            String score = assignmentInfo[i][3];

            // Adding score onto dateScore
            for (int j = isolateDays(dateAssigned);
                 j <= isolateDays(String.valueOf(dateDue)); j++) {

                dateScore[j-1] += Integer.parseInt(score);
            }
        }
    }
}



