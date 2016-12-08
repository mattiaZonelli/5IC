import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// Fantuzzo Marco 5IC

public class Client {
	Socket conn;
	BufferedReader ingresso;
	PrintWriter out;	

	public void run() throws IOException {
                int port = 9999;
		conn = new Socket("localhost", port);
		ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		out = new PrintWriter(conn.getOutputStream(), true );
		Scanner tast;
		tast = new Scanner(System.in);
		String messaggio = "";
		while (true) {
			messaggio = tast.nextLine();
			if (messaggio.equalsIgnoreCase("fine")) {
				break;
			}
			out.println(messaggio);
			messaggio = ingresso.readLine();
			System.out.println(messaggio);
		}
		conn.close();
	}
        
        public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.run();
        
        }
}
