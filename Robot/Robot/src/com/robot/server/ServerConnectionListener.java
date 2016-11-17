package com.robot.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import javax.swing.JOptionPane;

/**
 * Consente di soddisfare le richeiste continue di connessione dei client sino
 * al termine della risorsa di thread.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 * @see com.robot.server.ConcurrentTasksTrigger
 * @see com.robot.server.Server
 */
public class ServerConnectionListener implements Runnable {

    /**
     * L'endpoint del server.
     *
     * @see com.robot.server.Server
     */
    private final ServerSocket serverSocket;

    /**
     * Indica se pronto a partire ocon la comunicazione.
     */
    private boolean readyToStart;

    private Socket serverEndPoint;

    /**
     * Risorsa di thread.
     *
     * @see com.robot.server.Server
     */
    private final ExecutorService executorPool;

    /**
     * Lista dei socket connessi.
     */
    private static LinkedList<Socket> connectedSocket;
    /**
     * Timeout entro il quale se non c'è comunicazione il socket viene chiuso
     */

    private final int TIMEOUT = 300000; //5 minutes timeout

    /**
     * Costruttore.
     *
     * @param serverSocket endpoint
     * @param executorPool risorsa di thread
     */
    public ServerConnectionListener(ServerSocket serverSocket, ExecutorService executorPool) {
        readyToStart = false;
        connectedSocket = new LinkedList<>();
        this.serverSocket = serverSocket;
        this.executorPool = executorPool;
    }

    /**
     * Contiene ciò che si deve fare per accettare la richiesta di connessione
     * ed avviare la comunicazione.
     *
     * @see java.lang.Runnable
     */
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

    /**
     *
     * @return se è pronto a partire.
     */
    public synchronized boolean isReadyToStart() {
        return readyToStart;
    }

    /**
     * Termina la connessione.
     *
     * @param ip l'ip su cui terminare la connessione.
     */
    public static void abort(String ip) {
        connectedSocket.stream().filter((s) -> (s.getInetAddress().toString().equals(ip))).forEach((s) -> {
            try {
                s.close();
            } catch (IOException ex) {

            }
        });
    }

}
