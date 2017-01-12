package com.chat;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Nicola on 23/11/2016.
 */
public class Streamer {

    protected final int PORT;
    protected DatagramPacket packet;
    protected boolean ended;
    protected DatagramSocket socket;

    public Streamer(int PORT, DatagramSocket socket) {
        this.socket=socket;
        ended=false;
        this.PORT = PORT;
    }

    public byte[] getData() {
        if (packet != null) {
            return packet.getData();
        } else {
            throw new UnsupportedOperationException("You haven't received the packet yet.");
        }
    }

    public int getPort() {
        if (packet != null) {
            return packet.getPort();
        } else {
            throw new UnsupportedOperationException("You haven't received the packet yet.");
        }
    }

    public boolean isEnded() {
        return ended;
    }

    public void end() {
       ended=true;
    }

    public InetAddress getIp() {
        if (packet != null) {
            return packet.getAddress();
        } else {
            throw new UnsupportedOperationException("You haven't received the packet yet.");
        }


    }
}
