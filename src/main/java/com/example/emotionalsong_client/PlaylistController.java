package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class PlaylistController {
    @FXML
    private Text pl_nome;
    @FXML
    private Text pl_num;
    @FXML
    private Text pl_val;
    @FXML
    private Button pl_valBTN;
    @FXML
    private Text pl_id;
    @FXML
    private Text sb_nome;
    @FXML
    private Text sb_autore;
    @FXML
    private Text sb_anno;
    @FXML
    private Text sb_am;
    @FXML
    private Text sb_so;
    @FXML
    private Text sb_te;
    @FXML
    private Text sb_no;
    @FXML
    private Text sb_ca;
    @FXML
    private Text sb_po;
    @FXML
    private Text sb_jo;
    @FXML
    private Text sb_ts;
    @FXML
    private Text sb_sa;

    static private String playlistid;
    static private String playlistnome;
    static private String numerocanzoni;
    public void load(String id, String n, String nc) {
        playlistid = id;
        playlistnome = n;
        numerocanzoni = nc;
        boolean val=true;
        boolean hs=true;

        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("A");
            in.readLine();
            out.println(id);
            //C = VALUTATA(BOOLEAN)
            //  -1  -> playlist vuota
            //  t   -> playlist valutata
            //  f   -> playlist non valutata
            String c = in.readLine();
            URL url = getClass().getResource("playlist.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent ca = fxmlLoader.load();
            PlaylistController pc = fxmlLoader.getController();
            pc.pl_nome.setText(playlistnome);
            pc.pl_num.setText(numerocanzoni);
            pc.pl_id.setText(playlistid);
            if (c.equals("-1")){
                pc.pl_val.setVisible(false);
                pc.pl_valBTN.setVisible(false);
                hs=false;   //non ha canzoni
            } else if (c.equals("t")) {
                pc.pl_val.setText("Playlist già valutata");
                pc.pl_valBTN.setVisible(false);

                val = true;
            } else if (c.equals("f")) {
                pc.pl_val.setText("Playlist da valutare");
                pc.pl_valBTN.setVisible(true);

                val=false;
            }
            HelloController.centralVB.getChildren().clear();
            HelloController.centralVB.getChildren().add(ca);


            //CANZ = NOME~ARTISTA~ANNO
            //SE VALUTATE -> ~AMAZEMENT~SOLEMNITY~TENDERNESS~NOSTALGIA~CALMNESS~POWER~JOY~TENSION~SADNESS~AMAZEMENT_N~SOLEMNITY_N~TENDERNESS_N~NOSTALGIA_N~CALMNESS_N~POWER_N~JOY_N~TENSION_N~SADNESS_N
            //3 o 12 elementi

            if (hs){
                String el = in.readLine();
                String[] canz = el.split("~");
                int ncanzoni;
                ncanzoni = canz.length;
                if (val){   //se le canzoni sono valutate

                    int i = 0;
                    while (i<ncanzoni){
                        url = getClass().getResource("playlistSongVal.fxml");
                        fxmlLoader = new FXMLLoader(url);
                        Parent p = fxmlLoader.load();
                        PlaylistController blController = fxmlLoader.getController();

                        blController.sb_nome.setText(canz[i]);
                        blController.sb_autore.setText(canz[i+1]);
                        blController.sb_anno.setText(canz[i+2]);

                        blController.sb_am.setText(canz[i+3]);
                        blController.sb_so.setText(canz[i+4]);
                        blController.sb_te.setText(canz[i+5]);
                        blController.sb_no.setText(canz[i+6]);
                        blController.sb_ca.setText(canz[i+7]);
                        blController.sb_po.setText(canz[i+8]);
                        blController.sb_jo.setText(canz[i+9]);
                        blController.sb_ts.setText(canz[i+10]);
                        blController.sb_sa.setText(canz[i+11]);
                        HelloController.centralVB.getChildren().add(p);
                        i+=12;
                    }

                }else {
                    int i = 0;
                    while (i<ncanzoni){
                        url = getClass().getResource("playlistSong.fxml");
                        fxmlLoader = new FXMLLoader(url);
                        Parent p = fxmlLoader.load();
                        PlaylistController blController = fxmlLoader.getController();
                        blController.sb_nome.setText(canz[i]);
                        blController.sb_autore.setText(canz[i+1]);
                        blController.sb_anno.setText(canz[i+2]);
                        HelloController.centralVB.getChildren().add(p);
                        i+=3;
                    }
                }
            }


        }catch (Exception e){
            try {
                HelloController.centralVB.getChildren().clear();
                URL caurl = getClass().getResource("ConnectionErrorMsg.fxml");
                FXMLLoader f = new FXMLLoader(caurl);
                Parent ca =f.load();
                HelloController.centralVB.getChildren().add(ca);
            }catch (IOException e1){}
        }
    }

    public void valuta(){
        HelloController.hc.valuta(pl_id.getText());
    }
}
