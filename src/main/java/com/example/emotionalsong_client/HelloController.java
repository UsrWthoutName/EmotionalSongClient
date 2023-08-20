package com.example.emotionalsong_client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
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
    private Text srcTXT;



    public static HelloController hc;
    static String IP = "localhost";
    static int PORT= 5000;
    static boolean LgOp = false;
    static String userID;



    static public ScrollPane sp;
    static public VBox pbox;
    static public Text accadv;
     public Text searchT;


    //METODO LOAD CARICA INTERFACCIA E RICEVE ISTANZA DI HELLOCONTROLLER
    public void load(Stage stage, HelloController c) throws IOException {
        //hc = c;
        FXMLLoader fxmlLoader = new FXMLLoader(EmotionalSong.class.getResource("application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1058, 734);
        stage.setTitle("EmotionalSong");
        stage.setScene(scene);
        hc=fxmlLoader.getController();
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
            String playistsMsg = in.readLine();
            s.close();

            String[] playlist = playistsMsg.split("~");
            //0 = ID
            //1 = NOME PLAYLIST
            //2 = NUMERO CANZONI
            //...
            int i =0;
            while (i<playlist.length){
                PlaylistController block = new PlaylistController();

                block.load(hc, pbox, playlist[i+0], playlist[i+1], playlist[i+2]);
                i+=3;
            }
        }catch (IOException e){
            //MANCATA CONNESSIONE A SERVER
            System.err.println("non va un cristo");
        }
    }
    //CARICA SINGOLA PLAYLIST NEL BLOCCO CENTRALE
    public void openPlaylist(){}
    public void cerca(){
        try {
            Socket s = new Socket(HelloController.IP, HelloController.PORT);

            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
            System.out.println("asdadad");
            System.out.println(searchT.hashCode());
            System.out.println("asdasdasd");

            out.println("S");
            in.readLine();
            out.println(hc.srcTXT.getText());
            in.readLine();
            System.out.println(in);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrpane.setVisible(false);
        sp = scrpane;
        accadv = accAdv;
        pbox = Pbox;
        searchT = srcTXT;

    }
}
