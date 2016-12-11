package communication;

import other.MessageInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Rikkardo on 28/11/2016.
 */
public class ServerListener implements Runnable{


    private Server s;
    private DatagramSocket socket;
    private boolean running;


    public ServerListener(Server s, DatagramSocket socket){
        this.s = s;
        this.socket = socket;
        running = true;
    }

    @Override
    public void run() {
        while(running){
            MessageInfo message = receive();
            if(message.getType()==0){
                String name = message.getInfo();
                if(!s.checkUserRegistered(name)){
                    s.registerUser(name, message.getPort(), message.getAddressToString());
                    s.setUserConnected(name, true);
                    send("connected", message.getAddress(), message.getPort());
                } else if(!s.checkUserConnected(name)){
                    s.updateUser(name, message.getPort(), message.getAddressToString());
                    s.setUserConnected(name, true);
                    send("connected", message.getAddress(), message.getPort());
                } else{
                    send("not connected", message.getAddress(), message.getPort());
                }
            }else if(message.getType()==1){
                if(!s.checkPortPresence(message.getPort())){
                    broadcast(message.getPort(), s.getUserByPort(message.getPort()) + ": " + message.getInfo());
                    s.displayMessage(message.getPort(), message.getInfo());
                }
            }else if(message.getType()==2){
                send("disconnected", message.getAddress(), message.getPort());
                s.setUserConnected(s.getUserByPort(message.getPort()), false);
            }
        }
    }

    public void stop(){
        running = false;
    }

    private synchronized void send(String message, String userName){
        message += " end";
        byte[] sendData = message.getBytes();
        InetAddress IPAddress = s.getAddress(userName);
        int port = s.getPort(userName);
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            socket.send(packet);
        }catch(IOException e){
            System.out.println("Impossible send pack.");
        }
    }

    private void broadcast(int port, String message){
        ArrayList<String> users = s.getConnectedUsers();
        for(int i = 0; i<users.size(); i++){
            send("message " + message, users.get(i));
        }
    }

    private synchronized void send(String message, InetAddress IPAddress, int port){
        message += " end";
        byte[] sendData = message.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        try {
            socket.send(packet);
        }catch(IOException e){
            s.displayConsolle("Can't send information. " + e.getMessage());
        }
    }

    private synchronized MessageInfo receive(){
        MessageInfo info = null;
        byte[] receiveData = new byte[1024];
        DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(packet);
            info = new MessageInfo(packet);
        } catch(IOException e){
            s.displayConsolle("Can't receive information. " + e.getMessage());
        }
        return info;
    }

}
