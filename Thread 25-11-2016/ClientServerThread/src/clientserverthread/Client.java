
package clientserverthread;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 *
 * @author Eleonora
 */

public class Client  extends Thread{
	Socket con;
	BufferedReader bReader;
	PrintWriter out;
        Scanner tastiera;
        
	public Client () throws IOException{
            con = new Socket("localhost", 9999);
		bReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		out = new PrintWriter(con.getOutputStream(), true ); 
                tastiera = new Scanner(System.in);
        }
                        

        @Override
	public void run() {
            try {
		String s;
		while (true) {
			s = tastiera.nextLine();
			if (s.equals("fine")) {
				break;
			}
			out.println(s);
			s = bReader.readLine();
			System.out.println(s);

		}
		con.close();
            }catch (IOException e ){
                e.printStackTrace();
            }
	}
        
        
        public static void main (String args []) throws IOException{
            Client c = new Client ();
            c.start();
        }

}

    
