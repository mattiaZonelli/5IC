package chatconthread;

/*
 *Author Crocetta Jacopo
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThreadDentico {

    Socket conn;
    BufferedReader ingresso;
    PrintStream uscita;
    OutputStream out;
    BufferedReader tastiera;

    public ClientThreadDentico() {
        try {
            conn = new Socket("localhost", 54444);
            ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out = conn.getOutputStream();
            uscita = new PrintStream(conn.getOutputStream(), true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public void Spedisci() {
        String messaggio = "";
        System.out.println("Inserisci il messaggio da inviare al server, per interrompere le comunicazioni scrivere la parola 'esci' \n");
        while (true) {
            try {
                messaggio = tastiera.readLine();
            } catch (IOException ex) {
                Logger.getLogger(ClientThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (messaggio.equalsIgnoreCase("esci")) {
                System.out.println("Chiusura connessione...");
                try {
                    conn.close();
                    break;
                } catch (IOException ex) {
                    System.out.println("Errore nella chisura delle comunicazioni");
                    Logger.getLogger(ClientThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            uscita.println(messaggio);
            try {
                System.out.println("DAL SERVER: " + ingresso.readLine());
                uscita.flush();
            } catch (IOException ex) {
                Logger.getLogger(ClientThreadDentico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ClientThreadDentico c = new ClientThreadDentico();//Nel costruttore instauro la connessione con il server, inizializzo il canale socket e i vari reader
        c.Spedisci();//Qui mando il messaggio che voglio per aspettare la risposta del server
    }
}
