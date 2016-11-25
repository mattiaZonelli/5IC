package echoserver;
/*
 *Author Mazzucchi Mariano
*/
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ClientCorretto 
{
	Socket conn;
	BufferedReader ingresso;
	PrintStream uscita;
	OutputStream out;
	BufferedReader tastiera;
	public ClientCorretto() 
	{
		try 
		{
			conn = new Socket("localhost", 50000);
			ingresso = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			out = conn.getOutputStream();
			uscita = new PrintStream(conn.getOutputStream(), true);
			tastiera = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException ex) 
		{
			System.out.println("IO Exception");
		}
	}
	public void send() 
	{
		String messaggio = "";
		System.out.println("Inserisci il messaggio da inviare al server, per interrompere scrivere fine");
		while (true) 
		{
			try 
			{
				messaggio = tastiera.readLine();
				//System.out.println(messaggio);
			} catch (IOException ex) 
			{
				Logger.getLogger(ClientCorretto.class.getName()).log(Level.SEVERE, null, ex);
			}
			if (messaggio.equals("fine")) 
			{
				System.out.println("Connessione scaduta");
				try 
				{
					conn.close();
					break;
				} catch (IOException ex) 
				{
					System.out.println("Connessione scaduta");
					Logger.getLogger(ClientCorretto.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			uscita.println(messaggio);
			try 
			{
				System.out.println("Dal server: " + ingresso.readLine());
				uscita.flush();
			} catch (IOException ex) 
			{
				Logger.getLogger(ClientCorretto.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	public static void main(String[] args) throws IOException 
	{
		ClientCorretto c = new ClientCorretto();
		c.send();
	}
}
