import java.net.Socket;
import java.lang.Exception.*;
import java.io.*;
public class EchoClient{
	public static void main(String[]args) throws Exception{
		try{
			Socket s = new Socket("127.0.0.1",9999);
			System.out.println("Per disconnetterti, ti basterà scrivere 3 volte la stessa parola");
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintStream out = new PrintStream(s.getOutputStream(),true);
			BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));		
			String msg = keyboard.readLine();
			boolean stopped = false;
			while(!stopped){
				out.println(msg);
				String line = in.readLine();
				if(line.equals("exit"))
					stopped = true;
				else{
					System.out.println(line);
					msg = keyboard.readLine();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
