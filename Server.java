package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String args[]) throws IOException {

        ServerSocket echoServer = null;
        String line;
        BufferedReader is;
        PrintStream os;
        String chiudi = "ciao chiudi";

        String[] disconnettere = {"ciao", "ciao", "ciao"};
        Socket clientSocket = null;
        try {
            echoServer = new ServerSocket(9999);
            
        } catch (IOException e) {
            System.out.println(e);
        }
        try {

            clientSocket = echoServer.accept();

            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            int i = 0;

            while (true) {
                line = is.readLine();
                if (disconnettere[i].equals(line)) {
                    i++;
                    if (i == disconnettere.length) {
                        os.println("DISCONNESSO!" );
                        clientSocket = null;
                    } else {
                        Thread.sleep(1000);
                        os.println("ECHO " + line);
                    }
                } else {
                    Thread.sleep(1000);
                    os.println("ECHO " + line);
                }
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
    }
}
