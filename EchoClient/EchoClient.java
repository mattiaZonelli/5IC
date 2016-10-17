package echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author DAVIDE.BIANCAT
 */
public class EchoClient
{
	public static final int PORT = 9090;
	static String serverIP = "127.0.0.1";
	
    public static void main(String[] args) 
	{
        Socket socket;
		Scanner scan = new Scanner(System.in);
		while(true)//Finchè non mi connetto ad un server
		{
			System.out.print("Metti l' ip del server: ");
			try
			{
				socket = new Socket(scan.nextLine(),PORT);
				break;
			}
			catch (IOException e)
			{
				if(e.getMessage() != "Connection refused: connect")
					System.out.println("Server non raggiungibile o ip non valido!");
				else
					e.printStackTrace();
			}
		}
		System.out.println("Connesso!");
		try 
		{
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while(true)
			{
				System.out.print(">");
				os.print(scan.nextLine() + "\r\n");
				os.flush();
				System.out.println(is.readLine());
			}
		} 
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
    }

}
