package server;

/**
 *
 * @Ventura Giorgio
 */

import java.net.*;
import java.io.*;

public class Server {

    ServerSocket s;
    Socket conn;
    OutputStream out;
    PrintStream uscita;
    InputStream input;
    InputStreamReader in;
    BufferedReader ingresso;
    String messaggio;
    String msn;

    public Server() {
        try {
            s = new ServerSocket(50000);
            conn = s.accept();
            out = conn.getOutputStream();
            uscita = new PrintStream(out);
            input = conn.getInputStream();
            in = new InputStreamReader(input);
            ingresso = new BufferedReader(in);
            messaggio = "messaggio ricevuto";
            msn = "";
        } catch (IOException | NullPointerException ex) {//utilizzo multi-catch 
            System.out.println("Non riesco a creare il server");
        }
    }
    
    public void Ricevere() {
        try {
            msn = ingresso.readLine();
            uscita.println(messaggio);
        } catch (IOException ex) {
            System.out.println("Non riesco a ricevere nulla");
        }
    }
    
    public static void main(String[] args) throws IOException {
        Server s = new Server();
        
        while (true) {
            s.Ricevere();
        }
        
    }  
}

