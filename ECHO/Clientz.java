/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echozone;

/**

 @author MeijiTenno
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 @author zonelli.mattia
 */
public class Clientz {
	String line;
	BufferedReader is;
	PrintWriter os;
	BufferedReader read;
	

	public static void main(String[] args) throws IOException {
		Clientz client = new Clientz();
		client.run();
	}

	private void run() throws IOException {
		Socket socket = new Socket("127.0.0.1", 9999);
		is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		os = new PrintWriter(socket.getOutputStream(), true);
		read = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Sono il client");
		boolean stopped = false;
		line ="";
		while (!stopped) {
			
			os.println(read.readLine());
			line = is.readLine();
			if (line.equals("Chiudi")) {
				stopped = true;
			} else {
				System.out.println(line);
			}

		}
		System.out.println("Disconesso");
	}

}
