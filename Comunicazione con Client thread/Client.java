package esercitazione1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 @author mattia.zonelli
 */
public class Client {
	Socket con;
	BufferedReader br;
	PrintWriter out;
        Scanner tast;
        
	public Client () throws IOException{
            con = new Socket("localhost", 9999);
		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		out = new PrintWriter(con.getOutputStream(), true ); 
                tast = new Scanner(System.in);
        }
                        

        
	public void run() {
            try {
		String s = "";
		while (true) {
			s = tast.nextLine();
			if (s.equals("fine")) {
				break;
			}
			out.println(s);
			s = br.readLine();
			System.out.println(s);

		}
		con.close();
            }catch (IOException e ){
                e.printStackTrace();
            }
	}
        
        
        public static void main (String args []) throws IOException{
            Client c = new Client ();
            c.run();
        }

}
