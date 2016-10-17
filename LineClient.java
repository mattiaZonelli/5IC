package echoclient;

/**
 *
 * @author darkhaker
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class LineClient {

    public static void main(String[] args) throws IOException {
        String ip = "localhost";
        int port = 9999;
        Socket socket = new Socket(ip, port);
        System.out.println("Connection	established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        //BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        try {
            while (true) {
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                String socketLine = socketIn.nextLine();
                //String socketLine = socketIn.readLine();
                System.out.println(socketLine);
                if (inputLine.equals("quit")) {
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Connection closed");
        } /*finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }*/

    }

}
