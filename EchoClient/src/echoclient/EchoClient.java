package echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author swapnil.paul
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket server = null;
        try {
            String serverAddress = "127.0.0.1";
            server = new Socket(serverAddress, 9090);
            System.out.println("Connesione stabilita - 127.0.0.1:9090 ");
            System.out.println("Scrivi qualcosa per comunicare con il server - per chiudere la connesione scrivere 3 volte close");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintStream out = new PrintStream(server.getOutputStream(), true); // autoflush
            while (true) {
                String str = in.readLine();
                out.println(str);
                BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                String answer = input.readLine();
                if (!answer.equalsIgnoreCase("closeClient")) {
                    System.out.println("Server says: " + answer);
                } else {
                    server.close();
                    in.close();
                    out.close();
                    System.err.println("Connesione chiusa!");
                }
            }
        } catch (IOException ex) {
            System.out.print("");
        }
    }
}
