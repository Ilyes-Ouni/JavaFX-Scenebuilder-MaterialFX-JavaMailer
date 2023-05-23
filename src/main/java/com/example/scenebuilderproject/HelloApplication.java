package com.example.scenebuilderproject;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        try{
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("interface/DashboardAdmin.fxml")));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            //stage.setResizable(false);
            stage.show();

            stage.setOnCloseRequest(windowEvent -> Platform.exit());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Application.launch();
    }
}