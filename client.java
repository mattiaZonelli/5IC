/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author Leonardo
 */
public class client {

    /**
     * @param args the command line arguments
     */
        String line;
        BufferedReader is,s1;
        PrintStream os;
    
    public static void main(String[] args) throws IOException {
        client c = new client();
        c.collegamento();
    }
    
    public void collegamento() throws IOException{
        Socket socket = new Socket("127.0.0.1", 9999);
        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        os = new PrintStream(socket.getOutputStream());
        s1 = new BufferedReader(new InputStreamReader(System.in));
        String str;
        while (true) {
                 line= is.readLine();
                 System.out.println("Server:"+ line);
                 System.out.println("Digita:");
                 str = s1.readLine();
                 os.println(str);
            }
    }
}
