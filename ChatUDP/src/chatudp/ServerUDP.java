package chatudp;

/**
 *
 * @author MATTEO
 */
public class ServerUDP {

    public static void main(String[] args) throws java.io.IOException {
        System.out.println("Server started");
        new ThreadServerUDP().run();

    }
}
