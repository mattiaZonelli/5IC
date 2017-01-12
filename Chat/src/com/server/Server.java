package com.server;

import com.chat.Receiver;
import com.chat.Sender;
import com.database.*;
import com.protocol.Packet;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

public class Server {
    /**
     * La porta su cui il server ascolta.
     */
    private final int CLIENT_PORT = 9091;

    private final int SERVER_PORT = 9090;
    /**
     * Il numero di thread massimi per la risorsa.
     */

    private final int MAX_THREADS;
    /**
     * La risorsa di thread condivisa.
     *
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.Executors
     */

    private static ExecutorService executorPool;


    private DatagramSocket serverSocket;

    private boolean ended;

    private LinkedTransferQueue<Packet> storingQueue;

    private static Sender sender;

    private static Receiver receiver;


    public Server(int PORT) {
        ended = false;
        storingQueue = new LinkedTransferQueue<>();
        MAX_THREADS = 100;
        //this.PORT = PORT;

        try {
            serverSocket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        sender = new Sender(serverSocket, CLIENT_PORT, storingQueue);
        receiver = new Receiver(serverSocket, SERVER_PORT, storingQueue);
        executorPool = Executors.newWorkStealingPool(MAX_THREADS); // più efficiente e meno soggetto ad errori di FixedThreadPool e CachedThreadPool
        executorPool.execute(sender);
        executorPool.execute(receiver);
    }

    public Sender getSender() {
        return sender;
    }

    public static void updateIP(InetAddress ip, Field user) {
        DBConnector connector = new DBConnector("Users");
        DBWriter writer = connector.newDBWriter();
        Tuple t = new Tuple();
        t.addField(new Field("Ip_address", ip.toString().substring(1)));
        writer.update(t, "User_Data", "Username = '" + user.getValue() + "'");
        connector.abort();
    }

    public static boolean lookup(Field field) {
        DBConnector connector = new DBConnector("Users");
        DBReader reader = connector.newDBReader();
        boolean value = reader.exists(field, "User_Data");
        connector.abort();
        return value;
    }


    public static boolean insert(Tuple t) {
        DBConnector connector = new DBConnector("Users");
        DBReader reader = connector.newDBReader();
        //verifica che le due password corrispondano
        boolean exists = reader.exists(t.getField("Username"), "User_Data");
        if (!exists) {
            DBWriter writer = connector.newDBWriter();
            writer.write(t, "User_Data");
        }
        connector.abort();
        return exists;
    }



    public static String applyIP(Field field){
        DBConnector connector = new DBConnector("Users");
        DBReader reader = connector.newDBReader();
        Tuple t=new Tuple();
        reader.read(t,"Username = '"+field.getValue()+"'","User_Data");
        connector.abort();
        return (String) t.getField("Ip_address").getValue();
    }
    public static void sendUrgent(Packet packet) {
        sender.send(packet.getData(), packet.getIP());
    }

    public static void main(String[] args) {
        new Server(9090);
        while (true) {
        }
    }
}