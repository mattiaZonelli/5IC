package echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author swapnil.paul
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;

        Socket clientSocket = null;

        try {
            echoServer = new ServerSocket(9090);
            System.out.println("ServerSocket sta girando sulla porta 9090");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            clientSocket = echoServer.accept();
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            boolean close = false;
            int count = 0;
            while (!close) {
                line = is.readLine();
                if (line.equalsIgnoreCase("close")) {
                    count++;
                }
                if (count == 3) {
                    close = true;
                    os.println("closeClient");
                }
                Thread.sleep(500);
                os.println("ECHO " + line);
            }
            if (close) {
                clientSocket.close();
                echoServer.close();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
