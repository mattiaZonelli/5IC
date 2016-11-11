
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fantuzzo Marco
 */
public class Client {

    Socket conn = null;
    BufferedReader ingresso = null;
    PrintWriter uscita = null;
    InputStreamReader tast = null;
    BufferedReader tastiera = null;
    String messaggio = "";

    Client() throws IOException {
        conn = new Socket("localHost", 9999);
        ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream())); //passo l'oggetto che riceve dati        
        uscita = new PrintWriter(conn.getOutputStream(),true);
        tast = new InputStreamReader(System.in);
        tastiera = new BufferedReader(tast); 
        while (true) {
            messaggio = tastiera.readLine();
            if (messaggio.equals("fine")){
                conn.close();
                break;
            }
            uscita.println(messaggio);
            messaggio = ingresso.readLine();
            System.out.println(messaggio);
            //uscita.flush();//invia tutti i messaggi
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
    }
}
