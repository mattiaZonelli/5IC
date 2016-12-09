package com.chat;

import com.client.Client;
import com.database.Field;
import com.graphics.ClientLayout;
import com.protocol.Packet;

import java.net.DatagramSocket;

/**
 * Created by nicola.pasqualetto on 02/12/2016.
 */
public class ClientReceiver extends Receiver {
    private Client client;
    public ClientReceiver(Client client,DatagramSocket socket, int PORT) {
        super(socket, PORT, null);
        this.client=client;
    }

    public void run() {
        while (!ended) {
            byte b[] = new byte[1024];
            receive(b);
            Packet p = new Packet(packet.getData(), packet.getAddress(), packet.getPort(),false);
            if(p.getInfo().getClass().getName().equals("com.database.Field")){
                Field f =(Field)p.getInfo();
                if(f.getControl()!="") {
                    client.setIdle(false);
                    client.setControlField(f);
                }
            }else{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ClientLayout.addReceivedMessage(p.getInfo());
            }

        }
    }
}
