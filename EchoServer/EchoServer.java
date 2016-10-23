import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.lang.Thread;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
public class EchoServer extends Thread{
	ThreadPoolExecutor pooledThreads;
	ServerSocket echoServer;
	public EchoServer(int port){
		try{
			echoServer = new ServerSocket(port);
			System.out.println("Server creato!");
		}catch(IOException e){
			e.printStackTrace();
		}
		pooledThreads = new ThreadPoolExecutor(5,8,3,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4));
	}
	
	public ServerSocket getEchoServer(){
		return echoServer;
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
				/*System.out.println("PoolSize: "+pooledThreads.getPoolSize()						Debug per comprendere il funzionamento di ThreadPoolExecutor
									+"\nCorePoolSize: "+pooledThreads.getCorePoolSize()
									+"\nMaximumPoolSize: "+pooledThreads.getMaximumPoolSize()
									+"\nCodaTask: "+pooledThreads.getQueue().toString());*/
				System.out.println("In attesa di connessioni");
				clientSocket = echoServer.accept();
				System.out.println("Connesso "+clientSocket.getInetAddress());
				ClientListener singleClient = new ClientListener(clientSocket,exitString);
				pooledThreads.execute(singleClient);
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
