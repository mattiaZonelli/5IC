package echoclient;
/**
 *
 * @author darkhaker
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;

public class Echo {

    public static void main(String args[]) {

        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        Socket clientSocket = null;

        try {
            echoServer = new ServerSocket(9999);
            System.out.println("Server started.......Waiting for requests");
            clientSocket = echoServer.accept();
            System.out.println("Recieved client connection");
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            while (true) { //da qui ho modificato
                line = is.readLine();
                if (line.equals("quit")) {
                    Thread.sleep(100);
                    System.out.println("Client Disconnected");
                    break;
                } else {
                    Thread.sleep(100);
                    os.println("ECHO " + line);
                } //fine modifiche
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }
}
