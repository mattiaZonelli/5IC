package chatroom;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import services.Client;

/**
 *
 * @author Swapnil Paul
 */
public class FXMLClientController implements Initializable {

    @FXML
    private TextField userDisplay;

    @FXML
    private TextArea clientDisplay;
    private Label userExist;

    //private Text t;
    private Client c;
    private String userName;
    private String labelText = "";

    static int n = 0;
    Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = new Client(this);
        c.start();

        int check = 0;
        while (check < 4) {
            check = loginInterface();
        }
    }

    /**
     * @param s
     */
    public void print(String s) {
        clientDisplay.appendText(s + "\n");
    }

    /**
     *
     * @param addUser
     */
    public void printAddUserInfo(String addUser) {
        labelText = addUser;
    }

    @FXML
    private void keyPressedUser(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !userDisplay.getText().trim().isEmpty()) {
            if (userDisplay.getText() != "\n" || !userDisplay.getText().trim().isEmpty()) {
                //clientDisplay.setStyle("-fx-text-fill: #2473f2;");
                //clientDisplay.appendText(userDisplay.getText() + "\n");
                c.sendMsg(userName + ":  " + userDisplay.getText());
                try {

                    if (userDisplay.getText().equalsIgnoreCase("close") || userDisplay.getText().equalsIgnoreCase("chiudi")) {
                        Thread.sleep(500);
                        System.exit(1);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
                userDisplay.clear();
            }
        }
    }

    /**
     *
     * @return
     */
    public int loginInterface() {
        userExist = new Label("");

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("You have to login for access the Chatroom!");

        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Close", ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);
        Button btnReg = new Button("Add new account");

        // Creazione username e password campi
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField ipServer = new TextField("localhost");
        ipServer.setPromptText("Ip Server");
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Ip Server:"), 0, 0);
        grid.add(ipServer, 1, 0);
        grid.add(new Label("Username:"), 0, 1);
        grid.add(username, 1, 1);
        grid.add(new Label("Password:"), 0, 2);
        grid.add(password, 1, 2);
        grid.add(userExist, 3, 2);
        grid.add(btnReg, 3, 1);

        //  HANDLE REGISTER BUTTON
        btnReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 userExist.setText("Loading...");
                try {
                    c.setHost(ipServer.getText());
                    c.addUser(username.getText(), password.getText(), userExist);
                    Thread.sleep(1000);
                    String infoMsg = c.infoMsg();
                    userExist.setText(infoMsg);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLClientController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        // Enable/Disable dei login button, dipende se il userField è vuoto o meno
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // validazione
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        ipServer.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> username.requestFocus());
        Platform.runLater(() -> ipServer.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            } else if (dialogButton == cancelButtonType) {
                System.exit(1);
            }
            return null;
        });

        n++;
        if (n > 3) {
            System.exit(1);
        }

        Optional<Pair<String, String>> result = dialog.showAndWait();

        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            c.setHost(ipServer.getText());
            c.loginVerification(username.getText(), password.getText());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println(c.loginStatus());
            if (c.loginStatus()) {
                userName = username.getText();
                return 10;
            } else {
                return n;
            }
        }
        return n;
    }
}
