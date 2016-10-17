package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 @author fabio.manfrin
 */
public class Echoserver {

	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(9090);
		System.out.println("running server");
                while(true){
		Socket socket = server.accept();
                PrintWriter out= new PrintWriter(socket.getOutputStream(), true); 
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int c=0;
			boolean stop=false;
			while (!stop) {					
					String s=input.readLine();
                                        if(s.equals("quit"))
                                            if(c==2){
                                                
                                                out.println("connessione chiusa");
                                               stop=true;
                                            }
                                            else{
                                                c++;

                                                out.println("[ECHO] " +s+"  c:"+c);
                                            }
                                        else{
                                            c=0;
                                            out.println("[ECHO] " +s+"  c:"+c);
                                        }
					



			}
                }
	}
}	
