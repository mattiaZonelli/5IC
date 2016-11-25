
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Leonardo
 */
public class Client {

    static Socket conn;
    static BufferedReader ingresso, tastiera;
    static PrintWriter uscita;
    InputStreamReader in, tast;
    OutputStream out;

    public Client() throws IOException {
        this.conn = new Socket("localhost", 9999);
        ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        uscita = new PrintWriter(conn.getOutputStream(), true);
        tastiera = new BufferedReader(new InputStreamReader(System.in));

    }

    public static void Check() throws IOException {

        String messaggio = "";
        String usci;
        while (true) {

            System.out.println("Inserisci il messaggio: ");
            messaggio = tastiera.readLine();

            if (messaggio.equals("fine")) {
                conn.close();
                System.exit(0);
            }
            uscita.println(messaggio);
            uscita.flush();
            usci = ingresso.readLine();
            System.out.println("Server: " + ingresso.readLine());
            System.out.println(usci);
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        while (true) {
            c.Check();
        }
    }
}
