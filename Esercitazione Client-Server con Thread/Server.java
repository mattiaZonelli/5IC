package esercitazione1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	

	Server() throws IOException {
		ServerSocket ss = new ServerSocket(9999);
		System.out.println("Server Started");
		int ID = 0;
		while (true) {
			Thread t = new Thread(new User(ss.accept(),ID++));
			t.start();
		}
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();
	}

	public class User implements Runnable {

		Socket s;
		String mess = "";
		PrintWriter uscita;
		BufferedReader br;
		int ID;

		public User(Socket s, int ID) {
			this.s = s;
			this.ID = ID;
		}

		@Override
		public void run() {
			try {
				uscita = new PrintWriter(s.getOutputStream(), true);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				while (true) {
					mess = br.readLine();
					System.out.println("Client[" + ID + "]: " + mess);
					uscita.println("Messaggio Letto");
					if (mess == null) {
						break;
					}

				}
			} catch (IOException ex) {
			}

		}

	}
}
