package echoclientv3;

/**
 *
 * @author darkhaker
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientV3 {

    Socket socket;
    String ip;
    int port;
    boolean stopped;

    public ClientV3(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            stopped = false;
        } catch (IOException ex) {
            Logger.getLogger(ClientV3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void client() {
        try {

            System.out.println("Connection established");
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            PrintStream socketOut = new PrintStream(socket.getOutputStream(), true);  //set autoflush
            while (!stopped) {
                String inputLine = stdin.readLine();
                socketOut.println(inputLine);
                String socketLine = socketIn.readLine();
                if (socketLine.contains(""+(char) 0)) {
                    stopped = true;
                }
                System.out.println(socketLine);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } catch (IOException ex) {
            Logger.getLogger(ClientV3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        ClientV3 c = new ClientV3("localhost", 9999);
        c.client();
    }
}
