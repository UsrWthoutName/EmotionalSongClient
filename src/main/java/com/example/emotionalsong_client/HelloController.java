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

import java.io.*;
import java.net.InetAddress;
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
    //LOCALHOST:5000 INDIRIZZO STANDARD -> se non esiste init.txt
    static String IP = "localhost";
    static int PORT= 5000;
    static boolean LgOp = false;    //Schermata login aperta
    static boolean CPOP = false;    //Schermata creazione playlist aperta
    static boolean StOp = false;    //Schermata impostazioni aperta
    static boolean vlOp = false;    //Schermata valutazioni aperta
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
        pbox.getChildren().clear();
        sp.setVisible(true);
        accadv.setVisible(false);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pbox.setVisible(true);


        URL caurl = getClass().getResource("opCP.fxml");
        Parent ca = null;
        try {
            FXMLLoader f = new FXMLLoader(caurl);
            ca =f.load();
            PlaylistBlockController controller = f.getController();
        } catch (IOException e) {
            System.out.println(e);
        }
        pbox.getChildren().add(ca);
        System.out.println(userID);

        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println("P");
            in.readLine();
            out.println(HelloController.userID);

            int numEl = Integer.parseInt(in.readLine());
            if (numEl!=0){
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
                sc.load(centralVB, info[i+0], info[i+1], info[i+2], info[i+3]);
                i+=4;
                j++;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    //APRE UNA CANZONE E VISUALIZZA INFORMAZIONI, MEDIA VALUTAZIONI E RECENSIONI
    public void loadSong(String id, String nome, String autore, String anno){
        SongController sc = new SongController();
        sc.load(id, nome, autore, anno);
    }
    public void openPlaylist(String id, String nomePlaylist, String numerocanzoni){
        PlaylistController pc = new PlaylistController();
        pc.load(id, nomePlaylist, numerocanzoni);
    }
    public void creaPlaylist(){
        PlaylistCreationController pc = new PlaylistCreationController();
        pc.load();
    }
    public void settings() throws IOException {
        SettingsController sc = new SettingsController();
        sc.load();
    }
    public void valuta(String idplaylist){
        ValController vc = new ValController();
        vc.load(idplaylist);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrpane.setVisible(false);
        sp = scrpane;
        accadv = accAdv;
        pbox = Pbox;
        searchT = srcTXT;
        centralVB = cVB;

        try {
            BufferedReader br = new BufferedReader(new FileReader("init.txt"));
            HelloController.IP =  br.readLine();
            HelloController.PORT = Integer.parseInt(br.readLine());

        }catch (Exception e){}
    }
}
