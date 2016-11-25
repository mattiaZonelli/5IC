
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Leonardo
 */

public class Server {

    static final int port = 9999;

    public static void main(String[] args) throws IOException {

        ServerSocket ServerSock = new ServerSocket(port);
        Socket clientSocket;
        while (true) {
            clientSocket = ServerSock.accept();
            ThreadUse s = new ThreadUse(clientSocket);
            Thread t = new Thread(s);
            t.start();
        }
    }
}






