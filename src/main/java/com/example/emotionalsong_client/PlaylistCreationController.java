package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Questa classe gestisce la creazione di una nuova playlist nell'interfaccia utente.
 */
public class PlaylistCreationController {
    @FXML
    private TextField pl_name;
    @FXML
    private Text errTXT;
    /**
     * Metodo per caricare la finestra di creazione di una nuova playlist.
     */
    public void load(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("creaPlaylist.fxml"));
        try {
            if (!HelloController.CPOP) {
                HelloController.CPOP = true;
                Parent r1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setScene(new Scene(r1));
                stage.setOnCloseRequest(WindowEvent::consume);
                stage.show();
            }
        }catch (Exception e){}
    }
    /**
     * Metodo per chiudere la finestra di creazione di una nuova playlist.
     *
     * @param ae L'evento di azione generato dalla chiusura della finestra
     */
    public void close(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
        HelloController.CPOP = false;
    }
    /**
     * Metodo per creare una nuova playlist quando viene cliccato il pulsante "Crea".
     *
     * @param ae L'evento di azione generato dal pulsante "Crea"
     */
    public void crea(ActionEvent ae){
        String np = pl_name.getText();
        if (np.equals("")){
            errTXT.setText("impossibile creare");
        }else if (np.contains("~")){
            errTXT.setText("il valore ~ non può essere presente");
        }else {
            try {
                Socket s = new Socket(HelloController.IP, HelloController.PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println("B");
                in.readLine();
                out.println(HelloController.userID+"~"+np);
                String res = in.readLine();
                if (res.equals("-1")){
                    errTXT.setText("Playlist già esistente");
                }else{
                    HelloController.hc.loadPlaylists();
                    close(ae);
                }
            }catch (Exception e){
                errTXT.setText("Impossibile connettersi al server");
            }
        }
    }
}
