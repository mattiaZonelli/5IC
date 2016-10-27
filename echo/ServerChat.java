package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**

 @author cabianca.leonardo
 */
public class ServerChat {

	String[] out = {"1", "2", "3"}; //sequenza di uscita
	String[] app = {"", "", ""};
	int i = 0;
	ServerSocket echoServer = null;
	Socket clientSocket = null;
	String line;
	BufferedReader is;
	PrintStream os;
	boolean gate = true;   //condizione di entrata nel ciclo

	public void check() throws IOException{
		if (line.equals(out[i])) {
					app[i] = line;
					i++;
				} else {
					i = 0;
				}
				if (i == 3 && app[2].equals(out[2])) {
					gate = false;
					os.println("close");
					clientSocket.close();
				} else {
					os.println("ECHO " + line);
				}
	}

	ServerChat() {
		try {
			echoServer = new ServerSocket(9999);
                        System.out.println("Avvio del Server");
                        System.out.println("Continuare nel ClientChat!");
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			clientSocket = echoServer.accept();
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream(), true);

			while (gate) {
				line = is.readLine();
				check();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String args[]) {
		ServerChat server = new ServerChat();
	}
}

