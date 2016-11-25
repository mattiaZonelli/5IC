package serverclientd;

/**
 *
 * @author darkhaker
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerV3 {

    ServerSocket echoServer;
    String line;
    BufferedReader is;
    PrintStream os;

    public ServerV3(ServerSocket echoServer) {
        this.echoServer = echoServer;
    }

    public ServerV3(int port) {
        try {
            echoServer = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ServerV3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void server() {

        try {
            while (true) {
                System.out.println("Server started.......Waiting for requests");
                Socket clientSocket = echoServer.accept();
                System.out.println("Recieved client connection");
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintStream(clientSocket.getOutputStream());
                line = is.readLine();
                while (true) {
                    os.println("Message Recieved");
                    System.out.println("Client Message: " + line);
                    line = is.readLine();
                    if (line == null) {
                        break;
                    }
                }
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            System.out.println(e);

        }
    }

    public static void main(String args[]) {

        ServerV3 s = new ServerV3(9999);
        s.server();
    }
}
