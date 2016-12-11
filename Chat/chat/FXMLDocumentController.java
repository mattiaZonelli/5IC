package chat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class FXMLDocumentController {

    private Client client;
    private ConnectionUser user;
    private String name;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text txtTitle;

    @FXML
    private TextField txtConnect;

    @FXML
    private Button btnConnect;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtSend;

    @FXML
    private Button btnSend;

    @FXML
    void doConnect(ActionEvent event) {
        user=new ConnectionUser(txtConnect.getText());
        user.sendUser();
        name=txtConnect.getText();
        txtArea.appendText("connected as "+name+"\n");
        txtSend.setDisable(false);
        btnSend.setDisable(false);
        txtConnect.setDisable(true);
        btnConnect.setDisable(true);
        client=new Client(this);
        client.start();
        
    }

    @FXML
    void doSend(ActionEvent event) {
        String msg=name+": "+txtSend.getText();
        txtSend.setText("");
        client.send(msg);
    }

    
    @FXML
    void initialize() {
        assert txtTitle != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtConnect != null : "fx:id=\"txtConnect\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert btnConnect != null : "fx:id=\"btnConnect\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert txtSend != null : "fx:id=\"txtSend\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        assert btnSend != null : "fx:id=\"btnSend\" was not injected: check your FXML file 'FXMLDocument.fxml'.";
        txtSend.setDisable(true);
        btnSend.setDisable(true);
    }

    public TextArea getTxtArea() {
        return txtArea;
    }
    
}

