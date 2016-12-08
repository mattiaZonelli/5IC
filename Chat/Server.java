import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by pool on 04/12/16.
 */


public class Server extends Thread{

    final static String INET_ADDR = "224.0.0.3";

    final static int PORT = 8888;

    InetAddress addr;

    DatagramSocket serverSocket;

    public Server(){
        try {
            addr = InetAddress.getByName(INET_ADDR);
            serverSocket = new DatagramSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (SocketException ex){
            ex.printStackTrace();
        }
    }

    public void run()  {

        // Get the address that we are going to connect to.



        // Open a new DatagramSocket, which will be used to send the data.

        try{
            BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                byte [] inMessage = new byte[256];
                DatagramPacket inPacket = new DatagramPacket(inMessage,inMessage.length,addr,PORT);
                System.out.println("Aspetto il messaggio");
                serverSocket.receive(inPacket);
                String msg = new String(inPacket.getData(),0,inMessage.length);

                System.out.println("Received: "+msg);
                // Create a packet that will contain the data

                // (in the form of bytes) and send it.
                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, addr, PORT);
                serverSocket.send(msgPacket);
                System.out.println("Client sent packet with msg: " + msg);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException ex) {

            ex.printStackTrace();

        }

    }

    public static void main(String[] args) {
        new Server().start();
    }
}


