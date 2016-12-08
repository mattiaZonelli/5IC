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
public class ThreadServerUDP implements Runnable {

    final static String MULTICAST_GROUP = "224.0.0.1";
    final static int LUNGHEZZA_BUFFER = 1024;                //bufferini di 1 megabyte
    final static int PORT = 8087;                           //UDP used by Kaspersky AV Control Center

    protected String recivedPacketToString = "";
    protected DatagramPacket recivedPacket = null;
    protected DatagramSocket serverSocket = null;

    protected byte[] sendBuffer = null;
    protected byte[] receiveBuffer = null;
    protected ArrayList<InetAddress> clientAddress;

    public InetAddress groupAddress;

    //protected static BufferedReader input ;
    public ThreadServerUDP() throws IOException {
        serverSocket = new DatagramSocket();
        //converto il multicast address in un inet prechè lo vuole il costruttore del datagram
        groupAddress = InetAddress.getByName(MULTICAST_GROUP);
        System.out.println("Server creato. \n");

    }

    @Override
    public void run() {

        System.out.println("Server in esecuzione. \n");
        clientAddress = new ArrayList<>();

        try {
            while (true) {
                
               
                sendBuffer = new byte[LUNGHEZZA_BUFFER];
                receiveBuffer = new byte[LUNGHEZZA_BUFFER];
                recivedPacket = new DatagramPacket(sendBuffer, sendBuffer.length, groupAddress, PORT);
                System.out.println("Waiting da message");
                serverSocket.receive(recivedPacket);
                recivedPacketToString = new String(recivedPacket.getData(), 0, recivedPacket.getLength());
                System.out.println("Ricevuto: " + recivedPacket);
                DatagramPacket msgPacket = new DatagramPacket(recivedPacketToString.getBytes(), recivedPacketToString.getBytes().length, groupAddress, PORT);
                serverSocket.send(msgPacket);
                System.out.println("Client sent packet with msg: " + recivedPacketToString);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ThreadServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

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
