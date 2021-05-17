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
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
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
     * Writes an assignment's name, total marks, due date, and difficulty to a new row in the Sheet.
     *pre: Is called from another class.
     *post: Assignment data is written to a new row of the spreadsheet.
     */
    public static void UploadAssignment(String[] assignmentData) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(assignmentData[0], assignmentData[1], assignmentData[2], assignmentData[3])
                ));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "data!D:D", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("OVERWRITE")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    /**
     * Checks the Sheet for the entered username and, subsequently, password (returns errors if they are not found.)
     *pre: Is called from another class.
     *post: Confirmation or error is returned based on whether or not username and password are valid.
     * @return
     */
    public static boolean ConfirmUserCredentials(String enteredUsername, String enteredPassword) throws IOException,
            GeneralSecurityException {
        sheetsService = getSheetsService();

        String range = "data!A:B";
        boolean authenticationResult = false;

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
                        authenticationResult = true;
                    } else {
                        authenticationResult = false;
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
        String[] assignmentInfo = {"Geography ISP", "120", "05-20-21", "Hard"};

        //Example of how you would use the ConfirmUserCredentials method.
        //String result = ConfirmUserCredentials("Sleepy", "Sonic123");
        //System.out.println(result);
        //System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

    }
}