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
public class Server {

    public static void main(String args[]){
        try {
            ServerSocket waitconnessione= new ServerSocket(9090);
            Socket connessione;
            BufferedReader input;
            PrintWriter output;
            String messaggio;
            while(true)
            {
                connessione= waitconnessione.accept();
                input=new BufferedReader(new InputStreamReader(connessione.getInputStream()));
                output=new PrintWriter(connessione.getOutputStream());

                do
                {

                    messaggio=input.readLine();
                    System.out.println("Messaggio ricevuto: " + messaggio);
                    output.println("ho letto");
                    output.flush();

                } while(messaggio != null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
