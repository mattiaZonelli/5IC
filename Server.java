package echo;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    ServerSocket echoServer;
    Socket clientSocket;

    BufferedReader is;
    PrintStream os;
    String line;
    ArrayList <String>wordOccurrences;

    public static void main(String args[]) {
        Server server = new Server();
    }

    Server() {
        echoServer = null;
        clientSocket = null;
        wordOccurrences = new ArrayList();

        try {
            echoServer = new ServerSocket(9999);
            System.out.println("SERVER CONNESSO");
        } catch (IOException e) {
            System.out.println("ERROR. CLOSE CONNECTION.");
        }

        while (true) {
            try {
                clientSocket = echoServer.accept();

                System.out.println("\nClient connesso.");

                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintStream(clientSocket.getOutputStream());

                while (true) {
                    line = "ECHO " + is.readLine();
                    line = checkOccurrences();
                    os.println(line);
                }
            } catch (IOException e) {
                System.out.println("Client disconnesso.\n");
            }
        }
    }

    public final String checkOccurrences() {
        if (wordOccurrences.size() < 2) {
            if (wordOccurrences.isEmpty()) {
                wordOccurrences.add(line);
            } else if (line.equals(wordOccurrences.get(0))) {
                wordOccurrences.add(line);
            } else {
                wordOccurrences.removeAll(wordOccurrences);
            }
        }else{
            line = "STOP";
            wordOccurrences.removeAll(wordOccurrences);
        }

        return line;
    }
}
