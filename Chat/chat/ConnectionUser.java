
package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionUser {

    String name;

    ConnectionUser(String name) {
        this.name = name;
    }

    public void sendUser() {
        DatagramSocket clientSocket;
        try {
            clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(name.getBytes(), name.getBytes().length, IPAddress, 9090);
            clientSocket.send(sendPacket);

        } catch (SocketException ex) {
            Logger.getLogger(ConnectionUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
