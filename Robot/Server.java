
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 *
 * @author Fabio Manfrin
 */
public class Server {

    public final static int PORT = 9999;

    public Server() {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("starting server at port " + PORT);
            while (true) {
                try {
                    Socket socket = server.accept();
                    System.out.println("connessione stabilita con " + socket.getLocalAddress());
                    Callable<Void> task = new ServerTask(socket);
                    pool.submit(task);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.err.println("Server couldn't start");
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
