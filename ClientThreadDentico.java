package comunicazionethread;

import java.net.*;
import java.io.*;

public class Client {
	Socket conn;
	InputStreamReader in;
	OutputStream out;
	BufferedReader ingresso;
	PrintStream uscita;
	InputStreamReader tast;
	BufferedReader tastiera;

	public Client() {
		try {
			conn = new Socket("localhost", 5000);
			ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			out = conn.getOutputStream();
			uscita = new PrintStream(conn.getOutputStream(), true);
			tastiera = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Inserisci i messaggi da mandare al Server - chiudi termina la comunicazione.");
			System.out.print("Client: ");
		} catch (IOException ex) {
			System.out.println("IO Exception");
		}
	}

	public void invia() throws IOException {
		String messaggio = tastiera.readLine();
		if (messaggio.equals("chiudi")) {
			System.out.println("Comunicazione terminata.");
			conn.close();
			System.exit(0);
		}

		uscita.println(messaggio);
		System.out.println("Server: " + ingresso.readLine());
		System.out.print("Client: ");
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		while (true) {
			c.invia();
		}
	}
}
