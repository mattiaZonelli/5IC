package comunicazionethread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		final int port = 5000;
		System.out.println("Server in attesa sulla porta " + port);
		ServerSocket ss = new ServerSocket(port);
		Socket clientSocket;
		int i = 1;
		while (true) {
			clientSocket = ss.accept();
			System.out.println("collegamento ricevuto da " + clientSocket.getInetAddress() + " sulla porta " + clientSocket.getPort());
			ServerThread s = new ServerThread(i, clientSocket);
			Thread t = new Thread(s);
			i++;
			t.start();
		}
	}
}

class ServerThread implements Runnable {
	Socket clientSocket;
	int idClient;

	public ServerThread(int idClient, Socket s) {
		this.clientSocket = s;
		this.idClient = idClient;
	}

	@Override
	public void run() {
		RicezioneClient ricezione = new RicezioneClient(this.clientSocket, this.idClient);
		Thread riceviMessaggio = new Thread(ricezione);
		riceviMessaggio.start();
	}
}

class RicezioneClient implements Runnable {
	Socket clientSocket = null;
	BufferedReader input = null;
	Server s;
	int idClient;
	PrintWriter scrivi;

	public RicezioneClient(Socket Socket, int idClient) {
		this.idClient = idClient;
		this.clientSocket = Socket;
		s = new Server();
	}

	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			scrivi = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			String messaggioLetto;
			String confermaMessaggio = "messaggio ricevuto dal Client";
			while (true) {
				while ((messaggioLetto = input.readLine()) != null) {
					if (messaggioLetto.equalsIgnoreCase("chiudi")) {
						break;
					}
					System.out.println("Client " + this.idClient + ": " + messaggioLetto);
					scrivi.println(confermaMessaggio);
					scrivi.flush();
				}
				clientSocket.shutdownInput();
				clientSocket.shutdownOutput();
			}
		} catch (IOException ex) {
			System.out.println("Il client con ID " + idClient + " si è disconnesso.");
		}
	}
}
