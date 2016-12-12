/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emsrescue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matteo Incan
 */
public class Client {

    public static void main(String[] args) {
        PrintStream out = null;                                                     //canale in uscita
        BufferedReader input = null;                                                //canale in entrata

        Socket clientSocket = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); //standard input

        String address = "127.0.0.1";
        int port = 9090;

        try {
            clientSocket = new Socket(address, port);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintStream(clientSocket.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Connesso al server " + address);

        while (true) {
            try {
                System.out.print("Messaggio: ");
                String temp;
                temp = in.readLine();
                out.println(temp);
                String recived = input.readLine();
                if (recived.equalsIgnoreCase("Server spento")) {
                    System.err.println("Server spento come richiesto");
                    break;
                }

                if (recived.equalsIgnoreCase("Canale Scollegato")) {
                    System.err.println("Canale scollegato come richiesto");
                    break;
                }
                System.out.println("\n: " + recived);

            } catch (java.net.SocketException e) {
                System.err.println("Server disconnesso.");
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);

            }
            System.out.println(clientSocket.isConnected());
        }

    }
}
