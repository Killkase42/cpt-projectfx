package com.example;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Objects;

import static com.example.addAssignmentController.Date_To_Days;
import static com.example.addAssignmentController.deleteYear;


public class ControllerCalendar {

    public Label ChangeableNameDisplay;
    public Label noAssignmentsError;
    public Label outsideOfMayError;

    public static int[] dateScore = new int[61];
    public static double[] dailyHoursScore = new double[31];

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
    Pre: User logs in and see's calendar screen
    Post: Sets a welcome message to the main calendar screen
     */
    public void setText() {
        ChangeableNameDisplay.setText(LoginController.nameAccount);
    }

    /*
    Pre: User clicked either update or show assignment buttons when there is no assignment
    Post: Sets error message if no assignment has been added
     */
    public void errorNoAssignment() {
        noAssignmentsError.setVisible(true);
    }



    //--------------------------------------------------------------------------------------------------------------
    //POPUP ASPECTS


    /*
    Pre: Pressed "add assignment" button
    Post: Opens the add-assignment pop-up menu
     */
    public void addAssignmentPopUp() throws IOException {
        noAssignmentsError.setVisible(false);
        outsideOfMayError.setVisible(false);

        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/addAsignment.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = new Stage();

        window.setScene(addAssignmentScene);
        window.show();
    }

    /*
    Pre: Pressed "Assignment Information" button
    Post: Posts the remove-assignment pop up enu
     */
    public void RemoveAssignmentPopUp() throws IOException, GeneralSecurityException {
        noAssignmentsError.setVisible(false);
        outsideOfMayError.setVisible(false);

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
    Pre: Pressed "help" button
    Post: Shows a help screen popup
     */
    public void helpPopUp() throws IOException {
        noAssignmentsError.setVisible(false);
        outsideOfMayError.setVisible(false);

        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Help.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = new Stage();

        window.setScene(addAssignmentScene);
        window.show();
    }


    /*
    Pre: Pressed "Logout" button
    Post: Goes back to the account screen menu
    */
    public void BackToAccountScreen(ActionEvent event) throws IOException {
        noAssignmentsError.setVisible(false);
        outsideOfMayError.setVisible(false);

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
     Pre: Pressed "update assignments" button:
     Post: Adds any assignments to the calendar
     */
    public void addAssignmentToCalendar() throws IOException, GeneralSecurityException {

        //reseting errors
        noAssignmentsError.setVisible(false);
        outsideOfMayError.setVisible(false);

        toolTipsUpdate();

        // checking if there is no assignment
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        try {
            if (assignmentInfo[1][0].isEmpty()) {}

        } catch (ArrayIndexOutOfBoundsException e) {
            errorNoAssignment();
            return;
        }


        updateDateAndDailyHoursScore();
        
        assignmentInfo = SheetsAPI.PullAssignments();

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

            String assignmentDate = assignmentInfo[i][2];
            String assignmentName = assignmentInfo[i][0];
          
            if (Objects.equals(assignmentDate, "2021-05-01")) {
                newLine.append("\n-");
                newLine.append(assignmentName);
            } else if (Objects.equals(assignmentDate, "2021-05-02")) {
                newLine2.append("\n-");
                newLine2.append(assignmentName);
            } else if (Objects.equals(assignmentDate, "2021-05-03")) {
                newLine3.append("\n-");
                newLine3.append(assignmentName);
            } else if (Objects.equals(assignmentDate, "2021-05-04")) {
                newLine4.append("\n-");
                newLine4.append(assignmentName);
            }
             else if (Objects.equals(assignmentDate,("2021-05-05"))) {
                newLine5.append("\n-");
                newLine5.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-06"))) {
                newLine6.append("\n-");
                newLine6.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-07"))) {
                newLine7.append("\n-");
                newLine7.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-08"))) {
                newLine8.append("\n-");
                newLine8.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-09"))) {
                newLine9.append("\n-");
                newLine9.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-10"))) {
                newLine10.append("\n-");
                newLine10.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-11"))) {
                newLine11.append("\n-");
                newLine11.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-12"))) {
                newLine12.append("\n-");
                newLine12.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-13"))) {
                newLine13.append("\n-");
                newLine13.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-14"))) {
                newLine14.append("\n-");
                newLine14.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-15"))) {
                newLine15.append("\n-");
                newLine15.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-16"))) {
                newLine16.append("\n-");
                newLine16.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-17"))) {
                newLine17.append("\n-");
                newLine17.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-18"))) {
                newLine18.append("\n-");
                newLine18.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-19"))) {
                newLine19.append("\n-");
                newLine19.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-20"))) {
                newLine20.append("\n-");
                newLine20.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-21"))) {
                newLine21.append("\n-");
                newLine21.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-22"))) {
                newLine22.append("\n-");
                newLine22.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-23"))) {
                newLine23.append("\n-");
                newLine23.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-24"))) {
                newLine24.append("\n-");
                newLine24.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-25"))) {
                newLine25.append("\n-");
                newLine25.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-26"))) {
                newLine26.append("\n-");
                newLine26.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-27"))) {
                newLine27.append("\n-");
                newLine27.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-28"))) {
                newLine28.append("\n-");
                newLine28.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-29"))) {
                newLine29.append("\n-");
                newLine29.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-30"))) {
                newLine30.append("\n-");
                newLine30.append(assignmentName);
            }
            else if (Objects.equals(assignmentDate,("2021-05-31"))) {
                newLine31.append("\n-");
                newLine31.append(assignmentName);
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
    Pre: Selected date on datepicker at bottom
    Post: Show the date score and the assignments on a specific date
     */
    public void showAssignmentName_dateScore() {
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
                    updateDateAndDailyHoursScore();
                } catch (GeneralSecurityException | IOException e) {
                    e.printStackTrace();
                }

                noAssignmentsError.setVisible(false);
                outsideOfMayError.setVisible(false);

               // If date is outside of may
                if (LocalDate.parse(date_selected).isBefore(LocalDate.of(2021,Month.MAY,1))
                        || LocalDate.parse(date_selected).isAfter(LocalDate.of(2021, Month.MAY,31))) {
                    outsideOfMayError.setVisible(true);
                    return;
                }


                // Turning date selected into a number that can be identified to datescore
                int date_selected_number = addAssignmentController
                        .Date_To_Days(addAssignmentController.deleteYear(date_selected)) - 120;

                //rounding
                double dailyHours = Math.round(dailyHoursScore[date_selected_number-1] * 100.0) / 100.0;
                // printing datescore out
                DateScoreOnDate.setText("Date score: " + dateScore[date_selected_number-1]);
                hoursOnDay.setText("Daily Hours: " + dailyHours);

                // Finding out what assignments on specefic date
                for (int i = 1; i < assignmentInfo.length; i++) {
                    if (date_selected.equals(assignmentInfo[i][2])) {
                        // adding it to the text
                        assignmentNames.append(assignmentInfo[i][0]);
                        assignmentNames.append(", ");

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

    /*
    Pre: Pressed "Update Assignments" button
    Post: updates the tooltips
     */
    public void toolTipsUpdate() throws GeneralSecurityException, IOException {
            updateDateAndDailyHoursScore();

            TP_may1.setText("May 1: \n" + "Date Score:" + dateScore[0] + "\n" + "Daily Hours: " + dailyHoursScore[0]);
            TP_may2.setText("May 2: \n" + "Date Score:" + dateScore[1] + "\n" + "Daily Hours: " + dailyHoursScore[1]);
            TP_may3.setText("May 3: \n" + "Date Score:" + dateScore[2] + "\n" + "Daily Hours: " + dailyHoursScore[2]);
            TP_may4.setText("May 4: \n" + "Date Score:" + dateScore[3] + "\n" + "Daily Hours: " + dailyHoursScore[3]);
            TP_may5.setText("May 5: \n" + "Date Score:" + dateScore[4] + "\n" + "Daily Hours: " + dailyHoursScore[4]);
            TP_may6.setText("May 6: \n" + "Date Score:" + dateScore[5] + "\n" + "Daily Hours: " + dailyHoursScore[5]);
            TP_may7.setText("May 7: \n" + "Date Score:" + dateScore[6] + "\n" + "Daily Hours: " + dailyHoursScore[6]);
            TP_may8.setText("May 8: \n" + "Date Score:" + dateScore[7] + "\n" + "Daily Hours: " + dailyHoursScore[7]);
            TP_may9.setText("May 9: \n" + "Date Score:" + dateScore[8] + "\n" + "Daily Hours: " + dailyHoursScore[8]);
            TP_may10.setText("May 10: \n" + "Date Score:" + dateScore[9] + "\n" + "Daily Hours: " + dailyHoursScore[9]);
            TP_may11.setText("May 11: \n" + "Date Score:" + dateScore[10] + "\n" + "Daily Hours: " + dailyHoursScore[10]);
            TP_may12.setText("May 12: \n" + "Date Score:" + dateScore[11] + "\n" + "Daily Hours: " + dailyHoursScore[11]);
            TP_may13.setText("May 13: \n" + "Date Score:" + dateScore[12] + "\n" + "Daily Hours: " + dailyHoursScore[12]);
            TP_may14.setText("May 14: \n" + "Date Score:" + dateScore[13] + "\n" + "Daily Hours: " + dailyHoursScore[13]);
            TP_may15.setText("May 15: \n" + "Date Score:" + dateScore[14] + "\n" + "Daily Hours: " + dailyHoursScore[14]);
            TP_may16.setText("May 16: \n" + "Date Score:" + dateScore[15] + "\n" + "Daily Hours: " + dailyHoursScore[15]);
            TP_may17.setText("May 17: \n" + "Date Score:" + dateScore[16] + "\n" + "Daily Hours: " + dailyHoursScore[16]);
            TP_may18.setText("May 18: \n" + "Date Score:" + dateScore[17] + "\n" + "Daily Hours: " + dailyHoursScore[17]);
            TP_may19.setText("May 19: \n" + "Date Score:" + dateScore[18] + "\n" + "Daily Hours: " + dailyHoursScore[18]);
            TP_may20.setText("May 20: \n" + "Date Score:" + dateScore[19] + "\n" + "Daily Hours: " + dailyHoursScore[19]);
            TP_may21.setText("May 21: \n" + "Date Score:" + dateScore[20] + "\n" + "Daily Hours: " + dailyHoursScore[20]);
            TP_may22.setText("May 22: \n" + "Date Score:" + dateScore[21] + "\n" + "Daily Hours: " + dailyHoursScore[21]);
            TP_may23.setText("May 23: \n" + "Date Score:" + dateScore[22] + "\n" + "Daily Hours: " + dailyHoursScore[22]);
            TP_may24.setText("May 24: \n" + "Date Score:" + dateScore[23] + "\n" + "Daily Hours: " + dailyHoursScore[23]);
            TP_may25.setText("May 25: \n" + "Date Score:" + dateScore[24] + "\n" + "Daily Hours: " + dailyHoursScore[24]);
            TP_may26.setText("May 26: \n" + "Date Score:" + dateScore[25] + "\n" + "Daily Hours: " + dailyHoursScore[25]);
            TP_may27.setText("May 27: \n" + "Date Score:" + dateScore[26] + "\n" + "Daily Hours: " + dailyHoursScore[26]);
            TP_may28.setText("May 28: \n" + "Date Score:" + dateScore[27] + "\n" + "Daily Hours: " + dailyHoursScore[27]);
            TP_may29.setText("May 29: \n" + "Date Score:" + dateScore[28] + "\n" + "Daily Hours: " + dailyHoursScore[28]);
            TP_may30.setText("May 30: \n" + "Date Score:" + dateScore[29] + "\n" + "Daily Hours: " + dailyHoursScore[29]);
            TP_may31.setText("May 31: \n" + "Date Score:" + dateScore[30] + "\n" + "Daily Hours: " + dailyHoursScore[30]);



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
    public static void updateDateAndDailyHoursScore() throws GeneralSecurityException, IOException {
        String CurrentDate = ("2021-05-01");

        //clearing arrays
        Arrays.fill(dateScore, 0);
        Arrays.fill(dailyHoursScore,0);
        String[][] assignmentInfo = SheetsAPI.PullAssignments();
        for (int i = 1; i < assignmentInfo.length; i++) {
            // Variables
            String dateAssigned = assignmentInfo[i][4];
            String dateDue = assignmentInfo[i][2];
            String score = assignmentInfo[i][3];

            //converts dates into readable numbers
            double currentDate = Date_To_Days(deleteYear((CurrentDate)));
            double assignmentDueDate = Date_To_Days(deleteYear(String.valueOf(assignmentInfo[i][2])));
            //calulating daily hours
            double daily_Hours = Integer.parseInt(assignmentInfo[i][5]) / (assignmentDueDate - currentDate);
            double roundedDailyHours = Math.round(daily_Hours * 100.0) / 100.0;

            // Adding score onto dateScore and daily hours score
            for (int j = isolateDays(dateAssigned);
                 j <= isolateDays(String.valueOf(dateDue)); j++) {
                dateScore[j-1] += Integer.parseInt(score);
                dailyHoursScore[j-1] += roundedDailyHours;

            }
        }
// Making sure everything is rounded properly
        for (int i = 0; i < 31; i++) {
            dailyHoursScore[i] = Math.round(dailyHoursScore[i] * 100.0) / 100.0;
        }

    }
}



