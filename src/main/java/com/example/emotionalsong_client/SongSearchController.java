package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;

/**
 * Questa classe gestisce la visualizzazione di un blocco di informazioni sulla canzone.
 * Viene utilizzata per mostrare i risultati di una ricerca di canzoni.
 */
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


    /**
     * Carica le informazioni sulla canzone nel blocco.
     *
     * @param c   Riferimento al contenitore principale
     * @param sID ID della canzone
     * @param sTI Titolo della canzone
     * @param sAU Nome dell'artista
     * @param sAN Anno di pubblicazione
     */
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
    /**
     * Carica le informazioni dettagliate della canzone quando il blocco viene cliccato.
     */
    public void loadSong(){
        HelloController.hc.loadSong(sb_id.getText(), sb_nome.getText(), sb_autore.getText(), sb_anno.getText());
    }

}
