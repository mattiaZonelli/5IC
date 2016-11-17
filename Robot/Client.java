
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio Manfrin
 */
public class Client {

    private String serverAddress = "127.0.0.1";
    private Socket socket;
    private final static int PORT = 9999;
    private BufferedReader input;
    private BufferedReader tastiera;
    private PrintStream output;
    private boolean ATTIVO = true;

    Client() {
        try {
            socket = new Socket(serverAddress, PORT);                           //crea connessione con server
            output = new PrintStream(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = input.readLine();
            System.out.println(s);

            while (ATTIVO) {

                s = input.readLine();

                if (s.equalsIgnoreCase("connection stopped")) {
                    ATTIVO = false;
                } else if (isAnswerable(s)) {
                    System.out.println(s.substring(0, s.length() - 1));
                    output.println(chiedi());
                } else {
                    System.out.println(s);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String chiedi() {
        tastiera = new BufferedReader(new InputStreamReader(System.in));
        String messaggio = "";
        try {
            messaggio = tastiera.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messaggio;
    }

    public boolean isAnswerable(String s) {
        boolean risposta = false;
        if (s.charAt(s.length() - 1) == 'w') {
            risposta = true;
        }
        return risposta;

    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
    }

}
