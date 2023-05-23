package com.example.scenebuilderproject.controllers;

import com.example.scenebuilderproject.Registration;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ResourceBundle;

public class SplashInterfaceController implements Initializable {
    @FXML
    private Label mainTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new SplashScreen().start();
    }
    class SplashScreen extends Thread{
        @Override
        public void run(){
            try{
                Thread.sleep(4520);
                //Thread.sleep(120);
                Platform.runLater(() -> {
                    Registration registration = new Registration();
                    registration.openRegistration();
                    mainTitle.getScene().getWindow().hide();
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
