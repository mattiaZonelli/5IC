
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 *
 * @author Fabio Manfrin
 */
class ServerTask implements Callable<Void> {

    private Socket connection;
    private PrintStream output;
    private BufferedReader input;
    private boolean ATTIVO = true;

    public ServerTask(Socket connection) {
        this.connection = connection;
    }

    @Override
    public Void call() throws InterruptedException {
        try {
            input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            output = new PrintStream(connection.getOutputStream());
            output.println("connessione stabilita");
            Protocol p = new Protocol();
            String messaggio = "";
            String risposta = p.controlla(messaggio);
            while (ATTIVO) {
                if (risposta.equalsIgnoreCase("quit")) { //comando proveniente dal protocollo che chiude la connessione con il client
                    ATTIVO = false;
                    output.println("connection stopped"); //comando per chiudere la connessione con il client
                } else if (isAnswerable(risposta)) {
                    output.println(": " + risposta);
                    messaggio = input.readLine();
                    risposta = p.controlla(messaggio);
                } else {
                    output.println(": " + risposta);
                    risposta = p.controlla(messaggio);
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
        return null;
    }

    public boolean isAnswerable(String s) {
        boolean risposta = false;
        if (s.charAt(s.length() - 1) == 'w') {
            risposta = true;
        }
        return risposta;

    }
}
