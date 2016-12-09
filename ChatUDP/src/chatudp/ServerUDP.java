package chatudp;

/**
 *
 * @author MATTEO
 */
public class ServerUDP {

    public static void main(String[] args) throws java.io.IOException {

        new ThreadServerUDP().run();
        System.out.println("Server started");

    }
}
