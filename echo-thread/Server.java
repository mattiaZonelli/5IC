/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_socket_thread;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**

 @author lucabassanello
 */
public class Server extends Thread {

	Server() {
		try {
			ServerSocket s = new ServerSocket(9999);
			System.out.println("Server partito");
			while (true) {
				Thread t = new Thread(new Utente(s.accept()));
				t.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();

	}

	public class Utente extends Thread {
		String messaggio = "";
		Socket conn = null;
		PrintWriter uscita = null;
		BufferedReader ingresso = null;

		Utente(Socket conn) throws IOException {
			uscita = new PrintWriter(conn.getOutputStream(), true);
			ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			this.conn = conn;
		}

		@Override
		public void run() {
			while (true) {
				try {
					messaggio = ingresso.readLine();
					if (messaggio.equals("chiudi")) {
						break;
					}
					System.out.println(messaggio);
					messaggio = "messaggio ricevuto";
					uscita.println(messaggio);
				} catch (IOException ex) {
				}
			}
		}
	}
}
