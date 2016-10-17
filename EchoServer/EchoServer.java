package echoserver;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import echoserver.EchoThread;

/**
 * @author DAVIDE.BIANCAT
 */
public class EchoServer 
{

	final static int PORT = 9090;
	static int numThreads = 0;
	static int idThread = 0;
	static final int MAX_THREADS = 100;
	
    public static void main(String[] args)
	{
		try
		{
			ServerSocket listener = new ServerSocket(PORT);
			while(true)
			{
				System.out.println("[MAIN] Waiting connection");
				Socket clientSocket = listener.accept();
				if(numThreads<MAX_THREADS)//If i have available server threads start a new one to echo the client
					new EchoThread(clientSocket,idThread++,++numThreads).start();
				else
				{
					PrintStream os = new PrintStream(clientSocket.getOutputStream());
					os.println("[ERROR] Simultaneous connections limit reaced!");
					os.flush();
					System.out.println("[MAIN] Refused client connection: connections limit reaced(" + MAX_THREADS +").");
					clientSocket.close();
				}
			}
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
    }

}
