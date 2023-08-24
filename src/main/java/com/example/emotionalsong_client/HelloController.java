package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public VBox Pbox;
    @FXML
    public Button accediBTN;
    @FXML
    public Text usernameTag;
    @FXML
    private Text accAdv;
    @FXML
    private ScrollPane scrpane;
    @FXML
    private TextField srcTXT;
    @FXML
    private VBox cVB;
    @FXML
    private ScrollPane centSCLP;




    public static HelloController hc;
    static String IP = "localhost";
    static int PORT= 5000;
    static boolean LgOp = false;
    static String userID;
    static String[] playlistNOME;
    static String[] playlistID;
    static VBox centralVB;



    static public ScrollPane sp;
    static public VBox pbox;
    static public Text accadv;
    static public TextField searchT;




    //METODO LOAD CARICA INTERFACCIA E RICEVE ISTANZA DI HELLOCONTROLLER
    public void load(Stage stage, HelloController c) throws IOException {
        hc = c;
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionalSong.class.getResource("application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1058, 734);
        stage.setTitle("EmotionalSong");
        stage.setScene(scene);
        fxmlLoader.setController(c);
        stage.setResizable(false);
        stage.show();
    }



    public void login() throws IOException, InterruptedException {
        LoginController login = new LoginController();
        //pbox.setVisible(true);
        login.load(accediBTN, usernameTag, hc);
    }
    public void registra(Button b, Text u, HelloController h) throws IOException {
        hc = h;
        usernameTag = u;
        accediBTN = b;
        RegistrationController rc = new RegistrationController();
        rc.load(hc);
    }
    //CARICA PLAYLIST NELLA BARRA LATERALE
    public void loadPlaylists(){
        sp.setVisible(true);
        accadv.setVisible(false);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pbox.setVisible(true);

        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println("P");
            in.readLine();
            out.println(HelloController.userID);

            int numEl = Integer.parseInt(in.readLine());
            System.out.println(numEl);
            playlistNOME = new String[numEl];
            playlistID = new String[numEl];
            String playistsMsg = in.readLine();
            s.close();


            String[] playlist = playistsMsg.split("~");
            //0 = ID
            //1 = NOME PLAYLIST
            //2 = NUMERO CANZONI
            //...
            int i =0;
            int j=0;
            while (i<playlist.length){
                PlaylistBlockController block = new PlaylistBlockController();
                block.load(pbox, playlist[i], playlist[i+1], playlist[i+2]);
                playlistNOME[j] = playlist[i+1];
                playlistID[j] = playlist[i];
                i+=3;
                j++;
            }
        }catch (IOException e){
            //MANCATA CONNESSIONE A SERVER
            System.err.println("non va un cristo");
        }
    }
    //CARICA SINGOLA PLAYLIST NEL BLOCCO CENTRALE
    public void openPlaylist(){}
    //ESEGUE LA RICERCA E VISUALIZZA RISULTATO IN BLOCCO CENTRALE
    public void cerca(){
        centralVB.getChildren().clear();
        centSCLP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println(searchT.getText());

            out.println("S");
            in.readLine();
            out.println(searchT.getText());
            String st = in.readLine();
            String[] info = st.split("~");
            System.out.println(st);
            int i = 0;
            int j = 0;
            int l = info.length/4;
            while (j<l){
                SongSearchController sc = new SongSearchController();
                sc.load(centralVB, info[i+0], info[i+1], info[i+2], info[i+3], playlistNOME);
                i+=4;
                j++;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //APRE UNA CANZONE E VISUALIZZA INFORMAZIONI, MEDIA VALUTAZIONI E RECENSIONI
    public void loadSong(String id, String nome, String autore, String anno){
        System.out.println(id);
        centralVB.getChildren().clear();

        try {
            Socket s = new Socket(IP, PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("C");
            in.readLine();
            out.println(id);
            String[] r = in.readLine().split("~");
            System.out.println(r[0]);
            if (r[0].equals("null")){
                //STAMPA CANZONE NON ANCORA VALUTATA

            }
            else {
                //arrotonda valore double in x.x (1 decimale)
                int i =0;
                while (i<9){
                    Double d = Double.parseDouble(r[i]);
                    String dt = String.format("%.1f",d);
                    r[i]=dt;
                    i++;
                }

            }
            URL url = getClass().getResource("song.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent ca = fxmlLoader.load();
            SongController sc = fxmlLoader.getController();
            sc.sg_nome.setText(nome);
            sc.sg_anno.setText(anno);
            sc.sg_artista.setText(autore);

            sc.val_AMZ.setText(r[0]);
            sc.val_SOL.setText(r[1]);
            sc.val_TND.setText(r[2]);
            sc.val_NOS.setText(r[3]);
            sc.val_CAL.setText(r[4]);
            sc.val_POW.setText(r[5]);
            sc.val_JOY.setText(r[6]);
            sc.val_TEN.setText(r[7]);
            sc.val_SAD.setText(r[8]);


            String rec = in.readLine();
            System.out.println("asdisda");
            System.out.println(rec);

            centralVB.getChildren().add(ca); //VISUALIZZO VALUTAZIONI CANZONE


            if (!rec.equals("-1")){
                String[] sr = rec.split("~");
                int l = sr.length/2; //numero blocchi emozione~recensione
                int i =0;
                while (i<l*2){
                    url = getClass().getResource("review.fxml");
                    fxmlLoader = new FXMLLoader(url);
                    ca = fxmlLoader.load();
                    sc = fxmlLoader.getController();
                    sr[i]=  sr[i].substring(0, sr[i].length()-2); //rimuove ultimi 2 elementi (_n)
                    sr[i] = sr[i].substring(0, 1).toUpperCase() + sr[i].substring(1); //prima lettera uppercase

                    sc.em_nome.setText(sr[i]);
                    sc.em_rec.setText(sr[i+1]);
                    centralVB.getChildren().add(ca);
                    i+=2;

                }
            }
            else {
                System.out.println("termine");
            }



            //







            //


            //REALIZZARE INTERFACCIA GRAFICA SU LIVELLI
            //CANZONE CON VALUTAZIONI
            //N RECENSIONI

        }catch (Exception e){
            System.err.println(e
            );
        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrpane.setVisible(false);
        sp = scrpane;
        accadv = accAdv;
        pbox = Pbox;
        searchT = srcTXT;
        centralVB = cVB;
    }
}
