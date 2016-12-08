import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import javax.swing.*;

public class RegisterController {

    ChatroomGui application;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField name;

    @FXML
    private TextField srname;

    @FXML
    private TextField nckname;

    @FXML
    private PasswordField passwd;

    @FXML
    private Text nckAlreadyUsed;

    @FXML
    void initialize() {
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'register.fxml'.";
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'register.fxml'.";
        assert srname != null : "fx:id=\"srname\" was not injected: check your FXML file 'register.fxml'.";
        assert nckname != null : "fx:id=\"nckname\" was not injected: check your FXML file 'register.fxml'.";
        assert passwd != null : "fx:id=\"passwd\" was not injected: check your FXML file 'register.fxml'.";
        assert nckAlreadyUsed != null : "fx:id=\"nckAlreadyUsed\" was not injected: check your FXML file 'register.fxml'.";
        registerBtn.setOnAction(event -> {
            //controllo nickname
            if(getApplication().getDb().isNameRegistered(nckname.getText())){
                nckAlreadyUsed.setText("Il nickname è gia in uso");
            }else{
                User currentUser = getApplication().getDb().registerUser(name.getText(),srname.getText(),nckname.getText(),passwd.getText());
                if(currentUser != null){
                    getApplication().initChat(currentUser);
                }
            }
        });
    }

    public ChatroomGui getApplication() {
        return application;
    }

    public void setApplication(ChatroomGui application) {
        this.application = application;
    }
}
