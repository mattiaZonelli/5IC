package serverthread;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 *
 * @author Matteo Santuri
 */
class ThreadServer implements Runnable {

    private Socket connection;
    private PrintStream output;
    private BufferedReader input, key;
    private boolean CONNECTED = true;
    public int numClient = Server.numClient;

    public ThreadServer(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            key = new BufferedReader(new InputStreamReader(System.in));
            output = new PrintStream(connection.getOutputStream());
            CONNECTED = true;
            numClient++;
            System.out.println("connessione stabilita con client " + numClient);

            while (CONNECTED) {
                String clientMessage = input.readLine();
                boolean messageReceived = false;
                if (clientMessage.equalsIgnoreCase("quit")) {
                    System.out.println("client " + numClient + " disconnesso");
                    CONNECTED = false;
                } else {
                    System.out.println("messaggio ricevuto da client " + numClient + ": " + clientMessage);
                    output.println("messaggio ricevuto dal server");
                }

            }
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
