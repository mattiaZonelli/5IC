/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Nour Safadie
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
    String Messaggio;
    String MSN;
    
    public Server() {
        try {
            s= new ServerSocket(30000);
            conn= s.accept();
            out= conn.getOutputStream();
            uscita = new PrintStream(out);
            input= conn.getInputStream();
            in= new InputStreamReader(input);
            ingresso= new BufferedReader(in);
            Messaggio ="messaggio ricevuto";
            MSN = "";
        } catch (IOException | NullPointerException ex) {
            System.out.println("impossibile creare il server");
            
            
        }
    }
    
    public void Ricevere() {
        try {
            MSN = ingresso.readLine();
            uscita.println(Messaggio);
        } catch (IOException ex){
            System.out.println (" nessun messaggio è stato ricevuto ");
            
       
        }
    }
    
     
  
    public static void main(String[] args) throws IOException { {
        
        Server s=new Server();
        
        while (true) {
            s.Ricevere();
        }
    }
   
    }
    
}
