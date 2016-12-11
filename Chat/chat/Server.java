
package chat;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Server extends Thread {

    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private byte[] receiveData;
    private DBManager db;
    Server() {
        db=new DBManager();
        try {
            serverSocket = new DatagramSocket(9090);
            receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        System.out.println("server started");
        try {
            while (true) {
                serverSocket.receive(receivePacket);    
                String name = new String(receivePacket.getData(),0,receivePacket.getLength());
                System.out.println(name);
                db.addUser(name);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) {
        new Server().start();
    }
}
