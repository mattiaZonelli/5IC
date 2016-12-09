package com.chat;

import com.protocol.Packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Nicola on 23/11/2016.
 */
public class Sender extends Streamer implements Runnable {

    private LinkedTransferQueue <Packet> taskQueue;

    public Sender(DatagramSocket socket, int PORT, LinkedTransferQueue <Packet> taskQueue) {
        super(PORT, socket);
        this.taskQueue = taskQueue;
    }

    public void send(byte[] data, final InetAddress IP_ADDRESS) {
        packet = new DatagramPacket(data, data.length, IP_ADDRESS, PORT);
        try {
            socket.send(packet);
            System.out.println("SENT PACKET "+IP_ADDRESS+"@"+PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (!this.isEnded()) {
            try {
                Packet p = (Packet) taskQueue.take();
                send(p.getData(), p.getIP());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
