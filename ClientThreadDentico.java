package chatconthread;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    Socket connessione;
    BufferedReader ingresso;
    BufferedReader tast;
	PrintStream uscita;
    OutputStream out;
    public Client() {
        try {
            connessione = new Socket("localhost", 8000);
            ingresso = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
            out = connessione.getOutputStream();
            uscita = new PrintStream(connessione.getOutputStream(), true);
            tast = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public void send() {
        String msg = "";
        while (true) {
            try {
				System.out.print("Inserisci il messaggio da inviare al Server ('chiudi' per terminare la comunicazione): \n");
                msg = tast.readLine();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (msg.equalsIgnoreCase("chiudi")) {
                System.out.println("Connessione scaduta");
                try {
                    connessione.close();
                    break;
                } catch (IOException ex) {
                    System.out.println("Chiusura non effettuata");
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            uscita.println(msg);
            try {
                System.out.println("Server: " + ingresso.readLine());
                uscita.flush();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client x = new Client();
        x.send();
    }
}