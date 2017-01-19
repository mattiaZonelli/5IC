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

    @Override
    public void run() {

    }

    public static void main(String[] args) throws IOException {
        ServerUDP s = new ServerUDP();
        s.run();

    }

}

///////*********************MISTERI DELLA FEDE***************************\\\\\\\

/*
 //Construct the socket
 System.out.println("Server pronto.");
 Scanner input = new Scanner(System.in);
 //while(true)
 for (;;) {
 serverSocket.receive(recivedPacket);
 byte[] data = recivedPacket.getData();
 recivedPacketToString = new String(data, 0, recivedPacket.getLength());
 System.out.println(recivedPacket.getAddress() + " " + recivedPacket.getPort() + ": " + recivedPacketToString);
 // serverSocket.send(recivedDatagram);
 }*/
