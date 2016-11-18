/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicsServer;

import java.net.*;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import robot.*;

public class ServerController implements Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private Button start;
    @FXML
    private Button close;
    @FXML
    private Pane statusPane;
    
    private Server SERVER;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        close.setDisable(true);
    }
    
    @FXML
    private void startServer(MouseEvent event) {
        SERVER = new Server(textArea,this);
        SERVER.start();
        start.setDisable(true);
        close.setDisable(false);
        statusPane.setStyle("-fx-background-color: #1bff1b;");
    }

    @FXML
    private void closeServer(MouseEvent event) {
        SERVER.stopServer();
        start.setDisable(false);
        close.setDisable(true);
        statusPane.setStyle("-fx-background-color: #ff0000;");
    }


}
