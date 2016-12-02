package threadserverclient;

import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author darkhaker
 */
public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private int id;
    
    public ClientHandler(Socket socket, int id) {
        this.clientSocket = socket;
        this.id = id;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream out = new PrintStream(clientSocket.getOutputStream(),true);	
            while (true) {
                String line = in.readLine();
                if (line == null) { 
                    break;
                } else {
                    out.println("Message Recieved"); 
                    System.out.println("Client " + id +" Message: " + line); 
                }
            }
            System.out.println("Client " + id + " Disconnected"); 
            /*in.close();
            out.close();
            clientSocket.close(); */       
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }
}