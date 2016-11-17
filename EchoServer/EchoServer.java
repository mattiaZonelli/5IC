package echoserver;

import java.net.*;
import java.io.*;

public class EchoServer {
	 ServerSocket s;
	 Socket conn;
	 PrintStream uscita;
	 InputStreamReader in;
	 BufferedReader ingresso;

	public EchoServer(){
		try {
		s = new ServerSocket(8888);
		System.out.println("Server pronto...");
		conn = s.accept();
		System.out.println("Ricevuta la connessione del Client");
		uscita = new PrintStream(conn.getOutputStream());
		in = new InputStreamReader(conn.getInputStream());
		ingresso = new BufferedReader(in);
			} catch(IOException ex){
				System.out.println("IO Exception");
			}
	}

	public void ricevi() throws IOException{
		String messaggio = "";
		System.out.println("Server pronto a ricevere: ");
		while(true){
		messaggio = ingresso.readLine();
			uscita.println(messaggio);
		}
	}

	public static void main(String[] args) throws IOException {
		EchoServer s = new EchoServer();
		while(true){
			s.ricevi();
		}
}

}
