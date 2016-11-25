package echoserver;
import java.net.*;
import java.io.*;
public class ServerCorretto 
{
	ServerSocket s;
	Socket conn;
	OutputStream out;
	PrintStream uscita;
	InputStream input;
	InputStreamReader in;
	BufferedReader ingresso;
	String messaggio;
	String msn;
	public ServerCorretto() 
	{
		try 
		{
			s = new ServerSocket(50000);
			conn = s.accept();
			out=conn.getOutputStream();
			uscita = new PrintStream(out);
			input=conn.getInputStream();
			in=new InputStreamReader(input);
			ingresso = new BufferedReader(in);
			messaggio = "messaggio ricevuto";
			msn="";
		} catch (IOException | NullPointerException ex) 
		{
			System.out.println("Errore");
		}
	}
	public void receive() throws IOException 
	{
		msn=ingresso.readLine();
		System.out.println(msn);
		uscita.println(messaggio);
	}
	public static void main(String[] args) throws IOException 
	{
		ServerCorretto c = new ServerCorretto();
		while (true) 
		{
			c.receive();
		}
	}
}
