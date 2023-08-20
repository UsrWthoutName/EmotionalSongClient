package com.example.emotionalsong_client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmotionalSong extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HelloController hc = new HelloController();
        hc.load(stage, hc);

    }

    public static void main(String[] args) {
        launch();
    }
}