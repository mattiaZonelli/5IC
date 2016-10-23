import java.net.Socket;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
public class EchoClient{
	Socket s;
	BufferedReader keyboard;
	BufferedReader in;
	PrintStream out;
	boolean stopped;
	public EchoClient(String ip, int port,BufferedReader keyboard){
		this.keyboard = keyboard;
		stopped = false;
		try{
		s = new Socket(ip,port);
		}catch(UnknownHostException e){
			System.out.println("Host sconosciuto!");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public boolean getStopped(){
		return stopped;
	}
	
	public void setStopped(){
		stopped = true;
	}
	
	public void comunicate(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintStream out = new PrintStream(s.getOutputStream(),true);
			System.out.println("Per chiudere le connessione, basterà inviare al server le parole: \""+in.readLine()+"\" singolarmente");
			String line = null;
			String msg = "";
			while(!getStopped()){
				msg = keyboard.readLine();
				out.println(msg);
				line = in.readLine();
				if(line.charAt(line.length()-1) == (char)0)
					setStopped();
				System.out.println(line);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
	public static void main(String[]args) throws Exception{
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Inserire l'ip del server: ");
			String ip = keyboard.readLine();
			System.out.println();
			System.out.print("Inserire porta di connessione: ");
			int port = Integer.parseInt(keyboard.readLine());
			EchoClient client = new EchoClient(ip,port,keyboard);
			client.comunicate();
			System.out.println("Comunicazione Terminata!");
			
	}
}

