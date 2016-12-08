package chatudp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author MATTEO
 */
public class ClientUDP implements Runnable {

    final static int portSelected = 8087;
    DatagramSocket serverSock = null;
    int port;
    InetAddress host;
    byte[] sendData = null;
    byte[] receiveData = null;
    ChatUDP g;
    FXMLDocumentController controller;

    public ClientUDP(FXMLDocumentController controller) {
        this.controller=controller;
        try {
            host = InetAddress.getByName("localhost");
            

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.port = portSelected;
        System.out.println("creato");
        controller.setTxtFieldGrande("Sono il client");
    }

    @Override
    public void run() {
        try {
            serverSock = new DatagramSocket();
            if (serverSock.isClosed()) {
                System.err.println("ERRORE Socket col server chiuso.");
            }
            
            System.out.println("1");
            System.out.println(host);
        } catch (SocketException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 /*   public static void main(String[] args) {
        ClientUDP c = new ClientUDP();
        c.run();
       
        
    }*/

}
