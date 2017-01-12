package com.client;

import com.chat.ClientReceiver;
import com.chat.ClientSender;
import com.database.Field;
import com.graphics.SignIn;
import com.protocol.Packet;

import javax.swing.*;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Nicola on 23/11/2016.
 */
public class Client {

    private final int PORT;
    private final int MAX_THREADS;
    private static ExecutorService executorPool;


    private DatagramSocket socket;

    private boolean ended;


    private ClientSender sender;

    private ClientReceiver receiver;

    public boolean idle;

    public String SERVER_ADDRESS;

    public final int SERVER_PORT = 9090;

    public Field controlField;

    public String username;

    public String sendTo;

    public Client(int PORT) {
        ended = false;
        MAX_THREADS = 100;
        this.PORT = PORT;
        idle = true;
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        sender = new ClientSender(this, socket, SERVER_PORT);
        receiver = new ClientReceiver(this, socket, PORT);
        this.SERVER_ADDRESS = "127.0.0.1";
        executorPool = Executors.newWorkStealingPool(MAX_THREADS); // più efficiente e meno soggetto ad errori di FixedThreadPool e CachedThreadPool
        executorPool.execute(sender);
        executorPool.execute(receiver);
    }

    public Client(int PORT, String SERVER_ADDRESS) {
        this(PORT);
        this.SERVER_ADDRESS = SERVER_ADDRESS;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void sendUrgent(Packet packet) {
        sender.sendControl(packet); //bypassa la coda
    }

    public void setIdle(boolean i) {
        idle = i;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setControlField(Field f) {
        this.controlField = f;
    }

    public Field getControlField() {
        return controlField;
    }

    public ClientSender getSender() {
        return sender;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new SignIn();
    }

}
