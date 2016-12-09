package com.chat;

import com.client.Client;
import com.protocol.Packet;

import java.net.DatagramSocket;

/**
 * Created by nicola.pasqualetto on 02/12/2016.
 */
public class ClientSender extends Sender {


    private Packet currentPacket;
    private Client client;

    public ClientSender(Client client,DatagramSocket socket, int PORT) {

        super(socket, PORT, null);
        this.client=client;
        currentPacket=null;
    }

    public void sendControl(Packet packet){
        //aggiungere intestazione
        byte[] data=new byte[packet.getData().length+2];
        data[0]=data[data.length-1]= (char)0;

        for(int i=1;i<data.length-1;i++){
            data[i]=packet.getData()[i-1];
        }
        System.out.println(packet.getIP());
        send(data, packet.getIP());
    }

    public void sendMessage(Packet packet){
        send(packet.getData(), packet.getIP());
    }

    public void send(Packet p){
        System.out.println("CIAO");
        if(p.isControl()){
            sendControl(p);
        }else{
            sendMessage(p);
        }
    }

    public Packet getCurrentPacket() {
        return currentPacket;
    }

    public void setCurrentPacket(Packet currentPacket) {
        this.currentPacket = currentPacket;
    }

    public void run(){
        while (!this.isEnded()) {
            if(currentPacket!=null){
                System.out.println("Invio");
                send(currentPacket);
                currentPacket=null;
            }
        }
    }

}
