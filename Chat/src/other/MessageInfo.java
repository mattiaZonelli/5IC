package other;

import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by Rikkardo on 06/12/2016.
 */
public class MessageInfo {

    private int type;
    private String info;
    private InetAddress address;
    private int port;


    public MessageInfo(DatagramPacket packet) {
        String infoTemp = new String(packet.getData());
        if(infoTemp.startsWith("connect ") || infoTemp.equals("connect")){
            type = 0;
            info = infoTemp.substring(8, infoTemp.indexOf("end") - 1);
        } else if(infoTemp.startsWith("message ")){
            type = 1;
            info = infoTemp.substring(8, infoTemp.indexOf("end") - 1);
        } else if(infoTemp.startsWith("disconnect ") || infoTemp.equals("disconnect")){
            type = 2;
        } else if(infoTemp.startsWith("connected ")){
            type = 3;
        } else if(infoTemp.startsWith("already present ")){
            type = 4;
        } else if(infoTemp.startsWith("disconnected ")){
            type = 5;
        }
        address = packet.getAddress();
        port = packet.getPort();
    }

    public int getType(){
        return type;
    }

    public String getInfo(){
        return info;
    }

    public InetAddress getAddress(){
        return address;
    }

    public String getAddressToString(){
        return address.getHostAddress();
    }

    public int getPort(){
        return port;
    }


}
