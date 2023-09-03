package com.example.emotionalsong_client;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Questa classe rappresenta l'applicazione principale e avvia l'interfaccia utente.
 */
public class EmotionalSong extends Application {
    /**
     * Il metodo start() viene chiamato quando si avvia l'applicazione JavaFX.
     *
     * @param stage Lo stage principale dell'applicazione
     * @throws IOException Eccezione lanciata in caso di errore durante il caricamento dell'interfaccia utente
     */
    @Override
    public void start(Stage stage) throws IOException {
        HelloController hc = new HelloController();
        hc.load(stage, hc);
    }
    /**
     * Il metodo main() è il punto di ingresso dell'applicazione.
     *
     * @param args Argomenti della riga di comando (non utilizzati in questo caso)
     */
    public static void main(String[] args) {
        launch();
    }
}