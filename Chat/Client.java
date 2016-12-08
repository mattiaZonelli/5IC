import java.io.IOException;
import java.net.*;

/**
 * Classe che trasmette i pacchetti in broadcast e li riceve
 * Created by pool on 04/12/16.
 */
public class Client extends Thread {

    final static String INET_GROUP = "224.0.0.3";
    final static int PORT = 8888;
    InetAddress address;
    MulticastSocket clientSocket;
    boolean stopped;
    ChatController gui;  //Riferimento alla grafica
    User connectedUser; //Riferimento all'utente che vuole connettersi

    public Client(ChatController gui) {
        try {
            address = InetAddress.getByName(INET_GROUP);
            clientSocket = new MulticastSocket(PORT);
            clientSocket.joinGroup(address);
            stopped = false;
            this.gui = gui;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
        gui.getApplication().getChatStage().setTitle("Benvenuto nella Chatroom, "+getConnectedUser().getName()+" "+getConnectedUser().getSurname());
    }

    public void sendMessage(String msg) {
        String toSend = connectedUser.getNickname()+": "+msg;
        System.out.println("Sono il client e devo inviare: "+toSend);
        try {
            DatagramPacket sendMsg = new DatagramPacket(toSend.getBytes(), toSend.getBytes().length, address, PORT);
            clientSocket.send(sendMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped() {
        stopped = true;
        sendMessage(""+(char)0);
    }

    public void run(){
        try {
            while(!isStopped()) {
                System.out.println("Sto aspettando il pacchetto: ");
                byte[] buf = new byte[256];
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);
                String name = msg.substring(0,msg.indexOf(":"));
                switch (msg.charAt(name.length()+2)){
                    case (char)0:gui.getUserList().getItems().remove(name);
                        break;
                    case (char)1:gui.getUserList().getItems().add(name);
                        break;
                    default: gui.getMessagesBox().appendText(msg+"\n");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

