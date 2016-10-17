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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Leonardo
 */
public class Server {


    public static void main(String args[]) {
        
        ServerSocket echoServer = null;
        String line;
        String exit="";
        BufferedReader is,s;
        PrintStream os;
        
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
            System.out.println("vai con: \"netcat 127.0.0.1 9999\"");
        } catch (IOException e) {
            System.out.println(e);
        }   
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            s = new BufferedReader((new InputStreamReader(System.in)));
                 int i=0;
            String str;
            while (true) {                               
                 System.out.println("Digita:");
                 str = s.readLine();
             
                
                 os.println(str);
                 line = is.readLine();
                 if (line.equals(exit)){
                     i++;
                 }
                 if (i==2){
                     clientSocket.close();
                 }
                 System.out.println("ECHO " + line); 
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
