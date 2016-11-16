package com.robot.server;

import com.robot.swing.ServerGUI;
import com.utils.database.DBConnector;
import com.utils.database.DBWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JOptionPane;

/**Manca:
gestione se già proposto*
* gestione pulsante disconnetti*
* client richiesta ip e porta*
* inserire testo client in rettangoli o con formato migliore*
* commentare e documentazione
* gestione domande se possibile
* look&feel*
popolare db

 * Created by Nicola on 24/10/2016.
 */
public final class Server {

    private final ServerConnectionListener connectionListener;

    private ServerSocket serverSocket;

    private final int PORT;

    private final int MAX_THREADS;
    
    private static ServerGUI serverGUI;

    private static ExecutorService executorPool;

    public Server(int port) {

        this.PORT = port;
        serverGUI=new ServerGUI();
        MAX_THREADS = 20;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server opening not possible. Another one is running on this pc.", "Error", JOptionPane.ERROR_MESSAGE);
            serverGUI.dispose();
        }

        executorPool = Executors.newWorkStealingPool(MAX_THREADS);
        connectionListener = new ServerConnectionListener(serverSocket, executorPool);
        executorPool.execute(connectionListener);

    }
    
    public static void addClientToList(String info){
        serverGUI.getClientBox().addItem(info);
        
    }
    
    public static void log(String info){
        serverGUI.getLogArea().append(info+"\n");
    }
    
    public static void deleteData(){
        executorPool.shutdown();
        DBConnector c=new DBConnector("users");
        DBWriter delete=c.newDBWriter();
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

    public static void main(String[] args) {
        Server s = new Server(9090);
    }

}
