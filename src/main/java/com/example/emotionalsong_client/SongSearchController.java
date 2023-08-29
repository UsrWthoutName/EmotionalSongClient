package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class SongSearchController {
    static private VBox centralVB;
    @FXML
    private Text sb_nome;
    @FXML
    private Text sb_autore;
    @FXML
    private Text sb_anno;
    @FXML
    private Text sb_id;



    public void load(VBox c, String sID, String sTI, String sAU, String sAN){
        centralVB = c;
        URL url = getClass().getResource("songBlock.fxml");
        Parent ca = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            ca = fxmlLoader.load();
            SongSearchController controller = fxmlLoader.getController();

            controller.sb_anno.setText(sAN);
            controller.sb_nome.setText(sTI);
            controller.sb_autore.setText(sAU);
            controller.sb_id.setVisible(false);
            controller.sb_id.setText(sID);

        }catch (IOException e){}
        centralVB.getChildren().add(ca);

    }
    public void loadSong(){
        HelloController.hc.loadSong(sb_id.getText(), sb_nome.getText(), sb_autore.getText(), sb_anno.getText());
    }

}
