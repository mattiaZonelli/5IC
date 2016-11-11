package echoserver;
/*
   Author COLLAUTO ALVISE
*/
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	 Socket conn;
	 InputStreamReader in;
	 BufferedReader ingresso;
	 OutputStream out;
	 PrintStream uscita;
	 InputStreamReader tast;
	 BufferedReader tastiera;
	 static boolean closed = false;
	
	public Client(){
		try { 
			conn = new Socket("localhost", 50000);
			in = new InputStreamReader(conn.getInputStream());
			ingresso = new BufferedReader(in);
			out = conn.getOutputStream();
			uscita = new PrintStream(conn.getOutputStream(),true);
			tast = new InputStreamReader(System.in);
			tastiera = new BufferedReader(tast);
		} catch(IOException ex) {
			System.out.println("IO Exception");
		} 
	}
	public void send() {
		String messaggio="";
		System.out.println("Inserisci il messaggio da inviare al server, per interrompere scrivere fine");
		 try {
			 messaggio = tastiera.readLine();
		 } catch (IOException ex) {
			 Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		 }
		if (messaggio.equals("fine")){
			System.out.println("Connessione scaduta");
			try {
				conn.close();
				closed = true;
			} catch (IOException ex) {
				System.out.println("Connessione scaduta");
				Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else{
			uscita.println(messaggio);
			try {
				System.out.println("ECHO: "+ingresso.readLine());
				uscita.flush();
			} catch (IOException ex) {
				Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Client c = new Client();
		while(!closed){
			c.send();
		}
	}
}
