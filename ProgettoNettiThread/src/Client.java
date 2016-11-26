import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Bastianello Massimiliano on 11/11/2016.
 */
public class Client {
    public static void main(String args[]){
        try {
            Socket connessione=new Socket("127.0.0.1",9090);
            BufferedReader input=new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            PrintWriter output=new PrintWriter(connessione.getOutputStream());
            String messaggio=new String();
            Scanner leggo= new Scanner(System.in);
            while(true)
            {
                messaggio = leggo.nextLine();
                if(messaggio.equals("Fine"))
                    break;
                output.println(messaggio);
                output.flush();
                messaggio=input.readLine();
                System.out.println("Server:" + messaggio);
            }
            connessione.close();
        } catch (IOException e)

        {
            e.printStackTrace();
        }

    }
}
