/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. This software is created by Swapnil Paul
 */
package robotgraphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Swapnil Paul
 */
public class Client extends Thread {

    private String hostName;
    private int portNumber;
    private FXMLClientController controller;
    private PrintStream outToServer;
    private Socket server;

    /**
     *
     * @param hostaName
     * @param portNumeber
     * @param controller
     */
    public Client(String hostaName, int portNumeber, FXMLClientController controller) {
        this.hostName = hostaName;
        this.portNumber = portNumeber;
        this.controller = controller;
    }

    /**
     *
     */
    public Client() {
        this.hostName = "127.0.0.1";
        this.portNumber = 9090;
    }

    /**
     *
     * @param controller
     */
    public Client(FXMLClientController controller) {
        this.hostName = "127.0.0.1";
        this.portNumber = 9090;
        this.controller = controller;
    }

    @Override
    public void run() {
        server = null;
        try {
            server = new Socket(hostName, portNumber);
            System.out.println("Connesione stabilita con il server - " + hostName + " " + portNumber);
            controller.print("Connesione stabilita con il server - " + hostName + " " + portNumber);
            BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            //BufferedReader inUser = new BufferedReader(new InputStreamReader(System.in));
            outToServer = new PrintStream(server.getOutputStream(), true); // with autoflush
            String fromServer;
            String formatedString = "";
            while (in != null) {
                fromServer = in.readLine();
                formatedString = fromServer.replace("§", "\n");
                System.out.println(formatedString);
                controller.print(formatedString);
            }
        } catch (IOException ex) {
            System.err.println("Server is closed");
            controller.print("Server is closed");
        }
    }

    /**
     *
     * @param fromUser
     */
    public void sendMessage(String fromUser) {
        if (fromUser.equalsIgnoreCase("chiudi")) {
            try {
                outToServer.println(fromUser);
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (!fromUser.equals("") || fromUser != null) {
            outToServer.println(fromUser);
        }

    }

    /*public static void main(String[] args) {
        Client c = new Client();
        c.start();
    }*/
}
