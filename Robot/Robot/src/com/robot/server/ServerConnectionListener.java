package com.robot.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Created by Nicola on 24/10/2016.
 */
public class ServerConnectionListener implements Runnable {

    private final ServerSocket serverSocket;

    private boolean readyToStart;

    private Socket serverEndPoint;

    private final ExecutorService executorPool;

    private static LinkedList<Socket> connectedSocket;

    private final int TIMEOUT = 300000; //5 minutes timeout

    public ServerConnectionListener(ServerSocket serverSocket, ExecutorService executorPool) {
        readyToStart = false;
        connectedSocket = new LinkedList<>();
        this.serverSocket = serverSocket;
        this.executorPool = executorPool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Server avviato");
                Server.log("Server started... waiting for connections");
                serverEndPoint = serverSocket.accept();
                serverEndPoint.setSoTimeout(TIMEOUT);
                connectedSocket.add(serverEndPoint);// clone
                Server.log("Connection with " + serverEndPoint.getInetAddress() + ":" + serverEndPoint.getPort());
                Server.addClientToList(serverEndPoint.getInetAddress().toString());
                ConcurrentTasksTrigger tasks = new ConcurrentTasksTrigger(serverEndPoint, serverEndPoint.getOutputStream(), serverEndPoint.getInputStream());
                executorPool.execute(tasks);
                readyToStart = true;
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public synchronized boolean isReadyToStart() {
        return readyToStart;
    }

    public static void abort(String ip) {
        connectedSocket.stream().filter((s) -> (s.getInetAddress().toString().equals(ip))).forEach((s) -> {
            try {
                s.close();
            } catch (IOException ex) {

            }
        });
    }

}
