package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    static Socket s;
    static PrintWriter out;
    static BufferedReader in;
    static String[] canzoni;
    static int i;
    static String r;    //contiene canzoni e valutazioni da passare a server
    public void load(String idplaylist){
        //esegue richiesta canzoni in playlist e gestisce la creazione delle finestre di valutazione
        if (!HelloController.vlOp){
            HelloController.vlOp = true;
            r = idplaylist+"~";
            try {
                s = new Socket(HelloController.IP, HelloController.PORT);
                out = new PrintWriter(s.getOutputStream(), true);
                in =new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println("V");
                in.readLine();
                out.println(idplaylist);
                String c = in.readLine();
                System.out.println("canzoni  "+c);
                canzoni = c.split("~");
                i = 0;
                System.out.println("lunghezza "+c.length());
                valWindow();
            }catch (Exception e){
                System.out.println(e);

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
            stage.initStyle(StageStyle.UNDECORATED);
            ValController vc = fxmlLoader.getController();
            vc.val_cont.setText(i/2+1+"/"+canzoni.length/2);
            vc.val_nome.setText(nome);
            vc.val_id.setText(id);
            stage.show();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void continua(ActionEvent ae){
        boolean valid = true;
        //System.out.println(AmazementGroup.getSelectedToggle().isSelected());
        //per verifica selezione
        if (AmazementGroup.getSelectedToggle()==null||SolemnityGroup.getSelectedToggle()==null||TendernessGroup.getSelectedToggle()==null || NostalgiaGroup.getSelectedToggle()==null||CalmnessGroup.getSelectedToggle()==null||PowerGroup.getSelectedToggle()==null||JoyGroup.getSelectedToggle()==null||TensionGroup.getSelectedToggle()==null||SadnessGroup.getSelectedToggle()==null){
            System.out.println("SELEZIONARE TUTTI I MERDA DI VALORI");
        }else {
            r =r+val_id.getText()+"~";
            r =  r+AmazementGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            System.out.println(AmT.getText());
            if (AmT.getText().length() > 256){
                System.out.println("Amazement troppo lungo");
                valid=false;
            }else if ( AmT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+AmT.getText()+"~";
            }

            r =  r+SolemnityGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (SoT.getText().length() > 256){
                System.out.println("Solemnity troppo lungo");
                valid=false;
            }else if ( SoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+SoT.getText()+"~";
            }

            r =  r+TendernessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (TeT.getText().length() > 256){
                System.out.println("Tenderness troppo lungo");
                valid=false;
            }else if ( TeT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+TeT.getText()+"~";
            }
            r =  r+NostalgiaGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (NoT.getText().length() > 256){
                System.out.println("Nostalgia troppo lungo");
                valid=false;
            }else if ( NoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+NoT.getText()+"~";
            }

            r =  r+CalmnessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (CaT.getText().length() > 256){
                System.out.println("Calmness troppo lungo");
                valid=false;
            }else if ( CaT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+CaT.getText()+"~";
            }

            r =  r+PowerGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (PoT.getText().length() > 256){
                System.out.println("Power troppo lungo");
                valid=false;
            }else if ( PoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+PoT.getText()+"~";
            }

            r =  r+JoyGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (JoT.getText().length() > 256){
                System.out.println("Joy troppo lungo");
                valid=false;
            }else if ( JoT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+JoT.getText()+"~";
            }

            r =  r+TensionGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (TnT.getText().length() > 256){
                System.out.println("Tension troppo lungo");
                valid=false;
            }else if ( TnT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+TnT.getText()+"~";
            }

            r =  r+SadnessGroup.getSelectedToggle().selectedProperty().getBean().toString().substring(16,17)+"~";
            if (SaT.getText().length() > 256){
                System.out.println("Sadness troppo lungo");
                valid=false;
            }else if ( SaT.getText().isBlank()){
                r=r+"null~";
            }else{
                r=r+SaT.getText()+"~";
            }

            // R = IDCANZONE ~ VALUTAZIONE ~ RECENSIONE ~ ... ~ IDCANZONE ~ VALUTAZIONE ~ RECENSIONE ~ ...
            // ogni canzone occupa 1 (id) + 9 (valutazioni) + 9 (recensioni) = 19 blocchi
            // R va preceduto da id playlist -> IDPLAYLIST ~ BLOCCO CANZONE1 ~ BLOCCO CANZONE2 ~ ...
            // dimensione R = 1 + (numero canzoni * 9)
            System.out.println(r);
            if (valid) {
                i += 2;
                if (i != canzoni.length) {

                    esci(ae);
                    valWindow();
                } else {
                    send(ae);
                    esci(ae);
                }
            }
            else {
                System.out.println("input non valido");
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
        }catch (Exception e){}
    }
}
