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
 * @author leonardo.cabianca
 */
public class Server {
	static ServerSocket s;
	static Socket conn= null;
	static OutputStream out= null;
	static PrintWriter uscita= null;
	static InputStream input= null;
	static InputStreamReader in = null;
	static BufferedReader ingresso=null;
	static boolean stop=false;
	
	
	
	public Server() throws IOException{
		this.s =new ServerSocket(9999);
		
	}
	public static void Connection() throws IOException{
		String mess="";
		
		while(true){
			conn=s.accept();
			uscita=new PrintWriter(conn.getOutputStream(),true);		
			ingresso=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while(!stop){
				mess=ingresso.readLine();
				if(mess==null){
					break;
				}
				System.out.println(mess);
				mess="messaggio letto";
				uscita.println(mess);
				
			}
		}
	}
	
    public static void main(String[] args) throws IOException {
        Server server=new Server();
		Connection();
    }

}
