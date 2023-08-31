package com.example.emotionalsong_client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {
    @FXML
    TextField credTXTFD;
    @FXML
    TextField pwTXTFD;
    @FXML
    Text errText;



    static private Button accediB;
    static private Text usernameTxt;
    static private HelloController hc;


    public void load(Button b, Text t,HelloController c) throws IOException {
        accediB = b;
        usernameTxt = t;
        hc = c;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        if (!HelloController.LgOp){
            HelloController.LgOp= true;
            Parent r1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(r1));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
    }
    public void Accedi(ActionEvent ae)  {

        //CONTROLLARE PRESENZA CAMPI

        errText.setText("");
        String cred = credTXTFD.getText();
        String pw = pwTXTFD.getText();
        if (!cred.contains("~")&&!pw.contains("~")){
            try {
                Socket s = new Socket(HelloController.IP, HelloController.PORT);
                PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                BufferedReader in =new BufferedReader(new InputStreamReader(s.getInputStream()));

                out.println("L");
                in.readLine();
                out.println(cred+"~"+pw);

                //SERVER RITORNA -1 SE NON TROVATO, ALTRIMENTI ID UTENTE E USERNAME IN FORMATO "IDUTENTE~USERNAME"
                String st = in.readLine();
                if (!st.equals("-1")){
                    String[] idname = st.split("~");
                    System.out.println("Accesso eseguito");
                    HelloController.userID = idname[1];
                    accediB.setVisible(false);
                    usernameTxt.setText(idname[0]);
                    usernameTxt.setVisible(true);
                    in.close();
                    out.close();
                    s.close();
                    close(ae);
                    hc.loadPlaylists();
                    System.out.println("caricamento playlist");

                }else{
                    errText.setText("credenziali errate");
                }

            }catch (IOException e){
                errText.setText("impossibile connettersi al server");
            }
        }else{
            errText.setText("il valore ~ non può essere presente in username o password");
        }


    }
    public void Registrati(ActionEvent ae) throws IOException {
        hc.registra(accediB, usernameTxt, hc);
        close(ae);
        HelloController.LgOp=true;
    }
    public void close(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
        HelloController.LgOp = false;
    }
}
