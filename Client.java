package comunicazione.client.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
	Socket connection;
	InputStreamReader in;
	OutputStream out;
	BufferedReader ingresso;
	PrintStream uscita;
	InputStreamReader tast;
	BufferedReader keyboard;

	public Client() {
		try {
			connection = new Socket("localhost", 50000);
			ingresso = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = connection.getOutputStream();
			uscita = new PrintStream(connection.getOutputStream(), true);
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("MESSAGGIO DA INVIARE AL SERVER: ");
			System.out.print("CLIENT: ");
		} catch (IOException ex) {
			System.out.println("IO Exception");
		}
	}

	public void send() throws IOException {
		String message = keyboard.readLine();

		if (message.equalsIgnoreCase("chiudi")) {
			System.out.println("TERMINATO.");
			connection.close();
			System.exit(0);
		}

		uscita.println(message);
		System.out.println("SERVER: " + ingresso.readLine());
		System.out.print("CLIENT: ");
	}

	public static void main(String[] args) throws IOException {
		Client c = new Client();
		while (true) {
			c.send();
		}
	}
}
