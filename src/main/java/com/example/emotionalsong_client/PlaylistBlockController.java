package com.example.emotionalsong_client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;

public class PlaylistBlockController {
    @FXML
    Text pb_nome;
    @FXML
    Text pb_num;
    @FXML
    Text pb_id;
    @FXML
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


        } catch (IOException e) {}
        pbox.getChildren().add(ca);

    }

    public void loadPlaylist(){
        HelloController.hc.openPlaylist(pb_id.getText(), pb_nome.getText(), pb_num.getText());
    }
    public void creaPlaylist(){
        HelloController.hc.creaPlaylist();
    }

    public void reload(){
        HelloController.hc.loadPlaylists();
    }

}
