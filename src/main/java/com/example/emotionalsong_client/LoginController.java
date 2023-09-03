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
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Questa classe gestisce la finestra di login dell'applicazione.
 */
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


    /**
     * Metodo per caricare la finestra di login.
     *
     * @param b Il pulsante di accesso
     * @param t Il campo di testo dell'username
     * @param c Un'istanza di HelloController
     * @throws IOException Eccezione lanciata in caso di errore di caricamento
     */
    public void load(Button b, Text t,HelloController c) throws IOException {
        accediB = b;
        usernameTxt = t;
        hc = c;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        if (!HelloController.LgOp){
            HelloController.LgOp= true;
            Parent r1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setOnCloseRequest(WindowEvent::consume);
            stage.setResizable(false);
            stage.setScene(new Scene(r1));
            stage.show();
        }
    }
    /**
     * Metodo per gestire il pulsante "Accedi".
     *
     * @param ae L'evento di azione generato dal pulsante
     */
    public void Accedi(ActionEvent ae)  {

        //CONTROLLARE PRESENZA CAMPI

        errText.setText("");
        String cred = credTXTFD.getText();
        String pw = pwTXTFD.getText();
        if (!cred.equals("") || !pw.equals("")){
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
                        HelloController.userID = idname[1];
                        accediB.setVisible(false);
                        usernameTxt.setText(idname[0]);
                        usernameTxt.setVisible(true);
                        in.close();
                        out.close();
                        s.close();
                        close(ae);
                        hc.loadPlaylists();

                    }else{
                        errText.setText("credenziali errate");
                    }

                }catch (IOException e){
                    errText.setText("impossibile connettersi al server");
                }
            }else{
                errText.setText("il valore ~ non può essere presente in username o password");
            }

        }else {
            errText.setText("i valori non possono essere vuoti");
        }
    }
    /**
     * Metodo per gestire il pulsante "Registrati".
     *
     * @param ae L'evento di azione generato dal pulsante
     * @throws IOException Eccezione lanciata in caso di errore di registrazione
     */
    public void Registrati(ActionEvent ae) throws IOException {
        hc.registra(accediB, usernameTxt, hc);
        close(ae);
        HelloController.LgOp=true;
    }
    /**
     * Metodo per chiudere la finestra di login.
     *
     * @param ae L'evento di azione generato dalla chiusura della finestra
     */
    public void close(ActionEvent ae){
        Node n =(Node)ae.getSource();
        Stage s = (Stage) n.getScene().getWindow();
        s.close();
        HelloController.LgOp = false;
    }
}
