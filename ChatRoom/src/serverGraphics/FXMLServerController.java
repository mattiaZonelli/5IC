package serverGraphics;

import dbutil.ConnectionDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.Server;

/**
 *
 * @author Swapnil Paul
 */
public class FXMLServerController implements Initializable {
    
    @FXML
    private Button startBtn;
    
    @FXML
    private Button closerBtn;
    
    @FXML
    private Label label;
    
    private Server s;
    
    @FXML
    void startServer(ActionEvent event) {
        s = new Server();
        s.start();
        label.setText("    UDP Server is runnig!");
        startBtn.setDisable(true);
    }
    
    @FXML
    void closeServer(ActionEvent event) {
       // s.clientSockClose();
       // ConnectionDB.closeConnection();
        System.exit(1);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
