package serverecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * @author leonardo.issori
 */

public class ServerEcho
{

    public static void main(String[] args)
    {
        try
		{
			ServerSocket listener = new ServerSocket(9090);
			while(true)
			{
				System.out.println("MAIN: Waiting connection");
				Socket client = listener.accept();

				BufferedReader is;
				PrintStream os;

				String message;
				try
				{	//Echo client messages
					is = new BufferedReader(new InputStreamReader(client.getInputStream()));
					os = new PrintStream(client.getOutputStream());
					while(true)//Until an exception occurs (i hope)
					{
						message = is.readLine();
						os.print("ECHO: " + message + "\r\n");
						os.flush();
					}

				}
				catch (IOException ex)
				{
					System.out.println(ex.getStackTrace());

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
					} catch (IOException ex)
					{
						System.out.println(ex.getStackTrace());
					}
				}

			}

		}
		catch(IOException e)
		{
			System.out.println(e.getStackTrace());
		}
    }

}

