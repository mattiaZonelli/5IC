package serverthread;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matteo Santuri ft. Phanapton
 */
public class Client {

    private String serverAddress = "127.0.0.1";
    private Socket socket;
    private final static int PORT = 9090;
    private BufferedReader input, key;
    private PrintStream output;
    private boolean ACTIVIA = true;
    public String s;

    public Client() {
        try {
            socket = new Socket(serverAddress, PORT);                           //crea connessione con server
            output = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            key = new BufferedReader(new InputStreamReader(System.in));

            while (ACTIVIA) {
                System.out.println("- quit - to disconnect the client");
                s = key.readLine();
                if (s.equalsIgnoreCase("quit")) {
                    output.println(s);
                    System.out.println("Arrivederci!");
                    ACTIVIA = false;
                } else {
                    output.println(s);
                    System.out.println(input.readLine());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws IOException {
        new Client();
    }

}
