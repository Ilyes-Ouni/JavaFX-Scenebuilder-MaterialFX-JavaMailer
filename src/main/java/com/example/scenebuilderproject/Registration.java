package com.example.scenebuilderproject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Registration {
    public void openRegistration() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("interface/RegistrationInterface.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            //stage.setTitle("Registration");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
