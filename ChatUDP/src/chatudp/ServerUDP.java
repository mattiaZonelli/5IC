package chatudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MATTEO
 */
public class ServerUDP implements Runnable {

    final static String MULTICAST_GROUP = "224.0.0.3";
    final static int LUNGHEZZA_BUFFER = 1024;                //bufferini di 1 megabyte
    final static int PORT = 4446;                           //UDP used by Kaspersky AV Control Center

    protected String recivedPacketToString = "";
    protected DatagramPacket recivedPacket = null;
    protected DatagramSocket serverSocket = null;

    protected byte[] sendBuffer = null;
    protected byte[] receiveBuffer = null;
    protected ArrayList<InetAddress> clientAddress;

    public InetAddress groupAddress;

    //protected static BufferedReader input ;
    public ServerUDP() throws IOException {
    
        //converto il multicast address in un inet prechè lo vuole il costruttore del datagram
        groupAddress = InetAddress.getByName(MULTICAST_GROUP);
        System.out.println("Server creato. \n");

    }

    public void run() {
        System.out.println("server started");
        try {
            while (true) {
                serverSocket.receive(recivedPacket);    
                String name = new String(recivedPacket.getData(),0,recivedPacket.getLength());
                System.out.println(name);
             
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException e){
            System.err.println("Arrivato pacchetto nullo");
        }

    }

    public static void main(String[] args) throws IOException {
        

    }

}
