package echoclientv3;

/**
 *
 * @author darkhaker
 */
import java.io.*;
import java.net.*;
import java.lang.Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoV3 {

    ServerSocket echoServer;
    String line;
    BufferedReader is;
    PrintStream os;
    boolean stopped;
    int c;

    public EchoV3(ServerSocket echoServer) {
        this.echoServer = echoServer;
    }

    public EchoV3(int port) {
        try {
            echoServer = new ServerSocket(port);
            stopped = false;
            c = 0;
        } catch (IOException ex) {
            Logger.getLogger(EchoV3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void server() {

        String trace = "";
        try {
            while (!stopped) {
                System.out.println("Server started.......Waiting for requests");
                Socket clientSocket = echoServer.accept();
                System.out.println("Recieved client connection");
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintStream(clientSocket.getOutputStream());
                line = is.readLine();
                while (c < 3) {
                    os.println("ECHO " + line);
                    line = is.readLine();
                    if (line.equals("quit")) {
                        c++;
                        trace = line;
                    }
                    if (!trace.equals("quit")) {
                        c = 0;
                    }
                    if(c==3){
                        os.println((char)0);
                    }
                }
                os.println("ECHO " + line);
                System.out.println("Client disconnected");
            }
        } catch (IOException e) {
            System.out.println(e);

        }
    }

    public static void main(String args[]) {

        EchoV3 s = new EchoV3(9999);
        s.server();
    }
}
