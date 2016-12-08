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
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author MATTEO
 */
public class ClientUDP implements Runnable {

    final static int PORT = 8087;
    final static int BUFFER_LENGTH = 1024;
    final static String MULTICAST_GROUP = "224.0.0.1";

    public DatagramSocket serverSock = null;
    public int port;
    public InetAddress address;
    public byte[] sendData = null;
    public byte[] receiveData = null;
    public ChatUDP g;

    protected static MulticastSocket socket = null;
    protected static String recivedPacketToString = "";
    protected static DatagramPacket recivedPacket = null;
    FXMLDocumentController controller;

    byte[] buf;

    public ClientUDP(FXMLDocumentController controller) {
        this.controller = controller;
        try {
            address = InetAddress.getByName(MULTICAST_GROUP);
            socket = new MulticastSocket(PORT);
            socket.joinGroup(address);

            this.sendData = new byte[BUFFER_LENGTH];
            this.receiveData = new byte[BUFFER_LENGTH];

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Client creato");
        controller.setTxtFieldGrande("Client Creato");
    }

    public void send(String msg) {
        try {
            String toSend = msg;
            System.out.println("CLient richiede di inviare: " + toSend);

           
            DatagramPacket sendMsg = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, address, PORT);
            System.out.println("Datagramma creato");
            socket.send(sendMsg);
            System.out.println("Datagramma inviato");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            send("Primo messaggio");

            System.out.println("Client waiting da packet: ");
            buf = new byte[BUFFER_LENGTH];

            recivedPacket = new DatagramPacket(buf, buf.length);
            socket.receive(recivedPacket);
            recivedPacketToString = new String(buf, 0, buf.length);
            //String name = recivedPacketToString.substring(0, recivedPacketToString.indexOf(":"));

        } catch (SocketException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
