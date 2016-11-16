package gui;

import communication.Protocol;
import communication.ServerListener;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import other.Displayable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Rikkardo on 26/10/2016.
 */
public class Server extends Application{

    private final int PORT = 2500;
    private ServerListener listener;
    private ArrayList<Protocol> connected;
    private Displayable info_display;

    @FXML
    private Button start_stop;
    @FXML
    private TextArea info_area;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("server_interface.fxml"));
        primaryStage.setScene(new Scene(root, 600, 420));
        primaryStage.setTitle("SimpleDialogue Server");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @FXML
    private void start_stop(){
        if(start_stop.getText().equals("Stop")){
            start_stop.setText("Start");
            close();
        } else{
            info_display = new Displayable(info_area, 50);
            start_stop.setText("Stop");
            initialize();
        }
    }

    private void initialize(){
        try {
            listener = new ServerListener(new ServerSocket(PORT), this);
            Thread t = new Thread(listener);
            t.start();
            connected = new ArrayList<>();
            showInfo("Server launched succesfully.");
        } catch(IOException e){
            showInfo("Can not launch server.");
        }
    }

    private void close(){
        listener.close();
        for(int i = connected.size()-1; i>=0; i--){
            if(connected.get(i).isRunning()) {
                connected.get(i).close(true, "");
            }
        }
    }

    public void addConnection(Socket connection){
        Protocol p = new Protocol(connection, info_display);
        connected.add(p);
        Thread t = new Thread(p);
        t.start();
        showInfo("New user connected.");
    }

    public void showInfo(String info){
        info_display.display(info);
    }

}
