package esercitazione.pkg1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 @author leonardo.cabianca
 */
public class Client {
	static Socket conn;
	static InputStreamReader in = null, tast = null;
	static BufferedReader ingresso = null, tastiera = null;
	static InputStream input;
	static OutputStream out = null;
	static PrintWriter uscita = null;

	public Client() throws IOException {
		this.conn = new Socket("localhost", 9999);
	}

	public static void Check() throws IOException {
		ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		uscita = new PrintWriter(conn.getOutputStream(),true);
		tastiera = new BufferedReader(new InputStreamReader(System.in));
		String messaggio = "";
		String usci;
		while (true) {

			System.out.println("Inserisci il messaggio: ");
			messaggio = tastiera.readLine();

			if (messaggio.equals("fine")) {
				break;
			}
			uscita.println(messaggio);
			uscita.flush();
			usci = ingresso.readLine();
			System.out.println(usci);
		}
		conn.close();
	}

	public static void main(String[] args) throws IOException {
		Client client=new Client();
		Check();
	}

}