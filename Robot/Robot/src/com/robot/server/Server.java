package com.robot.server;

import com.robot.swing.ServerGUI;
import com.robot.utils.database.DBConnector;
import com.robot.utils.database.DBWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**
 * L'istanza del server che avvia l'interfaccia grafica e le risorse comuni a
 * tutti i thread della risorsa.
 *
 * @author Nicola Pasqualetto
 * @version 1.1
 * @see com.robot.server.ConcurrentTasksTrigger
 * @see com.robot.server.ServerConnectionListener
 */
public final class Server {

    /**
     * Il listener che riceve le richieste di connessione in continuazione dai
     * client.
     */
    private final ServerConnectionListener connectionListener;
    /**
     * L'endpoint lato server della comunicazione.
     */

    private ServerSocket serverSocket;
    /**
     * La porta su cui il server ascolta.
     */

    private final int PORT;
    /**
     * Il numero di thread massimi per la risorsa.
     */

    private final int MAX_THREADS;
    /**
     * L'istanza dell'interfaccia grafica.
     */
    private static ServerGUI serverGUI;
    /**
     * La risorsa di thread condivisa.
     *
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.Executors
     */

    private static ExecutorService executorPool;

    /**
     * Costruttore.
     *
     * @param port la porta di ascolto
     */
    public Server(int port) {

        this.PORT = port;
        serverGUI = new ServerGUI();
        MAX_THREADS = 20;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server opening not possible. Another one is running on this pc.", "Error", JOptionPane.ERROR_MESSAGE);
            serverGUI.dispose();
        }

        executorPool = Executors.newWorkStealingPool(MAX_THREADS); // più efficiente e meno soggetto ad errori di FixedThreadPool e CachedThreadPool
        connectionListener = new ServerConnectionListener(serverSocket, executorPool);
        executorPool.execute(connectionListener);

    }

    /**
     * Aggiunge il client alla lista della GUI
     *
     * @param info l'ip del client
     */
    public static void addClientToList(String info) {
        serverGUI.getClientBox().addItem(info);

    }

    /**
     * Scrive nella maschera della GUI
     *
     * @param info messaggio
     */
    public static void log(String info) {
        serverGUI.getLogArea().append(info + "\n");
    }

    /**
     * Cancella i dati dal database dopo aver terminato tutte le connessioni.
     *
     * @see com.robot.utils.database.DBWriter
     */
    public static void deleteData() {
        executorPool.shutdown();
        DBConnector c = new DBConnector("users");
        DBWriter delete = c.newDBWriter();
        delete.empty("Socket_Info");
        delete.empty("UserData");

        Server.log("Shutting down server...");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "An error occured while deleting data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.exit(0);
    }

    /**
     * Metodo main
     *
     * @param args non usato
     */
    public static void main(String[] args) {
        Server s = new Server(9090);
    }

}
