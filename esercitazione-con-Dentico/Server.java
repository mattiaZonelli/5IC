import java.io.*;
import java.net.*;

/**

 @author lucabassanello
 */
public class Server {
	public static void main(String[] args) throws IOException {
		try {
			
		
			ServerSocket s = new ServerSocket(9999);
			String messaggio = "";
			
			Socket conn = null;
			
			
			PrintWriter uscita=null;
			
			BufferedReader ingresso=null;
			
			while (true) {
				conn = s.accept();
						uscita=new PrintWriter(conn.getOutputStream(),true);		
			ingresso=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while (true) {
					messaggio=ingresso.readLine();
					if(messaggio==null){
						
					break;
					}
					System.out.println(messaggio);
					messaggio="messaggio ricevuto";
					uscita.println(messaggio);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
