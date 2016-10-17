/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echozone;

/**
 *
 * @author MeijiTenno
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Server {

    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os; 
        String chiudi = "Arrivederci!";

        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
            System.out.println("Server");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (true) {
                
                line = is.readLine();
                if (line.equals(chiudi)){
                    os.println("chiudi");
                    clientSocket.close();
                }
                os.println("ECHO " + line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

