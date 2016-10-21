/**
 * Created by Rikkardo on 16/10/2016.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class EchoServer {

    ServerSocket echoServer;
    boolean working;
    String[] outComb;

    public EchoServer(int port, String[] outComb, boolean sayHowToExit){
        try {
            echoServer = new ServerSocket(port);
            working = true;
            this.outComb = outComb;
        } catch(IOException e){
            System.out.println("Can't create server.");
        }
    }

    public void listen(){
        Socket clientSocket = null;
        while (working) {
            try {
                System.out.println("new Connection");
                clientSocket = echoServer.accept();
                Thread t = new Thread(new ClientHandler(clientSocket, outComb));
                t.start();
            } catch (IOException e) {
                System.out.println("Problems establishing a new connection.");
            }
        }
    }

    public static void main(String args[]) {
        String[] disComb = {"Ciao", "Voglio", "Uscire"};
        EchoServer server = new EchoServer(9999, disComb, true);
        server.listen();
    }

}
