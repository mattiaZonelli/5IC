import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;

public class LoginController {

    ChatroomGui application;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField usrname;

    @FXML
    private PasswordField passwd;

    @FXML
    private Button loginBtn;

    @FXML
    private Text nckError;

    @FXML
    private Text passwdError;

    @FXML
    private Button register;

    @FXML
    void initialize() {
        assert usrname != null : "fx:id=\"usrname\" was not injected: check your FXML file 'intialMenu.fxml'.";
        assert passwd != null : "fx:id=\"passwd\" was not injected: check your FXML file 'intialMenu.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'intialMenu.fxml'.";
        assert nckError != null : "fx:id=\"nckError\" was not injected: check your FXML file 'intialMenu.fxml'.";
        assert passwdError != null : "fx:id=\"passwdError\" was not injected: check your FXML file 'intialMenu.fxml'.";
        assert register != null : "fx:id=\"register\" was not injected: check your FXML file 'intialMenu.fxml'.";
        loginBtn.setOnAction(event -> {
            boolean nameCorrect = application.getDb().isNameRegistered(usrname.getText());
            boolean passwdCorrect = application.getDb().isPasswordCorrect(passwd.getText());
            nckError.setText((nameCorrect)?"":"Nome errato!");
            passwdError.setText((passwdCorrect)?"":"Password errata");
            if(nameCorrect && passwdCorrect)
                getApplication().initChat(getApplication().getDb().getUser(usrname.getText()));
        });

        passwd.setOnAction(loginBtn.getOnAction());

        register.setOnAction(event -> {
            getApplication().initRegister();
        });
    }

    public ChatroomGui getApplication() {
        return application;
    }

    public void setApplication(ChatroomGui application) {
        this.application = application;
    }
}
