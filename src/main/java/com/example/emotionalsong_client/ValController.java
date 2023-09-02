package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ValController {
    @FXML
    private ToggleGroup AmazementGroup;
    @FXML
    private TextField AmT;
    @FXML
    private ToggleGroup SolemnityGroup;
    @FXML
    private TextField SoT;
    @FXML
    private ToggleGroup TendernessGroup;
    @FXML
    private TextField TeT;
    @FXML
    private ToggleGroup NostalgiaGroup;
    @FXML
    private TextField NoT;
    @FXML
    private ToggleGroup CalmnessGroup;
    @FXML
    private TextField CaT;
    @FXML
    private ToggleGroup PowerGroup;
    @FXML
    private TextField PoT;
    @FXML
    private ToggleGroup JoyGroup;
    @FXML
    private TextField JoT;
    @FXML
    private ToggleGroup TensionGroup;
    @FXML
    private TextField TnT;
    @FXML
    private ToggleGroup SadnessGroup;
    @FXML
    private TextField SaT;
    @FXML
    private Text val_cont;
    @FXML
    private Text val_nome;
    @FXML
    private Text val_id;
    @FXML
    private Text val_err;

    static Socket s;
    static PrintWriter out;
    static BufferedReader in;
    static String[] canzoni;
    static int i;
    static String idplaylist;
    static String nomeplaylist ="";
    static String numcanzoni = "";
    static String r;    //contiene canzoni e valutazioni da passare a server
    public void load(String id){
        //esegue richiesta canzoni in playlist e gestisce la creazione delle finestre di valutazione
        idplaylist = id;
        if (!HelloController.vlOp){
            r = idplaylist+"~";
            try {
                s = new Socket(HelloController.IP, HelloController.PORT);
                out = new PrintWriter(s.getOutputStream(), true);
                in =new BufferedReader(new InputStreamReader(s.getInputStream()));

                HelloController.vlOp = true;


                out.println("V");
                in.readLine();
                out.println(idplaylist);
                String c = in.readLine();
                canzoni = c.split("~");
                i = 0;
                valWindow();
            }catch (Exception e){
                HelloController.vlOp = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("errore di connessione");
                alert.setContentText("impossibile connettersi al server, verificare la connessione e riprovare");
                alert.showAndWait();
            }
        }
    }
    public void valWindow(){
        String id = canzoni[i];
        String nome = canzoni[i+1];
        //crea finestra valutazione


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ValutaCanzone.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            if (nome.length()>17){
                nome = nome.substring(0,16);
                nome = nome + "...";

            }

            stage.setOnCloseRequest(WindowEvent::consume);
            ValController vc = fxmlLoader.getController();
            vc.val_cont.setText(i/2+1+"/"+canzoni.length/2);
            vc.val_nome.setText(nome);
            vc.val_id.setText(id);
            stage.show();
        }catch (Exception e){}
    }
    public void continua(ActionEvent ae){
        boolean valid = true;
        if (AmazementGroup.getSelectedToggle()==null||SolemnityGroup.getSelectedToggle()==null||TendernessGroup.getSelectedToggle()==null || NostalgiaGroup.getSelectedToggle()==null||CalmnessGroup.getSelectedToggle()==null||PowerGroup.getSelectedToggle()==null||JoyGroup.getSelectedToggle()==null||TensionGroup.getSelectedToggle()==null||SadnessGroup.getSelectedToggle()==null){
            val_err.setText("Valutare tutte le emozioni prima di continuare");
        }else {
            r =r+val_id.getText()+"~";
            r =  r+AmazementGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (AmT.getText().length() > 256){
                val_err.setText("stringa Amazement troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            } else if ( AmT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+AmT.getText()+"~";
            }

            r =  r+SolemnityGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (SoT.getText().length() > 256){
                val_err.setText("stringa Solemnity troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( SoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+SoT.getText()+"~";
            }

            r =  r+TendernessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (TeT.getText().length() > 256){
                val_err.setText("stringa Tenderness troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( TeT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+TeT.getText()+"~";
            }
            r =  r+NostalgiaGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (NoT.getText().length() > 256){
                val_err.setText("stringa Nostalgia troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( NoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+NoT.getText()+"~";
            }

            r =  r+CalmnessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (CaT.getText().length() > 256){
                val_err.setText("stringa Calmness troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( CaT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+CaT.getText()+"~";
            }

            r =  r+PowerGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (PoT.getText().length() > 256){
                val_err.setText("stringa Power troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( PoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+PoT.getText()+"~";
            }

            r =  r+JoyGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (JoT.getText().length() > 256){
                val_err.setText("stringa Joy troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( JoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+JoT.getText()+"~";
            }

            r =  r+TensionGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (TnT.getText().length() > 256){
                val_err.setText("stringa Tension troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            }else if ( TnT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+TnT.getText()+"~";
            }

            r =  r+SadnessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (SaT.getText().length() > 256){
                val_err.setText("stringa Sadness troppo lunga");
                valid=false;
            } else if (AmT.getText().contains("~")) {
                val_err.setText("Le stringhe non possono contenere il carattere ~");
            } else if ( SaT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+SaT.getText()+"~";
            }

            // R = IDCANZONE ~ VALUTAZIONE ~ RECENSIONE ~ ... ~ IDCANZONE ~ VALUTAZIONE ~ RECENSIONE ~ ...
            // ogni canzone occupa 1 (id) + 9 (valutazioni) + 9 (recensioni) = 19 blocchi
            // R va preceduto da id playlist -> IDPLAYLIST ~ BLOCCO CANZONE1 ~ BLOCCO CANZONE2 ~ ...
            // dimensione R = 1 + (numero canzoni * 9)
            if (valid) {
                i += 2;
                if (i != canzoni.length) {

                    esci(ae);
                    valWindow();
                } else {
                    send(ae);
                    HelloController.hc.openPlaylist(idplaylist, nomeplaylist, numcanzoni);
                    esci(ae);
                }
            }
        }
    }
    public void esci(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
        HelloController.vlOp = false;
    }
    public void send(ActionEvent ae){
        r =  r.substring(0, r.length()-1);
        out.println(r);
        try {
            s = new Socket(HelloController.IP, HelloController.PORT);
            out = new PrintWriter(s.getOutputStream(), true);
            in =new BufferedReader(new InputStreamReader(s.getInputStream()));

            out.println("Vb");
            in.readLine();
            out.println(r);
            String[] res = in.readLine().split("~");
            nomeplaylist = res[0];
            numcanzoni = res[1];

        }catch (Exception e){
            val_err.setText("impossibile connettersi al server");
        }
    }
}
