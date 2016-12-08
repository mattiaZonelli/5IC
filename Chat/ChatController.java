/**
 * Created by pool on 06/12/16.
 */

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatController {

    ChatroomGui application;
    Client client;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea messagesBox;

    @FXML
    private TextField toSendArea;

    @FXML
    private Button sendButton;

    @FXML
    private ListView<Object> userList;

    @FXML
    void initialize() {
        assert messagesBox != null : "fx:id=\"messagesBox\" was not injected: check your FXML file 'chatroomGUI.fxml'.";
        assert toSendArea != null : "fx:id=\"toSendArea\" was not injected: check your FXML file 'chatroomGUI.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'chatroomGUI.fxml'.";
        assert userList != null : "fx:id=\"userList\" was not injected: check your FXML file 'chatroomGUI.fxml'.";
        client = new Client(this);
        client.start();
        sendButton.setOnAction((event) -> {
            String toSend = toSendArea.getText();
            client.sendMessage(toSend);
            toSendArea.setText("");
        });
        toSendArea.setOnAction(event -> {
            String toSend = toSendArea.getText();
            client.sendMessage(toSend);
            toSendArea.setText("");
        });

    }

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public TextArea getMessagesBox() {
        return messagesBox;
    }

    public void setMessagesBox(TextArea messagesBox) {
        this.messagesBox = messagesBox;
    }

    public TextField getToSendArea() {
        return toSendArea;
    }

    public void setToSendArea(TextField toSendArea) {
        this.toSendArea = toSendArea;
    }

    public Button getSendButton() {
        return sendButton;
    }

    public void setSendButton(Button sendButton) {
        this.sendButton = sendButton;
    }

    public ListView<Object> getUserList() {
        return userList;
    }

    public ChatroomGui getApplication() {
        return application;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setApplication(ChatroomGui application){
        this.application = application;
    }
}
