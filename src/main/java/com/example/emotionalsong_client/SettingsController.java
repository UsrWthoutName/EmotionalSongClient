package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Questa classe gestisce le impostazioni dell'applicazione.
 */
public class SettingsController {
    @FXML
    private TextField ipL;
    @FXML
    private TextField PortL;
    /**
     * Metodo per caricare la finestra delle impostazioni.
     *
     * @throws IOException Eccezione in caso di errore nella lettura del file FXML
     */
    public void load() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("settings.fxml"));
        if (!HelloController.StOp){
            HelloController.StOp= true;
            Parent r1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(r1));
            stage.setOnCloseRequest(WindowEvent::consume);
            stage.show();
            SettingsController sc = fxmlLoader.getController();
            sc.ipL.setText(HelloController.IP);
            sc.PortL.setText(Integer.toString(HelloController.PORT));
        }
    }

    /**
     * Metodo per chiudere la finestra delle impostazioni.
     *
     * @param ae L'evento di azione generato
     */
    public void exit(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
        HelloController.StOp = false;
    }
    /**
     * Metodo per salvare le nuove impostazioni IP e porta.
     *
     * @param ae L'evento di azione generato
     */
    public void save(ActionEvent ae){
        HelloController.IP = ipL.getText();
        HelloController.PORT = Integer.parseInt(PortL.getText());

        try {
            FileWriter fw = new FileWriter("init.txt");
            String info = HelloController.IP+"\n"+HelloController.PORT;
            fw.write(info);
            fw.close();
        }catch (Exception e){}
        exit(ae);

    }


}
