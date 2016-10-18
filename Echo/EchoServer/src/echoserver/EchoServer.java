package echoserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author michael.cimino
 */
public class EchoServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
		String parola = "";
                boolean disc = true;
		int cont = 1;
		ServerSocket server = new ServerSocket(9999);
		System.out.println("Attesa connessione client...");
		Socket soc = server.accept();
		System.out.println("Client connesso!");
		ObjectInputStream input = new ObjectInputStream(soc.getInputStream());
		ObjectOutputStream Output = new ObjectOutputStream(soc.getOutputStream());
		Output.writeObject("Connected");
		while(!soc.isClosed() && cont !=3 && disc)
		{
			try
			{
				String temp = (String)input.readObject();
				if(temp.equals(parola))
					cont++;
				else
				{
					parola = temp;
					cont = 1;
				}
				Output.writeObject("Echo "+temp);
			}
			catch(java.net.SocketException e)
			{
				disc = false;
			}
		}
		try
		{
			Output.writeObject("Socket chiuso");
			soc.close();
		}
		catch(java.net.SocketException e)
		{
			System.out.println("Client crashed!");
		}
		
		System.out.println("Client disconnesso!");
	}

}
