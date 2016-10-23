package echo;

import java.io.*;
import java.net.*;
import java.lang.Thread;

/**
 @author bassanello.luca
 */

public class Server {
    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        BufferedReader input;
        PrintStream output;
		
		
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
            System.out.println("vai con: \"netcat 127.0.0.1 9999\"");
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept();		
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream());
		
            while (true) {                
				 
				 
				line=input.readLine(); 
                                output.println(line);
                                
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
