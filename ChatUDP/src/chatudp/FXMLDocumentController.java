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
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
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

    public static ClientUDP c;
    public ServerUDP s;
    protected String nickname;

    @FXML
    private Button btnServerStart;

    public ResourceBundle getResources() {
        return resources;
    }

    public URL getLocation() {
        return location;
    }

    public Button getBtnConnect() {
        return btnConnect;
    }

    public TextArea getTxtFieldGrande() {
        return txtFieldGrande;
    }

    public TextArea getTxtFieldInput() {
        return txtFieldInput;
    }

    public Button getBtnSend() {
        return btnSend;
    }

    public TextField getFieldNickname() {
        return fieldNickname;
    }

    public static ClientUDP getC() {
        return c;
    }

    public ServerUDP getS() {
        return s;
    }

    public Button getBtnServerStart() {
        return btnServerStart;
    }

    public String getNickname() {
        return nickname;
    }

    @FXML
    void btnStartServer(ActionEvent event) throws IOException {

        txtFieldGrande.appendText("Richiesta di start del server con Nickname: " + fieldNickname.getText() + "\n");
        s = new ServerUDP();
        new Thread(s).start();
        btnServerStart.setDisable(true);
    }

    @FXML
    void sendMessage(ActionEvent event) {
        System.out.println("Cliccato il bottone sendMessage");

        c.send(fieldNickname.getText() + ": " + "\"" + txtFieldInput.getText() + "\"");
        System.err.println("INVIATO: " + fieldNickname.getText() + ": " + "\"" + txtFieldInput.getText() + "\"");

    }

    @FXML
    void btnConnetti(ActionEvent event) throws IOException {
        System.out.println("Cliccato il bottone connetti.");
        nickname = fieldNickname.getText();
        c = new ClientUDP(this);
        new Thread(c).start();
        txtFieldGrande.appendText("Richiesta di start del client con Nickname: " + fieldNickname.getText() + "\n");
        //c.send(fieldNickname.getText()+" si è unito alla chatRoom");

        btnConnect.setDisable(true);

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
    void initialize() throws InterruptedException, UnknownHostException, IOException {

        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtFieldGrande != null : "fx:id=\"txtFieldGrande\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtFieldInput != null : "fx:id=\"txtFieldInput\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        System.out.println("Dentro a inizialize");

    }
}
