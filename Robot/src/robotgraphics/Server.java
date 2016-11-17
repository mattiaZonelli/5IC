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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Swapnil Paul
 */
public class Server extends Thread {

    private String hostName;
    private int port;
    private ServerSocket serverSocket;
    private FXMLServerController controller;

    /**
     *
     * @param controller
     */
    public Server(FXMLServerController controller) {
        this.hostName = "127.0.0.1";
        this.port = 9090;
        this.controller = controller;
    }

    /**
     *
     */
    public Server() {
        this.hostName = "127.0.0.1";
        this.port = 9090;
    }

    /**
     *
     * @param hostName
     * @param port
     * @param controller
     */
    public Server(String hostName, int port, FXMLServerController controller) {
        this.hostName = hostName;
        this.port = port;
        this.controller = controller;
    }

    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        String output = ".";
        int nClient = 0;
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("Starting server at port " + port + "  " + serverSocket.toString());
            output = "Starting server at port " + port + "  " + serverSocket.toString();
            controller.print(output);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                if (clientSocket.isConnected()) {
                    nClient++;
                    System.err.println("Connessione stabilita con client numero: " + nClient + "  " + clientSocket.toString());
                    controller.print("Connessione stabilita con client numero: " + nClient + "  " + clientSocket.toString());
                    Callable<Void> task = new RobotTask(clientSocket, controller);
                    pool.submit(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private static class RobotTask implements Callable<Void> {

        private Socket clientSocket;
        private FXMLServerController controller;

        public RobotTask(Socket connection, FXMLServerController controller) {
            this.clientSocket = connection;
            this.controller = controller;
        }

        @Override
        public Void call() throws Exception {
            PrintStream toClient = new PrintStream(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean close = false;
            boolean shutdown = false;
            String outputLine;

            Protocol kkp = new Protocol();
            outputLine = kkp.processInput(null);
            toClient.println(outputLine);

            String clientString;
            while (!shutdown || close) {
                clientString = fromClient.readLine().toLowerCase();
                if (clientString.equalsIgnoreCase("shutdown")) {
                    shutdown = true;
                    System.err.println("Server in stato di chiusura");
                }
                if (clientString.equalsIgnoreCase("chiudi")) {
                    close = true;
                }
                outputLine = kkp.processInput(clientString);
                toClient.println(outputLine);
            }
            if (shutdown) {
                clientSocket.close();
                System.exit(1);
            }
            if (close) {
                controller.print("Un Client si è disconesso!");
            }
            return null;
        }
    }

    /* public static void main(String[] args) throws IOException {
        Server s = new Server();
        s.start();
        // s.startServer();
        }*/
}
