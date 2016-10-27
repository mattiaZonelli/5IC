package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 @author cabianca.leonardo
 */
public class ClientChat {

	String line;
	BufferedReader is, s1;
	PrintStream os;
	static boolean stop = false;

	public static void main(String[] args) throws IOException {
		ClientChat c = new ClientChat();
		c.collegamento();
	}

	public void collegamento() throws IOException {
		Socket socket = new Socket("127.0.0.1", 9999);
        System.out.println("sequenza di uscita : " + "1 - 2 - 3");
		is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		os = new PrintStream(socket.getOutputStream(), true);
		s1 = new BufferedReader(new InputStreamReader(System.in));
		String str;
		String received;

		while (!stop) {
			System.out.println("Digita:");
			str = s1.readLine();
			os.println(str);
			received = is.readLine();
			if (received.equals("close")) {
				stop = true;
			} else {
				System.out.println(received);
			}

		}
	}

}
