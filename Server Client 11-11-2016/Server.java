package esercitazione.pkg1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author garbin eleonora
 */
public class Server {
	static ServerSocket s;
	static Socket connessione= null;
	static OutputStream out= null;
	static PrintWriter uscita= null;
	static InputStream input= null;
	static InputStreamReader in = null;
	static BufferedReader ingresso=null;
	static boolean stop=false;
	
	
	
	public Server() throws IOException{
		Server.s =new ServerSocket(9999);
		
	}
	public static void Connection() throws IOException{
		String messaggio;
		
		while(true){
			connessione=s.accept();
			uscita=new PrintWriter(connessione.getOutputStream(),true);		
			ingresso=new BufferedReader(new InputStreamReader(connessione.getInputStream()));
			while(!stop){
				messaggio=ingresso.readLine();
				if(messaggio==null){
					break;
				}
				System.out.println(messaggio);
				messaggio="messaggio letto";
				uscita.println(messaggio);
				
			}
		}
	}
	
    public static void main(String[] args) throws IOException {
        Server server = new Server();
		Connection();
    }

}
