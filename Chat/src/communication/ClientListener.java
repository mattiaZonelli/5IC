package communication;

import other.MessageInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Rikkardo on 28/11/2016.
 */
public class ClientListener implements Runnable{

    private Client c;
    private DatagramSocket socket;
    private boolean running;

    public ClientListener(Client c, DatagramSocket socket){
        this.c = c;
        this.socket = socket;
        running = true;
    }

    @Override
    public void run() {
        while(running){
            MessageInfo message = receive();
            if(message.getType()==1){
                c.displayMessage(message.getInfo());
            }
        }
    }

    public void stop(){
        running = false;
    }

    private synchronized MessageInfo receive(){
        MessageInfo info = null;
        byte[] receiveData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(packet);
            info = new MessageInfo(packet);
        } catch(IOException e){
            System.out.println("Can't receive packet");
        }
        return info;
    }
}
