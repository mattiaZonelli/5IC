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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matteo
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        boolean continua = true;
        int port = 9090;
        ServerSocket serverEcho = null;
        BufferedReader in = null;
        PrintStream out = null;

        //CREAZIONE SERVERSOCKET
        try {
            serverEcho = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Server operativo");

        Socket channel = new Socket();
        int i = 0;
        int j = 0;
        //INIZIALIZZAZIONE CANALE DI COMUNICAZIONE
        while (true && continua) {

            try {
                channel = serverEcho.accept();			//Connette il client
                System.out.println("Client connesso");
                in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
                out = new PrintStream(channel.getOutputStream(), true);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println(channel.getInetAddress());
            //int i = 0;
            while (true) {
                try {
                    String temp = in.readLine();
                    System.out.println("Stringa ricevuta: " + temp);

                    if (temp.equalsIgnoreCase("close")) {
                        i++;
                        /*  */
                    } else if (temp.equalsIgnoreCase("serverclose")) {
                        j++;
                        i = 0;
                    } else {
                        j = 0;
                        i = 0;
                    }
                    if (i == 3) {
                        channel = null;
                        out.println("Canale scollegato");
                        break;
                    }

                    if (j == 3) {
                        System.err.println("Server chiuso");
                        out.println("Canale Scollegato");
                        continua = false;
                        break;
                    }
                    Thread.sleep(1000);
                    out.println("Echo " + temp);

                } catch (java.net.SocketException e) {
                    System.err.println("Client disconnesso!");
                }

            }
        }

        System.err.println("Server spento");

    }
}
