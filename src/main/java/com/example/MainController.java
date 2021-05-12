package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    @FXML
    Button button1;

    @FXML
    public void buttonpress(ActionEvent actionEvent) {
        System.out.println("it worked");
    }
}
