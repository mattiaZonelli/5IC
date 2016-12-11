package communication;

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
import other.MessageInfo;
import other.utility;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Rikkardo on 28/11/2016.
 */
public class Client extends Application{

    private final int PORT = 2500;
    private DatagramSocket socket;
    private InetAddress IPAddress;
    private boolean connected;

    private Displayable messageDisplay;

    @FXML
    private Button join;
    @FXML
    private Button send;
    @FXML
    private TextField IP_field;
    @FXML
    private TextField nickname_field;
    @FXML
    private TextField message_field;
    @FXML
    private Text IP_error;
    @FXML
    private Text nickname_error;
    @FXML
    private TextArea message_area;

    public Client(){
        connected = false;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        stage.setTitle("Chat client");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    private void initDisplays(){
        messageDisplay = new Displayable(message_area, 40);
    }

    private void initConnection(){
        ClientListener listener = new ClientListener(this, socket);
        Thread t = new Thread(listener);
        t.start();
    }

    @FXML
    private void connect(){
        cleanErrors();
        initDisplays();
        if(connected){
            send("disconnect");
            connected = false;
            join.setText("Join");
        } else if(utility.validIp(IP_field.getText())){
            try{
                socket = new DatagramSocket();
                IPAddress = InetAddress.getByName(IP_field.getText());
                send("connect " + nickname_field.getText());
                MessageInfo message = receive();
                if(message.getType()==3){
                    connected = true;
                    join.setText("Disconnect");
                    initConnection();
                } else if(message.getType()==4){
                    nickname_error.setText("This nickname is already in use and online.");
                } else{
                    nickname_error.setText("Can not connect to server.");
                }
                join.setText("Disconnect");
            }catch(IOException e){
                IP_error.setText("IP address not working.");
                System.out.println(e.getMessage());
            }
        } else{
            IP_error.setText("IP address not correct.");
        }
    }

    @FXML
    private void sendMessage(){
        cleanErrors();
        if(connected) {
            send("message " + message_field.getText());
        }
        cleanMessageField();
    }

    private synchronized void send(String message){
        message += " end";
        byte[] sendData = message.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPAddress, PORT);
        try {
            socket.send(packet);
        }catch(IOException e){
            System.out.println("Impossible send pack.");
        }
    }

    private synchronized MessageInfo receive(){
        MessageInfo info = null;
        byte[] receiveData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(packet);
            info = new MessageInfo(packet);
        } catch(IOException e){
            System.out.println("Can't receive packet");
        }
        return info;
    }

    private void cleanErrors(){
        IP_error.setText("");
        nickname_error.setText("");
    }

    private void cleanMessageField(){
        message_field.setText("");
    }

    public void displayMessage(String message){
        messageDisplay.display(message);
    }

}
