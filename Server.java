package comunicazione.client.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket server;
	Socket connection;
	PrintStream uscita;
	InputStreamReader in;
	BufferedReader ingresso;

	public Server() {
		try {
			server = new ServerSocket(50000);
			connection = server.accept();
			uscita = new PrintStream(connection.getOutputStream());
			in = new InputStreamReader(connection.getInputStream());
			ingresso = new BufferedReader(in);
		} catch (IOException ex) {
			System.out.println("Errore.");
		}
	}

	public void receive() throws IOException {
		String message = "";

		while (true) {
			message = ingresso.readLine();
			uscita.println(message);
		}
	}

	public static void main(String[] args) throws IOException {
		Server c = new Server();
		while (true) {
			c.receive();
		}
	}
}
