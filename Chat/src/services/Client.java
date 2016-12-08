package services;

import chatroom.FXMLClientController;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Swapnil Paul
 */
public class Client extends Thread {

    DatagramSocket serverSock = null;
    int port;
    InetAddress host;

    FXMLClientController controller;
    Connection conn = null;
    Label label;
    String loginStatus;
    String infoMsg;
    BASE64Encoder bs64en;
    BASE64Decoder bs64de;

    /**
     *
     */
    public Client() {
        try {
            host = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loginStatus = "false";
        this.port = 7777;
        this.bs64en = new BASE64Encoder();
        this.bs64de = new BASE64Decoder();
    }

    /**
     *
     * @param aThis
     */
    public Client(FXMLClientController aThis) {
        try {
            host = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loginStatus = "false";
        this.controller = aThis;
        this.bs64en = new BASE64Encoder();
        this.port = 7777;
    }

    @Override
    public void run() {
        serverSock = null;
        System.out.println("Client start");
        try {
            String stringMsg;
            serverSock = new DatagramSocket();
            if (serverSock.isClosed()) {
                System.err.println("Server is temporarily closed!");
                controller.print("Server is temporarily closed!");
            }
            while (true) {
                //now receive reply
                //buffer to receive incoming data
                byte[] buffer = new byte[65536];
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length, host, port);
                serverSock.receive(reply);

                byte[] data = reply.getData();
                stringMsg = new String(data, 0, reply.getLength());

                ///codice adduser
                if (stringMsg.length() > 4 && stringMsg.substring(0, 2).equals("XX")) {
                    if (stringMsg.substring(2, 9).equals("ADDUSER")) {
                        //controller.printAddUserInfo(stringMsg.substring(9, stringMsg.length()));
                        infoMsg = stringMsg.substring(9, stringMsg.length());
                    } else if (stringMsg.substring(2, 9).equals("LOGINVE")) {
                        loginStatus = stringMsg.substring(9, stringMsg.length());
                    }
                } else {
                    System.out.println("HOST: " + host + " - MSG: " + stringMsg);
                    if (!stringMsg.isEmpty()) {
                        String newString = stringMsg.replace("ç", "\n");
                        controller.print(newString);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("IOException " + e);
            controller.print("Server is closed");
        }
    }
    
    /**
     *
     * @param encodeStr
     * @return encoded String
     */
    public String encode(String encodeStr){
        String enc = bs64en.encode(encodeStr.getBytes());
        return enc;
    } 
    
    /**
     *
     * @param decodeStr
     * @return decoded String
     */
    public String decode(String decodeStr){
        String dec = null;
        try {
            byte[] d = bs64de.decodeBuffer(decodeStr);
            dec = new String(d);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dec;
    }

    /**
     *
     * @param msg
     */
    synchronized public void sendMsg(String msg) {
        try {
            byte[] b = msg.getBytes();
            DatagramPacket dp = new DatagramPacket(b, b.length, host, port);
            serverSock.send(dp);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param username
     * @param password
     * @param label
     */
    synchronized public void addUser(String username, String password, Label label) {
        String msg = "XXADDUSER" + encode(username) + "§§" + encode(password);
        sendMsg(msg);
        this.label = label;
    }

    /**
     *
     * @param username
     * @param password
     */
    synchronized public void loginVerification(String username, String password) {
        String msg = "XXLOGINVE" + encode(username) + "§§" + encode(password);
        sendMsg(msg);
    }

    /**
     *
     * @param host
     */
    synchronized public void setHost(String host) {
        try {
            if (host.equals("localhost") || (host.length() > 9 && host.length() < 15)) {

                this.host = InetAddress.getByName(host);

            } else {
                this.host = InetAddress.getByName("localhost");
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @return String infoMsg
     */
    synchronized public String infoMsg(){
        return infoMsg;
    }

    /**
     *
     * @return boolean loginStatus
     */
    synchronized public boolean loginStatus() {
        if (loginStatus.equals("true")) {
           return true;
        } 
        return Boolean.parseBoolean(loginStatus);
    }

}
