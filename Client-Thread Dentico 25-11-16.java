package client.server.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author luca.forte
 */

public class Client {
	Socket connection;
	InputStreamReader in;
	OutputStream out;
	BufferedReader input1;
	PrintStream out1;
	InputStreamReader tast;
	BufferedReader keyboard;

	public Client() {
		try {
			connection = new Socket("localhost", 9000);
			input1 = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = connection.getOutputStream();
			out1 = new PrintStream(connection.getOutputStream(), true);
			keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Send message to server. \"quit\" to close connection.");
			System.out.print("Client: ");
		} catch (IOException ex) {
			System.out.println("IO Exception");
		}
	}

	public void send() throws IOException {
		String message = keyboard.readLine();
		if (message.equalsIgnoreCase("quit")) {
			System.out.println("Connection closed.");
			connection.close();
			System.exit(0);
		}

		out1.println(message);
		System.out.println("Server: " + input1.readLine());
		System.out.print("Client: ");
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		while (true) {
			client.send();
		}
	}
}
