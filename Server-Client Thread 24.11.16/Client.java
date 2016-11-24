
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Fantuzzo Marco
 */
public class Client extends Thread {

    Socket conn = null;
    BufferedReader ingresso = null;
    PrintWriter uscita = null;
    Scanner tastiera = null;
    String messaggio = "";

    Client() throws IOException {
        conn = new Socket("localHost", 9999);
        ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream())); //passo l'oggetto che riceve dati        
        uscita = new PrintWriter(conn.getOutputStream(), true);
        tastiera = new Scanner(System.in);
    }

    public void run() {
        try {
            while (true) {
                messaggio = tastiera.nextLine();
                if (messaggio.equals("fine")) {
                    conn.close();
                    break;
                }
                uscita.println(messaggio);
                messaggio = ingresso.readLine();
                System.out.println(messaggio);
                uscita.flush();
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Client c = new Client();
        c.run();
    }
}
