package echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author michael.cimino
 */
public class EchoClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
		Socket soc = new Socket("localhost",9999);
		ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
		ObjectInputStream inS = new ObjectInputStream(soc.getInputStream());
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String temp = (String) inS.readObject();
		System.out.println(temp);
		while(true)
		{
			try
			{
				out.writeObject(in.readLine());
				temp = (String) inS.readObject();
				System.out.println(temp);
			}
			catch(java.net.SocketException e)
			{
				break;
			}
		}
		System.out.println("Server disconnesso");
    }

}
