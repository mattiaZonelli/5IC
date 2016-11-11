package ServerClient;

import java.net.*;
import java.io.*;

public class Server {

    ServerSocket s;
    Socket conn;
    PrintStream uscita;
    InputStreamReader in;
    BufferedReader ingresso;

    public Server() {
        try {
            s = new ServerSocket(50000);
            conn = s.accept();
            uscita = new PrintStream(conn.getOutputStream());
            in = new InputStreamReader(conn.getInputStream());
            ingresso = new BufferedReader(in);
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    public void riceviMessaggio() throws IOException {
        String messaggio = "";
        while (true) {
            messaggio = ingresso.readLine();
            uscita.println(messaggio);
        }
    }

    public static void main(String[] args) throws IOException {
        Server c = new Server();
        while (true) {
            c.riceviMessaggio();
        }
    }

}
