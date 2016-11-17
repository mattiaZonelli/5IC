/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. This software is created by Swapnil Paul
 */
package robotgraphics;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**

 @author Swapnil Paul
 */
public class FXMLServerController implements Initializable {

	@FXML
	private TextArea serverDisplay;

	@FXML
	private TextField ipDisplay;

	@FXML
	private Button startServerBtn;

	@FXML
	private Button startClientBtn;

	@FXML
	void handleStartServerBtn(ActionEvent event) throws IOException {

		Server s = new Server(this);
		s.start();

		startServerBtn.setDisable(true);
	}

	@FXML
	void handleStartClientBtn(ActionEvent event) throws IOException {
		System.out.println("Client avviato");

		Parent root = FXMLLoader.load(getClass().getResource("ClientFXMLDoc.fxml"));
		Stage stage = new Stage();
		Scene scene = new Scene(root);

		stage.setScene(scene);
                stage.setTitle("Client numero");
		stage.show();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
            try {
                // TODO
                serverDisplay.setText("");
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

    /**
     *
     * @param s
     */
    public void print(String s) {
		serverDisplay.appendText(s + "\n");
	}

}
