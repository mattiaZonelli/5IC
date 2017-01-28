/**
 * Created by pool on 06/12/16.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatroomGui extends Application {

    private Stage chatStage;
    private Stage loginStage;
    private Stage registerStage;
    private AnchorPane layout;
    DBUtility db;

    @Override
    public void start(Stage primaryStage) {
        loginStage = primaryStage;
        loginStage.setTitle("Login");
        db = new DBUtility("Chat.sqlite");
        initLogin();
    }

    public void initLogin(){
        try {
            //Carico il layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("intialMenu.fxml"));
            layout = loader.load();
            Scene scene = new Scene(layout);
            loginStage.setScene(scene);
            loginStage.show();

            //Attacco il controller
            LoginController loginController = loader.getController();
            loginController.setApplication(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initRegister(){
        try {
            //Carico il layout
            registerStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            layout = loader.load();
            Scene scene = new Scene(layout);
            registerStage.setScene(scene);
            registerStage.show();

            //Attacco il controller
            RegisterController registerController = loader.getController();
            registerController.setApplication(this);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void initChat(User currentUser){
        try {
            //Carico il layout
            chatStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chatroomGUI.fxml"));
            layout = loader.load();
            Scene scene = new Scene(layout);
            chatStage.setScene(scene);
            chatStage.show();
            //Attacco il controller
            ChatController chatController = loader.getController();
            chatController.setApplication(this);
            chatController.getClient().setConnectedUser(currentUser);
            chatStage.setOnCloseRequest(event -> {
                chatController.getClient().setStopped();
            });
            chatController.getClient().sendMessage(""+(char)1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Stage getChatStage(){
        return chatStage;
    }

    public synchronized DBUtility getDb() {
        return db;
    }

    public void setChatStage(Stage chatStage) {
        this.chatStage = chatStage;
    }

    public Stage getLoginStage() {
        return loginStage;
    }

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    public Stage getRegisterStage() {
        return registerStage;
    }

    public void setRegisterStage(Stage registerStage) {
        this.registerStage = registerStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
