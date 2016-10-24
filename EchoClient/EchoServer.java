import java.io.*;
import java.net.*;

/**
 * @author SWAPNIL PAUL 5Ic
 */
public class EchoServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("************ Echo Server ************");
        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        echoServer = new ServerSocket(9090);
        System.out.println("ServerSocket is running at port 9090");
        boolean connection = false;
        try {
            int i = 0;
            boolean close = false;
            while (!close) {
                Socket clientSocket = echoServer.accept();

                if (clientSocket.isConnected()) {
                    i++;
                    connection = true;
                    System.out.println("Conessione stabilita col Client numero: " + i);
                }

                try {
                    int count = 0;
                    is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    os = new PrintStream(clientSocket.getOutputStream(), true);
                    while (!close && connection) {
                        line = is.readLine();
                        if (line.equalsIgnoreCase("shutdown")) {
                            close = true;

                        }
                        if (line.equalsIgnoreCase("close")) {
                            //close = true;
                            count++;
                        }
                        if (count == 3) {
                            clientSocket.close();
                            //break;
                            connection = false;
                        }
                        Thread.sleep(500);
                        os.println("ECHO " + line);
                    }
                    if (close) {
                        clientSocket.close();
                        echoServer.close();
                    }
                } finally {
                    if (clientSocket.isClosed()) {
                        System.out.println("Disconnesione col Client numero: " + i);
                    }
                    clientSocket.close();
                }
            }
        } finally {
            echoServer.close();
            System.err.println("Il Server e tutti i Client sono stati disconnessi!");
        }
    }
}
