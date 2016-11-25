package chatconthread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *Al posto di far estendere la classe Thread ho preferito implementare l'interfaccia Runnable
 *in modo tale da avere carta bianca sulla codifica e l'implementazione del metodo run.
 */
public class ServerConThreadDentico {

	public static void main(String[] args) throws IOException {
		final int port = 54444;
		System.out.println("Server in attesa di connessione sulla porta " + port);
		ServerSocket ss = new ServerSocket(port);
		Socket clientSocket;
		int i = 1;
		while (true) {
			clientSocket = ss.accept();
			System.out.println("Il client " + i + " collegato sul indirizzo " + clientSocket.getInetAddress() + " e sulla porta " + clientSocket.getPort());
			//con il metodo getInetAdres() visualizzo l indirizzo ip su cui ho collegato il socket al server
			RecieveFromClientThread recieve = new RecieveFromClientThread(clientSocket, i);
			Thread receiveMessage = new Thread(recieve);
			i++;
			receiveMessage.start();
			//In questo modo posso creare un thread ogni volta che un client si collega al server
			//inoltre incremento un contatore ogni volta che un client si collega in modo tale quant client si sono collegati
		}
	}
}

class RecieveFromClientThread implements Runnable {

	Socket clientSocket = null;
	BufferedReader input = null;
	ServerConThreadDentico s;
	int contatore;
	PrintWriter pwPrintWriter;

	public RecieveFromClientThread(Socket Socket, int contatore) {
		this.contatore = contatore;
		this.clientSocket = Socket;
		s = new ServerConThreadDentico();
	}

	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			pwPrintWriter = new PrintWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			String messageString;
			String msgToClientString = "messaggio ricevuto";
			while (true) {
				while ((messageString = input.readLine()) != null) {
					if (messageString.equalsIgnoreCase("esci")) {
						break;
					} else {
					}
					System.out.println("Messaggio dal client " + this.contatore + ": " + messageString);
					pwPrintWriter.println(msgToClientString);
					pwPrintWriter.flush();
				}
				this.clientSocket.shutdownOutput();
				this.clientSocket.shutdownInput();
				//con questi due metodi chiudo le comunicazioni con il socket client e soleva l'eccezzione 
			}
		} catch (IOException ex) {
			System.out.println("Il client " + this.contatore + ": disconnesso");
		}
	}
}
