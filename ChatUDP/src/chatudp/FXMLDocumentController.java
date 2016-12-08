/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatudp;

/**
 *
 * @author MATTEO
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLDocumentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnConnect;

    @FXML
    private TextArea txtFieldGrande;

    @FXML
    private TextArea txtFieldInput;

    @FXML
    private Button btnSend;

    @FXML
    private TextField fieldNickname;

    public ClientUDP c;

    @FXML
    void sendMessage(ActionEvent event) {
        System.out.println("Cliccato il bottone sendMessage");
        c.send(txtFieldInput.getText());

    }

    @FXML
    void btnConnetti(ActionEvent event) {
        System.out.println("Cliccato il bottone connetti.");
        txtFieldGrande.appendText("Richiesta di connessione con Nickname: " + fieldNickname.getText() + "\n");

    }

    public void setTxtFieldGrande(String s) {
        txtFieldGrande.appendText(s + "\n");
    }

    public void setTxtFieldInput(String s) {
        txtFieldInput.appendText(s + "\n");
    }

    public void setFieldNickname(String s) {
        fieldNickname.appendText(s + "\n");
    }

    @FXML
    void initialize() throws InterruptedException {
        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtFieldGrande != null : "fx:id=\"txtFieldGrande\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtFieldInput != null : "fx:id=\"txtFieldInput\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'FXMLDocument.fxml'.";

        c = new ClientUDP(this);
        
        c.run();

    }
}
