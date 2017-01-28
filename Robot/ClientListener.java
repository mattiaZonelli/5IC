import java.io.*;
import java.net.Socket;

/**
 * Classe che ascolta il singolo client
 * Created by pool on 25/10/16.
 */
public class ClientListener implements Runnable{
    Socket s;
    Protocol p;
    boolean stopped;
    BufferedReader in;
    PrintStream out;

    /**
     * Crea un ascoltatore di un Client
     * @param s Punto terminale della connessione
     * @param db Database che verra utilizzato dal protocollo
     */
    public ClientListener(Socket s,DBUtility db) {
        this.s = s;
        p = new Protocol(db);
        stopped=false;
    }

    /**
     * Getter socket
     * @return socket
     */
    public Socket getSocket() {
        return s;
    }

    /**
     * Ritorna se è finito l'ascolto di un client
     * @return true comunicazione finita
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * Ferma la comunicazione
     */
    public void setStopped() {
        this.stopped = true;
    }


    /**
     * Comincia l'ascolto di un client
     */
    @Override
    public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
                out = new PrintStream(getSocket().getOutputStream(), true);
                out.println(p.answer(""));
                while(!isStopped()) {
                    String line = in.readLine();
                    String answer = p.answer(line);
                    if(answer.charAt(answer.length()-1) == (char)2)
                        setStopped();
                    out.println(answer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
