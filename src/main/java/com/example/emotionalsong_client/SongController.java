package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class SongController {
    @FXML
    public Text sg_nome;
    @FXML
    public Text sg_anno;
    @FXML
    public Text sg_artista;
    @FXML
    public Text val_AMZ;
    @FXML
    public Text val_SOL;
    @FXML
    public Text val_TND;
    @FXML
    public Text val_NOS;
    @FXML
    public Text val_CAL;
    @FXML
    public Text val_POW;
    @FXML
    public Text val_JOY;
    @FXML
    public Text val_TEN;
    @FXML
    public Text val_SAD;
    @FXML
    public Text em_nome;
    @FXML
    public Text em_rec;
    @FXML
    public AnchorPane sg_BLC1;
    @FXML
    public AnchorPane sg_BLC2;
    @FXML
    public AnchorPane sg_BLC3;
    @FXML
    public Text sg_NV;
    @FXML
    private ComboBox sb_playlist;
    @FXML
    private Button addBTN;
    @FXML
    private Text sg_id;
    @FXML
    private Text sg_log;

    static private String[] pln;
    static private String[] pli;

    public void load(String id, String nome, String autore, String anno){
        //pl = HelloController.playlistNOME;

        System.out.println(id);
        HelloController.centralVB.getChildren().clear();

        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("C");
            in.readLine();
            out.println(HelloController.userID);
            String[] playlistvalide = in.readLine().split("~");
            int l = playlistvalide.length;
            int i = 0;
            int j = 0;
            pln = new String[l/2];
            pli = new String[l/2];
            while (i<l){
                pli[j] = playlistvalide[i];
                i++;
                pln[j] = playlistvalide[i];
                j++;
                i++;
            }

            out.println(id);
            String[] r = in.readLine().split("~");

            URL url = getClass().getResource("song.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent ca = fxmlLoader.load();
            SongController sc = fxmlLoader.getController();
            sc.sg_id.setText(id);

            sc.sg_nome.setText(nome);
            sc.sg_anno.setText(anno);
            sc.sg_artista.setText(autore);

            if (r[0].equals("null")){
                sc.sg_BLC1.setVisible(false);
                sc.sg_BLC2.setVisible(false);
                sc.sg_BLC3.setVisible(false);
                sc.sg_NV.setVisible(true);
            }
            else {
                //arrotonda valore double in x.x (1 decimale)
                i =0;
                while (i<9){
                    Double d = Double.parseDouble(r[i]);
                    String dt = String.format("%.1f",d);
                    r[i]=dt;
                    i++;
                }
                sc.val_AMZ.setText(r[0]);
                sc.val_SOL.setText(r[1]);
                sc.val_TND.setText(r[2]);
                sc.val_NOS.setText(r[3]);
                sc.val_CAL.setText(r[4]);
                sc.val_POW.setText(r[5]);
                sc.val_JOY.setText(r[6]);
                sc.val_TEN.setText(r[7]);
                sc.val_SAD.setText(r[8]);
            }


            i = 0;
            if (pln==null){
                sc.sb_playlist.setVisible(false);
                sc.addBTN.setVisible(false);
            }else{
                l = pln.length;
                while (i<l){
                    sc.sb_playlist.getItems().add(pln[i]);
                    i++;
                }
            }
            String rec = in.readLine();
            HelloController.centralVB.getChildren().add(ca); //VISUALIZZO VALUTAZIONI CANZONE

            if (!rec.equals("-1")){
                String[] sr = rec.split("~");
                l = sr.length/2; //numero blocchi emozione~recensione
                i =0;
                while (i<l*2){
                    url = getClass().getResource("review.fxml");
                    fxmlLoader = new FXMLLoader(url);
                    ca = fxmlLoader.load();
                    sc = fxmlLoader.getController();
                    sr[i]=  sr[i].substring(0, sr[i].length()-2); //rimuove ultimi 2 elementi (_n)
                    sr[i] = sr[i].substring(0, 1).toUpperCase() + sr[i].substring(1); //prima lettera uppercase

                    sc.em_nome.setText(sr[i]);
                    sc.em_rec.setText(sr[i+1]);
                    HelloController.centralVB.getChildren().add(ca);
                    i+=2;
                }
            }

        }catch (Exception e){
            System.err.println(e);
        }
    }
    public void addPlaylist(){
        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("I");
            in.readLine();
            int pos=0;
            int i=0;
            System.out.println(pln.length);
            while (i< pln.length){
                if (pln[i].equals(sb_playlist.getValue())){
                    pos = i;
                }
                i++;
            }
            String plst=pli[pos];
            System.out.println(plst);
            out.println(plst+"~"+sg_id.getText());
            String res = in.readLine();
            if (res.equals("1")){
                sg_log.setText("canzone aggiunta alla playlist");
            }
            else{
                sg_log.setText("canzone già presente nella playlist");
            }
            HelloController.hc.loadPlaylists();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
