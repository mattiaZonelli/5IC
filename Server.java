package echoserver;
/*
   author COLLAUTO ALVISE
*/
import java.net.*;
import java.io.*;

public class Server {
	ServerSocket s;
	Socket conn;
	OutputStream out;
	PrintStream uscita;
	InputStream input;
	InputStreamReader in;
	BufferedReader ingresso;
	
	public Server(){
		try {
			s = new ServerSocket(50000);
			conn = s.accept();
			out = conn.getOutputStream();
			uscita = new PrintStream(out);
			input = conn.getInputStream();
			in = new InputStreamReader(input);
			ingresso = new BufferedReader(in);	
		} catch(IOException ex){
			System.out.println("Errore");
		}
	}
	
	public void receive() throws IOException{
		String messaggio = "";
		while(true){
			messaggio = ingresso.readLine();
			uscita.println(messaggio);
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server c = new Server();
		while(true){
			c.receive();
		}
	}	
}