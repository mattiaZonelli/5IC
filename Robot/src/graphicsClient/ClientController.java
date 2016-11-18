/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsClient;

import robot.*;
import java.net.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class ClientController implements Initializable {

    //POPUP IP
    @FXML
    private DialogPane popUp;
    @FXML
    private Label title;
    @FXML
    private Label label;
    @FXML
    public TextField ip;
    @FXML
    private Label error;

    //Interfaccia client
    @FXML
    private TextArea textArea;
    @FXML
    private TextField inputText;
    @FXML
    private AnchorPane pane;

    Client CLIENT;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ip.setText("127.0.0.1");
        error.setVisible(false);
    }

    @FXML
    private void sendMessageInputText(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendMessage();
        }
    }

    private void sendMessage() {
        CLIENT.sendMessage(inputText.getText());
        inputText.clear();
    }

    @FXML
    private void checkIP(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            CLIENT = new Client(textArea,this);
            if (CLIENT.requestServerAddress(ip.getText())) {
                showPopUp(false);
                CLIENT.start();
            }
        }
    }

    public void showPopUp(boolean b) {
        title.setVisible(b);
        label.setVisible(b);
        popUp.setVisible(b);
        ip.setVisible(b);
        error.setVisible(b);
    }

}
