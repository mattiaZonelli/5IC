package echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Echoclient {
	
	public static void main(String[] args) throws IOException {
		String serverAddress = "127.0.0.1";
		Socket s = new Socket(serverAddress, 9090);
		
		Scanner in=new Scanner(System.in);
		PrintStream ps;
		BufferedReader input;
		
		while(true){
		String str =in.nextLine();
		ps=new PrintStream(s.getOutputStream());
		ps.println(str);		
		input = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String answer = input.readLine();
		System.out.println(answer);
		}
	
	}
}
