package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoThread extends Thread
{
	private final Socket client;
	private final int id;
	Integer numThreads;
	
	PrintStream os;
	BufferedReader is;

	public EchoThread(Socket clientSocket,int id,Integer numThreads)
	{
		client = clientSocket;
		this.id = id;
		this.numThreads = numThreads;
		System.out.println("THREAD" + id + " CREATED!");
	}
	
	public void run()
	{ 
		String message;
		try 
		{//Echo client messages
			is = new BufferedReader(new InputStreamReader(client.getInputStream()));
			os = new PrintStream(client.getOutputStream());
			while(true)//Until an exception occurs (i hope)
			{
				System.out.println("[THREAD" + id + "] Waiting messages");
				message = is.readLine();
				System.out.println("[THREAD" + id + "] Received a message");
				//Thread.sleep(10);
				os.print("[ECHO] " + message + "\r\n");
				os.flush();
				System.out.println("[THREAD" + id +"] Echoed the message");
			}
			
		} 
		catch (IOException ex) 
		{
			System.out.print("[THREAD" + id +"]");
			ex.printStackTrace();
		} 
		/*catch (InterruptedException ex)
		{
			Logger.getLogger(EchoThread.class.getName()).log(Level.SEVERE, null, ex);
		}*/
		finally
		{
			try 
			{
				client.close();
			}
			catch (IOException ex)
			{
				System.out.print("[THREAD" + id +"]");
				ex.printStackTrace();
			}
			numThreads--;//Just before the thread dies, MUST be the LAST instruction
		}
	}
}
