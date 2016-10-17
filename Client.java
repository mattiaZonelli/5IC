package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author eleonora.garbin
 */
public class Client {

    public static void main(String[] args) throws IOException {

        String serverAddress = "127.0.0.1";
        String userInput = "prova";
        Socket s = new Socket(serverAddress, 9999);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        while ((userInput = stdIn.readLine()) != null) {

            out.println(userInput);
            System.out.println(in.readLine());
        }
    }

}
