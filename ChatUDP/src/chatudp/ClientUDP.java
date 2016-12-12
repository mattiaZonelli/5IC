package chatudp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.*;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author MATTEO
 */
public class ClientUDP implements Runnable {

    final static int BUFFER_LENGTH = 1024;

    public DatagramSocket serverSock = null;
    public int port;
    public InetAddress address;
    public byte[] sendData = null;
    public byte[] receiveData = null;
    public ChatUDP g;
    byte[] buf;

    protected static MulticastSocket socket = null;
    protected static String recivedPacketToString = "";
    protected static DatagramPacket recivedPacket = null;
    FXMLDocumentController controller;
    DatagramPacket msgPacket;

    final static String INET_ADDR = "224.0.0.4";
    final static int PORT = 8888;
    MulticastSocket clientSocket;

    public ClientUDP(FXMLDocumentController controller) throws UnknownHostException, IOException {

        this.controller = controller;
        address = InetAddress.getByName(INET_ADDR);
        buf = new byte[256];
        clientSocket = new MulticastSocket(PORT);
        msgPacket = new DatagramPacket(buf, buf.length);
        clientSocket.joinGroup(address);

        System.out.println("Client creato");
        controller.setTxtFieldGrande("Client Creato");
    }

    public void send(String msg) {
        String toSend = msg;

        try {
            DatagramPacket sendMsg = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, address, PORT);
            clientSocket.send(sendMsg);
            System.out.println("Inviato: " + toSend);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Dentro al run");
            controller.setTxtFieldGrande("Client in esecuzione");
           send(controller.getNickname()+" si è unito alla chatRoom");
        

            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);
                controller.getTxtFieldGrande().appendText(msg + "\n");

                /*clientSocket.receive(msgPacket);

                 String msg = new String(buf, 0, buf.length);
                 System.out.println("Socket 1 received msg: " + msg);*/
            }
        } catch (IOException ex) {
            ex.printStackTrace();
		 // } catch (InterruptedException ex) {
            //     Logger.getLogger(ClientUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
