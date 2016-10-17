import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
public class EchoClient{
	public static void main(String[]args) throws Exception{
		try{
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Inserire l'ip del server: ");
			String ip = keyboard.readLine();
			System.out.println();
			System.out.print("Inserire porta di connessione: ");
			int port = Integer.parseInt(keyboard.readLine());
			Socket s = new Socket(ip,port);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintStream out = new PrintStream(s.getOutputStream(),true);
			System.out.println("Per chiudere le connessione, basterà inviare al server le parole: \""+in.readLine()+"\" singolarmente");
			String line = null;
			String msg = "";
			boolean stopped = false;
			while(!stopped){
				msg = keyboard.readLine();
				out.println(msg);
				line = in.readLine();
				if(line.charAt(line.length()-1) == (char)0)
					stopped = true;
				System.out.println(line);
			}
			System.out.println("Comunicazione Terminata!");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
