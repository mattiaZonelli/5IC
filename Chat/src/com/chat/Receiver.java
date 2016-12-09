package com.chat;

import com.database.DBConnector;
import com.database.DBReader;
import com.database.Field;
import com.database.Tuple;
import com.protocol.Packet;
import com.protocol.Protocol;
import com.server.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by Nicola on 23/11/2016.
 */
public class Receiver extends Streamer implements Runnable {

    private LinkedTransferQueue<Packet> taskQueue;

    public Receiver(DatagramSocket socket, int PORT, LinkedTransferQueue<Packet> taskQueue) {
        super(PORT, socket);
        this.taskQueue = taskQueue;
    }

    public void receive(byte[] data) {
        packet = new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
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
        while (!ended) {
            byte b[] = new byte[1024];
            receive(b);
            if (packet != null) {
                byte[] data;
                boolean isControl;
                if (packet.getData()[0] == (char) 0 && packet.getData()[packet.getData().length - 1] == (char) 0) { //se campo controllo
                    data = new byte[packet.getData().length - 2];
                    isControl = true;
                    for (int i = 1; i < packet.getData().length - 1; i++) {
                        data[i - 1] = packet.getData()[i];
                    }
                } else {
                    data = packet.getData();
                    isControl = false;
                }
                String s=new String(data);
                int nameEnd=s.indexOf('@');
                InetAddress address=null;
                if(nameEnd!=-1) {
                    String receiverName = s.substring(0, nameEnd);
                    DBConnector connector=new DBConnector("Users");
                    DBReader reader=connector.newDBReader();
                    Tuple t=new Tuple();
                    reader.read(t,"Username = '"+receiverName+"'","User_Data");
                    try {
                        address=InetAddress.getByName((String)t.getField("Ip_address").getValue());
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }else{
                    address=packet.getAddress();
                }
                Packet p = new Packet(data, address, packet.getPort(), isControl);
                if (isControl) {
                    handleControls(p);
                } else {
                    taskQueue.offer(p);
                }
                packet = null;
            }
        }
    }


    public void handleControls(Packet packet) {
        Object object = packet.deserialize(packet.getData()); //ottieni il campo inviato
        Field responseField = null;
        Packet response = null;
        if (object.getClass().getName().equals("com.database.Field")) {
            if (((Field) (object)).getControl().equals(Protocol.EXISTS)) {
                responseField = new Field(((Field) (object)).getName(), Server.lookup((Field) object), Protocol.EXISTS); // risposta al campo inviato
                response = new Packet(responseField, packet.getIP(), packet.getPORT(), packet.isControl());
                if (((Field) (object)).isUpdate()) {
                    Server.updateIP(this.packet.getAddress(), (Field) object);
                }
                Server.sendUrgent(response);
            }
            if (((Field) (object)).getControl().equals(Protocol.IP_RESOLUTION)) {
                responseField = new Field(((Field) (object)).getName(), Server.applyIP((Field) object), Protocol.IP_RESOLUTION); // risposta al campo inviato
                response = new Packet(responseField, packet.getIP(), packet.getPORT(), packet.isControl());
                Server.sendUrgent(response);
            }
            if(((Field) (object)).getControl().equals(Protocol.CLOSING)){
                try {
                    Server.updateIP(InetAddress.getByName("0.0.0.0"), (Field) object);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }

        } else if (object.getClass().getName().equals("com.database.Tuple")) {
            if (((Tuple) (object)).getControl().equals(Protocol.INSERT)) {
                responseField = new Field("Control", Server.insert((Tuple) object), Protocol.INSERT); // risposta al campo inviato
                response = new Packet(responseField, packet.getIP(), packet.getPORT(), packet.isControl());
                Server.sendUrgent(response);
            }
        }
    }

}
