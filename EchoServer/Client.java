package echoserver;

import java.net.*;
import java.io.*;

public class Client {
	 Socket conn;
	 BufferedReader ingresso;
	 OutputStream out;
	 PrintStream uscita;
	 BufferedReader tastiera;
	 boolean connessione;
	 int close;

	public Client(){
		try {
		conn = new Socket("localhost", 8888);
		ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = conn.getOutputStream();
		uscita = new PrintStream(conn.getOutputStream(),true);
		tastiera = new BufferedReader(new InputStreamReader(System.in));
		close = 3;
		} catch(IOException ex) {
			System.out.println("IO Exception");
		}
	}
	public void invia() throws IOException{
		String messaggio="";
		connessione = true;
		while (connessione){
		if (close == 3){
			System.out.println("Inserisci il messaggio da inviare al server, - chiudi - in 3 messaggi consecutivi interrompono la connessione");
		}
		messaggio = tastiera.readLine();
		if (messaggio.equals("chiudi")){
			close--;
			if (close!=0)
			System.out.println("Scrivi chiudi ancora " + close + " volte per chiudere");

				if ( close == 0) {
					System.out.println("Connessione scaduta");
					connessione = false;
					reconnect();
				}
		} else close = 3;
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
					close = 3;
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