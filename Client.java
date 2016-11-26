/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Nour Safadie
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Client {
    
    Socket conn;
    BufferedReader ingresso;
    PrintStream uscita;
    OutputStream out;
    BufferedReader tastiera;
    
    public Client() {
        try {
            conn = new Socket("localhost",30000);
            ingresso = new BufferedReader (new InputStreamReader(conn.getInputStream()));
            out = conn.getOutputStream();
            uscita = new PrintStream(conn.getOutputStream(),true);
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            
        } catch (IOException ex) {
            System.out.println ("IO Exception");
        }
     
    }
    public void send() {
        String messaggio = "";
        System.out.println("Digita il messaggio da inviare al server e per una fine di una comunicazione digita EXIT");
        while (true) {
            try {
                messaggio = tastiera.readLine();
                System.out.println(messaggio);
            } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    
            }
            
            if (messaggio.equals("end")) {
                System.out.println("Connessione non è andata a buon fine");
                try {
                    conn.close();
                    break;
                } catch (IOException ex) {
                    
                    System.out.println("Si è verificato un errore nella chiusura della comunicazione");
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            uscita.println(messaggio);
            try {
                System.out.println ("ECHO: " + ingresso.readLine());
                uscita.flush();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) throws IOException  {
       
        Client c = new Client();
        c.send();
    }
    
}
