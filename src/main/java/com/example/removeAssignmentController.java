package com.example;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;



public class removeAssignmentController {


    public Text assignmentNames;
    public TextField selectAssignment;

    //Configuring table
    @FXML private TableView<addAssignmentController> tableView;
    @FXML private TableColumn<addAssignmentController, Array> nameColumn;
    @FXML private TableColumn<addAssignmentController, Array> marksColumn;
    @FXML private TableColumn<addAssignmentController, Array> dateColumn;


    public void setText() throws IOException {
        assignmentNames.setText(Arrays.toString(addAssignmentController.assignmentName));
    }

    public void removeText() throws IOException {
        selectAssignment.setText("");
    }

    public void changeScreenButton(ActionEvent event) throws IOException {
        Parent addAssignmentParent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("calendarScreen.fxml")));
        Scene addAssignmentScene = new Scene(addAssignmentParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addAssignmentScene);
        window.show();
    }

    public void removeAssignment() {
        String choice;
        int id;
        choice = selectAssignment.getText();

        //Getting the assignment array id
        id = Arrays.asList(addAssignmentController.assignmentName).indexOf(choice);

        addAssignmentController.assignmentName = ArrayUtils.remove(addAssignmentController.assignmentName,id);
        addAssignmentController.assignmentMarks = ArrayUtils.remove(addAssignmentController.assignmentMarks,id);
        addAssignmentController.assignmentDueDate = ArrayUtils.remove(addAssignmentController.assignmentDueDate,id);








    }


    public void intialize(URL url, ResourceBundle rb) {
       //Setting up columns in table
        nameColumn.setCellValueFactory(new PropertyValueFactory<addAssignmentController, Array>("assignmentName"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<addAssignmentController, Array>("assignmentMarks"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<addAssignmentController, Array>("assignmentDueDate"));



    }
    
    }
