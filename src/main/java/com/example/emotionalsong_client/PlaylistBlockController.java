package com.example.emotionalsong_client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistBlockController implements Initializable {
    @FXML
    protected Text pb_nome;
    @FXML
    protected Text pb_num;
    @FXML
    protected Text pb_id;

    private static VBox pbox;



    public void load(VBox p, String id, String nome, String ncanzoni){
        pbox = p;

        URL caurl = getClass().getResource("playlistBlock.fxml");
        Parent ca = null;
        try {
            FXMLLoader f = new FXMLLoader(caurl);
            ca =f.load();
            PlaylistBlockController controller = f.getController();

            controller.pb_nome.setText(nome);
            controller.pb_num.setText(ncanzoni+" canzoni");
            controller.pb_id.setText(id);
        } catch (IOException e) {
            System.out.println(e);
        }
        pbox.getChildren().add(ca);

    }

    public void a(){
        System.out.println("playlist "+pb_id.getText());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
