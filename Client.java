package ServerClient;

import java.net.*;
import java.io.*;

public class Client {

    Socket conn;
    InputStreamReader in;
    BufferedReader ingresso;
    OutputStream out;
    PrintStream uscita;
    InputStreamReader tast;
    BufferedReader tastiera;

    public Client() {
        try {
            conn = new Socket("localhost", 50000);
            ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = conn.getOutputStream();
            uscita = new PrintStream(conn.getOutputStream(), true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public void inviaMessaggio() throws IOException {
        String messaggio = "";
        System.out.println("Inserisci - fine - per interrompere la connessione \n");
        while (true) {
            messaggio = tastiera.readLine();
            System.out.println(messaggio);
            if (messaggio.equals("fine")) {
                conn.close();
                System.out.println("Connessione terminata con successo");
                System.exit(0);

            }

            uscita.println(messaggio);
            System.out.println("ECHO: " + "messaggio ricevuto correttamente" + "\n\t");
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        while (true) {
            c.inviaMessaggio();
        }
    }
}
