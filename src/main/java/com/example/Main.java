package com.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

// Starting the application
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        // setting root for splash screen
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/splashScreen.fxml")));
        primaryStage.setTitle("SMCS Calender Beta");
        primaryStage.setScene(new Scene(root, 859, 689));
        primaryStage.show();

        // setting pane
        AnchorPane pane = FXMLLoader.load
                (Objects.requireNonNull(getClass().getResource("fxml/splashScreen.fxml")));
        root.getChildren().setAll(pane);

        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);


       //Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), pane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);


        //Play fadeIn
        fadeIn.play();

       //Thread.sleep(3000);

        // Run fade out when fade in finished
        fadeIn.setOnFinished(event -> {
            fadeOut.play();
        });

        // Go to new screen once fade out complete
        fadeOut.setOnFinished(event -> {
            try {
                AnchorPane parentContent = FXMLLoader.load
                        (Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
                root.getChildren().setAll(parentContent);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public static void main(String[] args) {
        launch(args);
    }

}
