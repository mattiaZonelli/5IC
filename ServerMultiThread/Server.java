package serverthread;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Matteo Santuri ft. Phanapton
 */
public class Server {

    public final static int PORT = 9090;
    public static int numClient = 0;

    public Server() {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("starting server at port " + PORT);
            while (true) {
                try {
                    Socket socket = server.accept();
                    pool.submit(new ThreadServer(socket));
                    numClient++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println("Server can't start");
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
