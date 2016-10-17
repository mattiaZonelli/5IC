package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

//Fantuzzo Marco 5IC

public class Server {
	public static void main(String args[]) {

		ServerSocket echoServer = null;
		String line;
		BufferedReader is;
		PrintStream os;
		//String [] out= {"Chiudi", "Addio", "Arrivederci"};
		String out = "";
		Socket clientSocket = null;
		try {
			echoServer = new ServerSocket(9999);
			//System.out.println("Use \"netcat 127.0.0.1 9999\"");
		} catch (IOException e) {
			System.out.println(e);
		}
		try {
			clientSocket = echoServer.accept();
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //riceve			
			os = new PrintStream(clientSocket.getOutputStream()); //Invia
                        int cont = 0;
			while (true) {
				line = is.readLine();
                                if (line.equals(out))
                                    cont ++;
                                if (cont == 2)
                                    clientSocket.close();
                                Thread.sleep(1000);
                                os.println("ECHO " + line);
                                out = line;    
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (InterruptedException ie) {
			System.out.println(ie);
		}
		
	}
}
