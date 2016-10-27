import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

// Fantuzzo Marco 5IC

public class Server {
	String[] out = {"Arrivederci", "Ciao", "Addio"}; //sequenza chiusura
	String[] buffer = {"", "", ""};
	int i = 0;
	ServerSocket echoServer = null;
	Socket clientSocket = null;
	String line;
	BufferedReader is;
	PrintStream os;
	boolean continua = true;   //Finchè != false il server accetta quello che invia il client

	Server() {		
		try {
			echoServer = new ServerSocket(9999);
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			clientSocket = echoServer.accept();
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream(), true);
			
			while (continua) {
				line = is.readLine();
				if (line.equals(out[i])) {
					buffer[i] = line;
					i++;
				} else {
					i = 0;
				}
				if (i == 3 && buffer[2].equals(out[2])) {
					continua = false;
					os.println("chiudiComunicazione");
					clientSocket.close();
				} else {
					os.println("ECHO " + line);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) {
		Server server = new Server();
	}
}

