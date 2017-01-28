import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Classe che offre il servizio ai client
 * Created by pool on 25/10/16.
 */
public class Server extends Thread{
    ServerSocket server;
    ThreadPoolExecutor pooledThreads;
    DBUtility db;

    /**
     * Costruttore che crea il server tramite la porta
     * @param port porta d'ascolto
     */
    public Server(int port){
        try{
            server = new ServerSocket(port);
            pooledThreads = new ThreadPoolExecutor(4, 6, 2000, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4));
            db = new DBUtility();
        }catch (IOException e){
            System.out.println("Si è verificato un errore nella creazione del server");
        }
    }

    /**
     * Metodo che starta il server
     */
    public void run(){
        System.out.println("Aspetto connessioni");
        while(true){
            try {
                Socket client = server.accept();
                System.out.println("Connesso: "+client);
                pooledThreads.execute(new ClientListener(client,db));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int port = 0;
        System.out.println("Inserisci la porta sulla quale ascoltare le connessioni");
        try{
            port = Integer.parseInt(in.readLine());
        }catch (IOException e) {
            e.printStackTrace();
        }
        Server srv = new Server(port);
        srv.start();
    }
}
