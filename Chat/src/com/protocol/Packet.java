package com.protocol;

import java.io.*;
import java.net.InetAddress;

/**
 * Created by Nicola on 23/11/2016.
 */
public class Packet implements Serializable {


    private final InetAddress IP;
    private final int PORT;
    private final byte[] data;
    private final Object info;
    private final boolean control;
    private String usernameOfReceiver;

    public Packet(Object info, InetAddress IP, int PORT, boolean control) {
        this.control = control;
        this.IP = IP;
        this.PORT = PORT;
        this.info = info;
        data = serialize(info);
        usernameOfReceiver = "";
    }

    public Packet(byte[] data, InetAddress IP, int PORT, boolean control) {
        this.control = control;
        this.IP = IP;
        this.PORT = PORT;
        this.data = data;
        this.info =  deserialize(data);
        usernameOfReceiver = "";
    }

    public Packet(byte[] data, InetAddress IP, int PORT, boolean control, String usernameOfReceiver) {
        this(data, IP, PORT, control);
        this.usernameOfReceiver = usernameOfReceiver;
    }

    public Packet(Object info, InetAddress IP, int PORT, boolean control, String usernameOfReceiver) {
        this(info, IP, PORT, control);
        this.usernameOfReceiver = usernameOfReceiver;
    }

    public boolean isControl() {
        return control;
    }

    public String getUsernameOfReceiver() {
        return usernameOfReceiver;
    }

    public byte[] serialize(Object data) {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream out;
        try {
            bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(data);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray() != null ? bos.toByteArray() : null;
    }

    public Object deserialize(byte[] bytes) {
        ByteArrayInputStream bis = null;
        ObjectInputStream in = null;
        Object output = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            in = new ObjectInputStream(bis);
            output = in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();

        }
        return output;
    }

    public InetAddress getIP() {
        return IP;
    }

    public int getPORT() {
        return PORT;
    }

    public byte[] getData() {
        return data;
    }

    public Object getInfo() {
        return info;
    }
}
