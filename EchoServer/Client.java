package echoserver;

// Matteo Santuri

import java.net.*;
import java.io.*;

public class Client {
	 Socket conn;
	 BufferedReader ingresso;
	 OutputStream out;
	 PrintStream uscita;
	 BufferedReader tastiera;
	 boolean connessione;
	 int cont; 
	
	public Client(){
		try { 
		conn = new Socket("localhost", 8888);
		ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = conn.getOutputStream();
		uscita = new PrintStream(conn.getOutputStream(),true);
		tastiera = new BufferedReader(new InputStreamReader(System.in));
		cont = 0;
		} catch(IOException ex) {
			System.out.println("IO Exception");
		} 
	}
	public void invia() throws IOException{
		String messaggio="";
		connessione = true;
		while (connessione){
		System.out.println("Inserisci il messaggio da inviare al server, - chiudi - in 3 messaggi consecutivi interrompono la connessione");
		messaggio = tastiera.readLine();
		if (messaggio.equals("chiudi")){
			cont++;
				if ( cont == 3) {
					System.out.println("Connessione scaduta");
					connessione = false;
					reconnect();
				} 
		} else cont = 0;
		if (connessione) {
		uscita.println(messaggio);
		System.out.println("ECHO: "+ingresso.readLine());
		//uscita.flush();
		}
		}
		
	}
	
		public void reconnect() throws IOException{
			if(connessione==false){
				String messaggio = "";
				while(true){
					System.out.println("- reconnect - per riconnettere il socket");
				messaggio = tastiera.readLine();
				if(messaggio.equals("reconnect")){
					System.out.println("connessione ristabilita");
					conn = new Socket("localhost", 8888);
					cont = 0;
					invia();
				}
			}
		}
		}
	
	public static void main(String[] args) throws IOException {
		Client c = new Client();
		while(true){
			c.invia();
		}
	}
}
