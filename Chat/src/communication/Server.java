package communication;

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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by lucentini riccardo on 28/11/2016.
 */
public class Server extends Application {

    private final int PORT = 2500;
    private Connection conn;
    private Statement stmt;
    private ServerListener listener;
    private boolean running;

    private Displayable messageDisplay;
    private Displayable consolleDisplay;

    @FXML
    private Button start_stop;
    @FXML
    private TextArea consolle_messages;
    @FXML
    private TextArea messages_area;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("server.fxml"));
        stage.setTitle("Chat server");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    /**
     * Initialization of the server
     */

    @FXML
    private void start(){
        initDisplays();
        if(running){
            running = false;
            start_stop.setText("Start");
            setUsersDisconnected();
            DBDisconnection();
        } else {
            DBConnection();
            setUsersDisconnected();
            networkConnection();
            start_stop.setText("Stop");
            running = true;
        }
    }

    private void DBConnection(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
            stmt = conn.createStatement();
            displayConsolle("Connected to DB");
        } catch(SQLException e){
            displayConsolle("Can't connect to DB. " + e.getMessage());
        }
    }

    private void networkConnection(){
        try {
            DatagramSocket socket = new DatagramSocket(PORT);
            listener = new ServerListener(this, socket);
            Thread t = new Thread(listener);
            t.start();
        } catch(SocketException e){
            displayConsolle("Can't create network. " + e.getMessage());
        }
    }

    private void initDisplays(){
        messageDisplay = new Displayable(messages_area, 30);
        consolleDisplay = new Displayable(consolle_messages, 35);
    }

    private void DBDisconnection(){
        try {
            stmt.close();
            conn.close();
        } catch(SQLException e){
            displayConsolle("Problems disconnecting from DB. " + e.getMessage());
        }
    }


    /**
     * DB functions
     */

    public boolean checkUserRegistered(String userName) {
        boolean alreadyRegistered = true;
        try {
            ResultSet res = stmt.executeQuery("SELECT name FROM users WHERE name=\"" + userName + "\";");
            alreadyRegistered = res.next();
        }catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return alreadyRegistered;
    }

    public boolean checkUserConnected(String userName){
        boolean alreadyConnected = true;
        try {
            ResultSet res = stmt.executeQuery("SELECT connected FROM users WHERE name=\"" + userName + "\";");
            alreadyConnected = res.getBoolean(1);
        }catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
            alreadyConnected = false;
        }
        return alreadyConnected;
    }


    public boolean registerUser(String userName, int port, String address){
        boolean registered = true;
        try{
            stmt.executeUpdate("INSERT INTO users (name, connected, port, address) " +
                    "VALUES (\"" + userName + "\", \"" + false + "\", \"" + port + "\", \"" + address + "\");");
            displayConsolle("New user " + userName + " registered.");
        } catch(SQLException e){
            registered = false;
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return registered;
    }

    public void setUserConnected(String userName, boolean connected){
        try{
            stmt.executeUpdate("UPDATE users SET connected=\"" + connected + "\" WHERE name=\"" + userName + "\";");
            if(connected) {
                displayConsolle("User " + userName + " connected.");
            } else{
                displayConsolle("User " + userName + " disconnected.");
            }
        }catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
    }

    public boolean updateUser(String userName, int port, String address){
        boolean updated = true;
        try{
            stmt.executeUpdate("UPDATE users SET port=\"" + port + "\", address=\"" + address +
                            "\" WHERE name=\"" + userName + "\";");
        }catch(SQLException e){
            updated = false;
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return updated;
    }

    public InetAddress getAddress(String userName){
        InetAddress address = null;
        try{
            ResultSet res = stmt.executeQuery("SELECT address FROM users WHERE name=\"" + userName + "\";");
            address = InetAddress.getByName(res.getString(1));
        } catch (SQLException | UnknownHostException e){
            displayConsolle("Problems accessing DB or with IP address. " + e.getMessage());
        }
        return address;
    }

    public int getPort(String userName){
        int port = -1;
        try{
            ResultSet res = stmt.executeQuery("SELECT port FROM users WHERE name=\"" + userName + "\";");
            port = res.getInt(1);
        } catch (SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return port;
    }

    public void setUsersDisconnected(){
        try{
            stmt.executeUpdate("UPDATE users SET connected=\"" + false + "\" WHERE connected=\"" + true + "\";");
        }catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
    }

    public boolean checkPortPresence(int port){
        boolean present = false;
        try {
            ResultSet res = stmt.executeQuery("SELECT connected FROM users WHERE port=\"" + port + "\"");
            present = res.getBoolean(1);
        } catch(SQLException e){
            present = false;
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return present;
    }

    public ArrayList<String> getConnectedUsers(){
        ArrayList<String> users = new ArrayList<>();
        try {
            ResultSet res = stmt.executeQuery("SELECT name FROM users WHERE connected=\"true\"");
            while(res.next()){
                users.add(res.getString(1));
            }
        } catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return users;
    }

    public String getUserByPort(int port){
        String name = "";
        try {
            ResultSet res = stmt.executeQuery("SELECT name FROM users WHERE port=\"" + port + "\" AND connected=\"true\"");
            name = res.getString(1);
        } catch(SQLException e){
            displayConsolle("Problems accessing DB. " + e.getMessage());
        }
        return name;
    }

    public void displayConsolle(String message){
        consolleDisplay.display(message);
    }

    public void displayMessage(int port, String message){
        messageDisplay.display(getUserByPort(port) + ": " + message);
    }

}
