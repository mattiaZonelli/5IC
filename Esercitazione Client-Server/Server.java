package esercitazione1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket ss= new ServerSocket(9999);
		Socket con;
		PrintWriter uscita;
		BufferedReader br;
		String mess = "";
		while (true) {
			con = ss.accept();
			uscita = new PrintWriter(con.getOutputStream(), true);
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while (true) {
				mess = br.readLine();
				System.out.println(mess);
				uscita.println("Messaggio Letto");
				if (mess == null) {
				 break;	
				}
					
			}
		}
	}
}
