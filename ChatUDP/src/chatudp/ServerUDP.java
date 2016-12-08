package chatudp;


import java.io.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
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
public class ServerUDP {

    final static int lunghezzaBuffer = 1024;                 //bufferini di 1 megabyte
    final static int port = 8087;                           //UDP used by Kaspersky AV Control Center
    protected static DatagramSocket clientSocket = null;
    protected static String recivedPacketToString = "";
    protected static DatagramPacket recivedDatagram;
    protected static byte[] sendBuffer = null;
    protected static byte[] receiveBuffer = null;
    //protected static BufferedReader input ;

    public static void main(String[] args) {

        try {
            //input  = new BufferedReader(new InputStreamReader(System.in));
            sendBuffer = new byte[lunghezzaBuffer];
            receiveBuffer = new byte[lunghezzaBuffer];
            
            //Construct the socket
            clientSocket = new DatagramSocket(port);
            recivedDatagram = new DatagramPacket(sendBuffer, sendBuffer.length);

            System.out.println("The server is ready...");
            Scanner input=new Scanner(System.in);
            //while(true)
            for (;;) {
                clientSocket.receive(recivedDatagram);
                byte[] data = recivedDatagram.getData();
                recivedPacketToString = new String(data, 0, recivedDatagram.getLength());

                System.out.println(recivedDatagram.getAddress() + " " + recivedDatagram.getPort() + ": " + recivedPacketToString);

                clientSocket.send(recivedDatagram);
            }

        } catch (SocketException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
