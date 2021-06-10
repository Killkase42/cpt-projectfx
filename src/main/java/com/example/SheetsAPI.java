package com.example;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsAPI {
    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "cpt-projectfx";
    private static String SPREADSHEET_ID = "1HmUxAeaJO-PCz1HqXX6xWF91AjeV0B0MV1ELB5I0yUA";

    /**
     * An authorization method that grants the application access to Google Sheets
     *pre: The class is run.
     *post: The application is granted access to Google Sheets via the credentials file.
     */
    public static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = SheetsAPI.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new InputStreamReader(in)
        );

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");

        return credential;
    }

    /**
     * The actual Sheets service that the application makes use of.
     *pre: The class is run.
     *post: This application can directly interface with sheets via the new service.
     */
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Reads data from the specified rows spreadsheet.
     *pre: Is called from another class.
     *post: Data from the spreadsheet is printed.
     */
    public static void DataReading() throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!A1:B4";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values){
                System.out.printf("%s %s\n", row.get(0), row.get(1));
            }
        }
    }

    /**
     * Writes data to the next available row of the spreadsheet.
     *pre: Is called from another class.
     *post: Data is written to a new row of the spreadsheet.
     */
    public static void DataWriting(String username, String password) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(username, password) // More commas and strings can be added if desired.
                ));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "data", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    /**
     * Writes a new account's username and password to a new row in the Sheet.
     *pre: Is called from another class.
     *post: New account is written to a new row of the spreadsheet.
     * @return
     */
    public static String UploadAccount(String enteredUsername, String enteredPassword) throws IOException,
            GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!A:B";
        String authenticationResult = "Account successfully created";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        // Checks all rows to see if the entered username is taken.
        for (List row : values) {
            if (String.valueOf(row.get(0)).equals(enteredUsername)) {
                authenticationResult = "Account name taken";
                break;
            }
        }

        // If the for loop did not find any accounts with the same name, the new account is then created.
        if (authenticationResult.equals("Account successfully created")){
            ValueRange appendBody = new ValueRange()
                    .setValues(Arrays.asList(
                            Arrays.asList(enteredUsername, enteredPassword) // More commas and strings can be added if desired.
                    ));

            AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                    .append(SPREADSHEET_ID, "data!A:B", appendBody)
                    .setValueInputOption("USER_ENTERED")
                    .setInsertDataOption("OVERWRITE")
                    .setIncludeValuesInResponse(true)
                    .execute();
        }
        return authenticationResult;
    }

    /**
     * Writes a new assignment's name, total marks, due date, and difficulty to a new row in the Sheet.
     *pre: Is called from another class.
     *post: New assignment data is written to a new row of the spreadsheet.
     */
    public static void UploadAssignment(String[] assignmentData) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(assignmentData[0], assignmentData[1], assignmentData[2], assignmentData[3],
                                assignmentData[4], assignmentData[5])
                ));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "data!D:D", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("OVERWRITE")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    /**
     * Converts the data from each assignment on the Sheet into an array and puts them all into an ARRAY OF ARRAYS!!
     *pre: Is called from another class.
     *post: An array of arrays is returned that contains the data of each assignment on the Sheet.
     */
    public static String[][] PullAssignments() throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!D:I";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();


        String[][] assignmentArrayOfArrays = new String[values.size()][];

        int i = 0;
        for (List<Object> nestedList : values){
            assignmentArrayOfArrays[i++] = nestedList.toArray(new String[nestedList.size()]);
        }
        return assignmentArrayOfArrays;
    }

    /**
     * Deletes the specified assignment from the Sheet.
     *pre: Is called from another class.
     *post: The assignment is removed from the Sheet.
     */
    public static String DeleteAssignment(String[] assignmentToDelete) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!D:I";
        String deletionResult = "Specified assignment not found";
        ArrayList<String> checkAssignment = new ArrayList<String>();
        checkAssignment.add(assignmentToDelete[0]);
        checkAssignment.add(assignmentToDelete[1]);
        checkAssignment.add(assignmentToDelete[2]);
        checkAssignment.add(assignmentToDelete[3]);
        checkAssignment.add(assignmentToDelete[4]);
        checkAssignment.add(assignmentToDelete[5]);

        ArrayList<String> replacer = new ArrayList<String>();
        replacer.add("");
        replacer.add("");
        replacer.add("");
        replacer.add("");
        replacer.add("");
        replacer.add("");

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values){
                // If specified assignment is found in the list, remove it from the list.

                if (row.equals(checkAssignment)){
                    //Clearing the old space in the Sheet
                    for (int i = 1; i <= values.size(); i++) {
                        List<List<Object>> values1 = Arrays.asList(
                                Arrays.asList("", "", "", "", "", "")
                        );
                        ValueRange body1 = new ValueRange()
                                .setValues(values1);
                        UpdateValuesResponse result =
                                sheetsService.spreadsheets().values().update(SPREADSHEET_ID, "data!D" + i, body1)
                                        .setValueInputOption("USER_ENTERED")
                                        .execute();
                    }

                    // Remove the row that contains the assignment to delete.
                    values.remove(row);

                    values.add(Collections.singletonList(replacer));
                    deletionResult = "Assignment deleted";

                    break;
                }
            }
            // Uploading the list (without the deleted assignment) to the Sheet.
            int j = 1;
            for (List row3 : values) {
                ValueRange appendBody3 = new ValueRange()
                        .setValues(Arrays.asList(
                                Arrays.asList(row3.get(0), row3.get(1), row3.get(2), row3.get(3), row3.get(4),
                                        row3.get(5))
                        ));

                AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                        .append(SPREADSHEET_ID, "data!D"+j, appendBody3)
                        .setValueInputOption("USER_ENTERED")
                        .setInsertDataOption("OVERWRITE")
                        .setIncludeValuesInResponse(true)
                        .execute();
                j++;
            }
        }
        return deletionResult;
    }


    /**
     * Checks the Sheet for the entered username and, subsequently, password (returns errors if they are not found.)
     *pre: Is called from another class.
     *post: Confirmation or error is returned based on whether or not username and password are valid.
     * @return
     */
    public static String ConfirmUserCredentials(String enteredUsername, String enteredPassword) throws IOException,
            GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!A:B";
        String authenticationResult = "Account not found";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
                //Below statements check to see if the entered credentials are found in the Sheet.
                if (String.valueOf(row.get(0)).equals(enteredUsername)) {
                    if (String.valueOf(row.get(1)).equals(enteredPassword)) {
                        authenticationResult = "Account found, logging you in...";

                    } else {
                        authenticationResult = "Password is incorrect";
                    }
                    break;
                }
            }
        }
        return authenticationResult;
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        //Example of what format the arrays for UploadAssignment method must be in.
        String[] assignmentInfo = {"Arjuuuuun", "120", "05-20-21", "Hard", "04-20-21", "10"};

//        UploadAssignment(assignmentInfo);

        //Example of how you would use the ConfirmUserCredentials method.
//        String result = ConfirmUserCredentials("Sleepy", "Sonic123");
//        System.out.println(result);
//        System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

        //Example of how you would use the UploadAccount method.
//        String accountVar = UploadAccount("Java   >", "Python");
//        System.out.println(accountVar);

        //Example of how you would use the PullAssignments method/how it is formatted
//        String[][] assignmentArray = PullAssignments();
//        for (int i = 0; i < 150; i++){
//            System.out.println(Arrays.toString(assignmentArray[i]));
//        }

        //Example of how you would use the DeleteAssignment method.
//        String[] assignmentToDelete = {"ben","10","2021-05-20","20"};
//        String result = DeleteAssignment(assignmentToDelete);
//        System.out.println(result);

    }
}