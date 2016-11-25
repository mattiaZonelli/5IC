package serverclientd;

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

    public ClientV3(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException ex) {
            System.out.println("Impossible to reach the Server");
        }
    }

    public void client() {
        try {

            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            PrintStream socketOut = new PrintStream(socket.getOutputStream(), true);  //set autoflush
            System.out.println("Connection established");
            while (true) {
                String inputLine = stdin.readLine();
                socketOut.println(inputLine);
                String socketLine = socketIn.readLine();
                if (inputLine.equals("fine")) {
                    socket.close();
                    break;
                }
                System.out.println(socketLine);
            }
        } catch (Exception ex) {
            System.out.println("Connection Lost");
        }

    }

    public static void main(String[] args) {

        ClientV3 c = new ClientV3("localhost", 9999);
        c.client();
    }
}
