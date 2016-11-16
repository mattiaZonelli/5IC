package gui;

import communication.ClientListener;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import other.Displayable;
import other.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Rikkardo on 09/11/2016.
 */
public class Client extends Application{

    private final int PORT = 2500;
    private Socket socket;
    private ClientListener listener;
    private boolean connected;
    private PrintWriter out;
    private BufferedReader in;
    private Displayable display_messages;

    @FXML
    private TextField IP_field;
    @FXML
    private Text error_field;
    @FXML
    private Text info_field;
    @FXML
    private Button connect;
    @FXML
    private TextArea messages_area;
    @FXML
    private TextField message_field;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("client_interface.fxml"));
        primaryStage.setScene(new Scene(root, 600, 420));
        primaryStage.setTitle("SimpleDialogue Client");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void connect(){
        cleanUp();
        connected = false;
        display_messages = new Displayable(messages_area, 25);
        if(connect.getText().equals("Connect")) {
            if (utility.validIp(IP_field.getText())) {
                try {
                    socket = new Socket(IP_field.getText(), PORT);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    listener = new ClientListener(this, in);
                    Thread t = new Thread(listener);
                    t.start();
                    connect.setText("Disconnect");
                    showInfo("Connected succesfully.");
                } catch (IOException e) {
                    showError("Could not reach the server.");
                }
            } else {
                showError("Not valid IP address.");
            }
        } else{
            close(true, "Connection closed succesfully", "");
            connect.setText("Connect");
            showInfo("Disconnected succesfully.");
        }
    }

    @FXML
    private void send(){
        cleanUp();
        if(connected){
            if(!message_field.getText().equals("")) {
                String message = message_field.getText();
                message_field.setText("");
                out.println("MESSAGE " + message);
                showMessage("Me: " + message);
            } else {
                showError("Can not send void messages.");
            }
        }
    }

    public void setConnected(){
        connected = true;
        out.println("READY");
    }

    public void close(boolean requested, String info, String error){
        if(requested) {
            out.println("CLOSE");
        }
        out.close();
        connected = false;
        listener.close();
        showInfo(info);
        showError(error);
        try {
            socket.close();
            in.close();
        } catch(IOException e){
            showError("Connection closed not properly.");
        }
    }

    public synchronized void showMessage(String message){
        display_messages.display(message);
    }

    public synchronized void showError(String error){
        error_field.setText(error);
    }

    public synchronized void showInfo(String info){
        info_field.setText(info);
    }

    private synchronized void cleanUp(){
        showError("");
        showInfo("");
    }
}
