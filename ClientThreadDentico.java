package chatthreaddentico;

import java.net.*;
import java.io.*;

public class ClientThreadDentico {

    Socket connection;
    InputStreamReader in;
    OutputStream out;
    BufferedReader ingresso;
    PrintStream uscita;
    InputStreamReader tast;
    BufferedReader tastiera;

    public ClientThreadDentico() {
        try {
            connection = new Socket("localhost", 8000);
            ingresso = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = connection.getOutputStream();
            uscita = new PrintStream(connection.getOutputStream(), true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inserisci il messaggio - chiudi - per terminare la comunicazione.");
            System.out.print("Client: ");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public void inviaMessaggio() throws IOException {
        String messaggio = tastiera.readLine();
        if (messaggio.equals("chiudi")) {
            System.out.println("Comunicazione terminata con successo:");
            connection.close();
            System.exit(0);
        }

        uscita.println(messaggio);
        System.out.println("Server: " + ingresso.readLine());
        System.out.print("Client: ");
    }

    public static void main(String[] args) throws IOException {
        ClientThreadDentico client = new ClientThreadDentico();
        while (true) {
            client.inviaMessaggio();
        }
    }
}
