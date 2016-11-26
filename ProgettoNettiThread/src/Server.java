import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Bastianello Massimiliano on 11/11/2016.
 */
public class Server implements Runnable
{
    private Socket connessione;
    private BufferedReader input;
    private PrintWriter output;
    private String messaggio;

    public Server(Socket conn)
    {
        connessione = conn;
    }

    public static void main(String args[]){
        try {
            ServerSocket waitconnessione= new ServerSocket(9090);

            while(true)
            {
                //Server serverThread= new Server(waitconnessione.accept());
                new Thread(new Server(waitconnessione.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run()
    {

        try {
            input=new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            output=new PrintWriter(connessione.getOutputStream());

            do
            {

                messaggio=input.readLine();
                System.out.println("Messaggio ricevuto: " + messaggio);
                output.println("ho letto");
                output.flush();

            } while(messaggio != null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
