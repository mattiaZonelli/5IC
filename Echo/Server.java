package echo;

import java.io.*;
import java.net.*;
import java.util.*;
public class Server {

	ServerSocket echoServer;
	Socket clientSocket;

	BufferedReader is;
	PrintStream os;
	String line;
	String sequence[] = {"1", "2", "3"};
	int index = 0;

	public static void main(String args[]) {
		Server server = new Server();
	}

	Server() {
		echoServer = null;
		clientSocket = null;

		try {
			echoServer = new ServerSocket(9999);
			System.out.println("SERVER CONNESSO");
		} catch (IOException e) {
			System.out.println("ERROR. CLOSE CONNECTION.");
		}

		while (true) {
			try {
				clientSocket = echoServer.accept();

				System.out.println("\nClient connesso.");

				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintStream(clientSocket.getOutputStream());

				while (true) {
					line = "ECHO " + is.readLine();
					line = checkSequences();
					os.println(line);
				}
			} catch (IOException e) {
				System.out.println("Client disconnesso.\n");
			}
		}
	}

	public String checkSequences() {
		line = line.substring(5);
		if (index == 2) {
			index = 0;
			line = "STOP";
		} else {
			System.out.println(line.equals(sequence[index]));
			if (line.equals(sequence[index])) {
				index++;
			}else{
				index = 0;
				System.out.println(index);
			}
		}

		return line;
	}
}
