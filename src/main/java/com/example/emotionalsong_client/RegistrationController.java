package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RegistrationController {

    @FXML
    TextField nmTF;
    @FXML
    TextField snmTF;
    @FXML
    TextField emTF;
    @FXML
    TextField unTF;
    @FXML
    TextField pwTF;
    @FXML
    TextField cfTF;
    @FXML
    TextField adTF;
    @FXML
    DatePicker ddnDP;
    @FXML
    Text errTXT;


    static private HelloController hc;


    public void load(HelloController c) throws IOException {
        hc = c;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registration.fxml"));
        Parent r1 = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setOnCloseRequest(WindowEvent::consume);
        stage.setScene(new Scene(r1));
        stage.show();
    }
    public void accedi(ActionEvent ae) throws IOException{

        close(ae);
        HelloController.LgOp = false;
        hc.login();
    }


    public void register(ActionEvent ae){
        errTXT.setText("");
        if (nmTF.getText().contains("~") || snmTF.getText().contains("~") || emTF.getText().contains("~") || unTF.getText().contains("~") || pwTF.getText().contains("~") || cfTF.getText().contains("~") || adTF.getText().contains("~") ){
            errTXT.setText("Il valore ~ non può essere presente in nessun campo");
        }else {
            if (nmTF.getText().length()!=0||snmTF.getText().length()!=0||emTF.getText().length()!=0||unTF.getText().length()!=0||pwTF.getText().length()!=0||cfTF.getText().length()!=0||adTF.getText().length()!=0){
                if (ncTest(nmTF.getText()) && ncTest(snmTF.getText()) && emailTest(emTF.getText())  && codfisTest(cfTF.getText())){
                    try {
                        String d = ddnDP.getValue().toString();
                        try {

                            //IN CONTROLLO DATI USERNAME NON PUO AVERE CARATTERE @, OBBLIGATORIO IN MAIL
                            //EMAIL DEVE CONTENERE @ E .
                            //PASSWORD DEVE CONTENERE Aa1! DOVE SIMBOLO != DA ~ o \
                            //COD FISCALE DEVE RISPETTARE IL FORMATO AAAAAA 11 A 11 A 111 X
                            //DDN > 1899
                            //EMAIL UNIVOCA
                            //USERNAME UNIVOCO

                            Socket s = new Socket(HelloController.IP, HelloController.PORT);
                            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                            BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));
                            out.println("R");
                            in.readLine();
                            out.println(nmTF.getText()+"~"+snmTF.getText()+"~"+emTF.getText()+"~"+unTF.getText()+"~"+pwTF.getText()+"~"+cfTF.getText()+"~"+adTF.getText()+"~"+ddnDP.getValue());
                            String user = in.readLine();
                            if (!user.equals("-1")){
                                HelloController.userID = user;
                                hc.usernameTag.setText(unTF.getText());
                                hc.usernameTag.setVisible(true);
                                hc.accediBTN.setVisible(false);
                                close(ae);
                                HelloController.LgOp = false;
                                hc.loadPlaylists();

                            }
                            else {
                                errTXT.setText("email o username già in uso");
                            }
                        }
                        catch (IOException e){
                            errTXT.setText("impossibile connettersi al server");
                        }
                    }catch (NullPointerException e){            //////////////CONTROLLO DATA
                        errTXT.setText("inserire una data corretta");
                    }
                }else {
                    errTXT.setText("I valori inseriti non sono validi (vedi DocumentazioneUtente.pdf pag1)");
                }

            }else {
                errTXT.setText("i campi non possono essere vuoti");
            }
        }
    }

    private boolean ncTest(String s){   //ritorna true se non contiene numeri
        for (int i=0; i<s.length(); i++){
            if (Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
    }
    private boolean emailTest(String s){
        if (!s.contains(".")){
            return false;
        } else if (!s.contains("@")) {
            return false;
        }
        return true;
    }

    private boolean codfisTest(String s){
        int i = 0;

        if (s.length()!=16){
            return false;
        }

        while (i<6){
            if (!ncTest(String.valueOf(s.charAt(i)))){
                return false;
            }
            i++;
        }

        int j = i+2;
        while (i<j){
            if (ncTest(String.valueOf(s.charAt(i)))){
                return false;
            }
            i++;
        }
        if (!ncTest(String.valueOf(s.charAt(i)))){
            return false;
        }
        i++;
        j = i+2;
        while (i<j){
            if (ncTest(String.valueOf(s.charAt(i)))){
                return false;
            }
            i++;
        }
        if (!ncTest(String.valueOf(s.charAt(i)))){
            return false;
        }
        i++;
        j=i+2;
        while (i<j){
            if (ncTest(String.valueOf(s.charAt(i)))){
                return false;
            }
            i++;
        }
        return true;
    }

    public void close(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
    }
}
