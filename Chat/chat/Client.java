
package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

class Client extends Thread{
    String name;
    final static String INET_GROUP = "224.0.0.1";
    final static int PORT =9999;
    InetAddress address;
    MulticastSocket clientSocket;
    FXMLDocumentController gui;
    Client(FXMLDocumentController gui) {
        try {
            address = InetAddress.getByName(INET_GROUP);
            clientSocket = new MulticastSocket(PORT);
            clientSocket.joinGroup(address);
            this.gui = gui;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(String msg) {
        try {
            DatagramPacket sendMsg = new DatagramPacket(msg.getBytes(), msg.getBytes().length, address, PORT);
            clientSocket.send(sendMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void run(){
        try {
            while(true) {
                byte[] buf = new byte[1024];
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);
                gui.getTxtArea().appendText(msg+"\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
