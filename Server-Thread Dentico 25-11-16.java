package client.server.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author luca.forte
 */

public class Server {
	public static void main(String[] args) throws IOException {
		final int port = 9000;
		System.out.println("Server listening on port " + port + ".");
		ServerSocket ss = new ServerSocket(port);
		Socket clientSocket;
		int i = 1;
		while (true) {
			clientSocket = ss.accept();
			System.out.println("Connected with " + clientSocket.getInetAddress() + " on port " + clientSocket.getPort() + ".");
			ServerThread s = new ServerThread(i, clientSocket);
			Thread t = new Thread(s);
			i++;
			t.start();
		}
	}
}

class ServerThread implements Runnable {
	Socket clientSocket;
	int clientId;

	public ServerThread(int clientId, Socket s) {
		this.clientSocket = s;
		this.clientId = clientId;
	}

	@Override
	public void run() {
		RicezioneClient ricezione = new RicezioneClient(this.clientSocket, this.clientId);
		Thread riceviMessaggio = new Thread(ricezione);
		riceviMessaggio.start();
	}
}

class RicezioneClient implements Runnable {
	Socket clientSocket = null;
	BufferedReader input = null;
	Server server;
	int clientId;
	PrintWriter write;

	public RicezioneClient(Socket Socket, int clientId) {
		this.clientId = clientId;
		this.clientSocket = Socket;
		server = new Server();
	}

	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			write = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			String message;
			String confermaMessaggio = "received message from client.";
			while (true) {
				while ((message = input.readLine()) != null) {
					if (message.equalsIgnoreCase("quit")) {
						break;
					}
					System.out.println("Client " + this.clientId + ": " + message);
					write.println(confermaMessaggio);
					write.flush();
				}
				clientSocket.shutdownInput();
				clientSocket.shutdownOutput();
			}
		} catch (IOException ex) {
			System.out.println("Client disconnected - ID: "+ clientId +".");
		}
	}
}
