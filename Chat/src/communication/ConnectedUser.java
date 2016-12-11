package communication;

import java.net.InetAddress;

/**
 * Created by Rikkardo on 02/12/2016.
 */
public class ConnectedUser {

    private final String name;
    private final InetAddress address;
    private final int PORT;

    public ConnectedUser(String name, InetAddress address, int PORT){
        this.name = name;
        this.address = address;
        this.PORT = PORT;
    }

    public String getName(){
        return name;
    }

    public InetAddress getAddress(){
        return address;
    }

    public int getPort(){
        return PORT;
    }

}
