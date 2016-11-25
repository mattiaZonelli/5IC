
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author Leonardo
 */


//classe che tramite l'utilizzo dei thread crea ogni connessione Client-Server utilizzando la classe ThreadClient
class ThreadUse extends Thread {

    static Socket clientSocket;

    public ThreadUse( Socket s) {
        clientSocket = s;
    }

    @Override
    public void run() {
        ThreadClient appr = new ThreadClient(this.clientSocket);
        Thread utilizzoClient = new Thread(appr);
        utilizzoClient.start();
    }
}

//classe che ingloba le funzionalità della classe Server ma utilizzata tramite Thread
public class ThreadClient extends Thread {

    static Socket clientSocket = null;
    BufferedReader in = null;
    Server server;
    PrintWriter ingresso;
    static String app;

    public ThreadClient(Socket Socket) {
        clientSocket = Socket;
        server = new Server();
    }

    @Override
    public void run() {

        boolean stop = false;
        while (!stop) {
            try {
                in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
                ingresso = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
                while (!stop) {
                    try {
                        app = in.readLine();
                    } catch (IOException ex) {
                        System.out.println("Errore in ingresso");
                    }
                    if (app == null) {
                        break;
                    }
                    System.out.print(app);
                    ingresso.println(app);
                    ingresso.flush();
                }
            } catch (IOException ex) {
                System.out.println("Errore in creazione");
            }
        }

    }

}
