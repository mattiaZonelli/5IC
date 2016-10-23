package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 @author bassanello.luca
 */
public class Client {
	String line;
	BufferedReader input;
	PrintStream output;

	public static void main(String[] args) throws IOException {
		Client client = new Client();
		client.run();
	}

	private void run() throws IOException {
		Socket socket = new Socket("127.0.0.1", 9999);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintStream(socket.getOutputStream());
BufferedReader read= new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			 System.out.print("Digita: ");
				 String s = read.readLine();
                                 output.println(s);
                                 System.out.println(input.readLine());
		}
	}

}
