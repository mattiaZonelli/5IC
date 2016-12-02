package threadserverclient;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author darkhaker
 */
public class Server {
    
    private int port;
    public static LinkedList<ClientHandler> counter = new LinkedList();

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {

        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
             System.err.println(e.getMessage());
            return;
        }   
        System.out.println("Server started.......Waiting for requests");
        
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket,counter.size()+1);//
                counter.add(client);
                System.out.println("Recieved Client Connection " + counter.size());
                executor.submit(client);//
            } catch (IOException e) {
                break;	
            }
        }
        executor.shutdown();
    }
    public static void main(String[] args) {
        Server echoServer= new Server(9999);
        echoServer.startServer();
    }

}




