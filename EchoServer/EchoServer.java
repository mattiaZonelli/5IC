import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;
public class EchoServer extends Thread{
	List <ClientListener> clients;
	ServerSocket echoServer;
	public EchoServer(int port){
		try{
			echoServer = new ServerSocket(port);
			System.out.println("Server creato!");
		}catch(IOException e){
			e.printStackTrace();
		}
		clients = new ArrayList<>();
	}
	
	public ServerSocket getEchoServer(){
		return echoServer;
	}
	
	public void removeClientListener(ClientListener c){
		clients.remove(c);
	}
	
	public void run(){
		BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Scrivi la sequenza la disconnessione di un socket (parole separate da spazi)");
		String exitString = null;
		try{
			 exitString= keyboard.readLine();
		 }catch(IOException e){
			 e.printStackTrace();
		 }  
		while(true){
			Socket clientSocket = null;
			try {
				System.out.println("In attesa di connessioni");
				clientSocket = echoServer.accept();
				System.out.println("Connesso "+clientSocket.getInetAddress());
				ClientListener singleClient = new ClientListener(clientSocket,exitString,this);
				clients.add(singleClient);
				singleClient.start();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
    public static void main(String args[]) {
      EchoServer server = null;
      BufferedReader keyboard;
      int port;
      try{
		  keyboard = new BufferedReader(new InputStreamReader(System.in));
		  System.out.print("Inserisci la porta sulla quale mettere il server in ascolto: ");
		  port = Integer.parseInt(keyboard.readLine());
		  server = new EchoServer(port);
	  }catch(IOException e){
		  e.printStackTrace();
	  }
	  server.start();
    }
}
