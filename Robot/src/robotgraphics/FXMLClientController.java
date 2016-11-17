/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. This software is created by Swapnil Paul
 */
package robotgraphics;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

/**
 *
 * @author Swapnil Paul
 */
public class FXMLClientController implements Initializable {

    @FXML
    private TextField userDisplay;

    @FXML
    private TextArea clientDisplay;

    private Client c;
    private Text t;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // TODO
            clientDisplay.setText("");
            Thread.sleep(500);
            c = new Client(this);
            c.start();
            t = new Text();
            t.setStyle("-fx-text-fill: #2473f2;");
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param s
     */
    public void print(String s) {
        t.setText(s);
        clientDisplay.appendText(s + "\n");
    }

    @FXML
    private void keyPressedUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !userDisplay.getText().trim().isEmpty()) {
            if (userDisplay.getText() != "\n" || !userDisplay.getText().trim().isEmpty()) {
                //clientDisplay.setStyle("-fx-text-fill: #2473f2;");
                clientDisplay.appendText(userDisplay.getText() + "\n");
                c.sendMessage(userDisplay.getText());
                userDisplay.clear();
            }
        }
    }
}
