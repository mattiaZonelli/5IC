package echoclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author SWAPNIL PAUL 5IC
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        System.out.println("************ Echo Client ************");
        System.out.println("Per chiudere la connesione bisogna scrivere per 3 volte \"close\" oppure per chiudere tutto \"shutdown\"");
        Socket server = null;
        try {
            String serverAddress = "127.0.0.1";
            server = new Socket(serverAddress, 9090);
            System.out.println("Connesione stabilita - 127.0.0.1:9090 ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintStream out = new PrintStream(server.getOutputStream(), true); // with autoflush
            int count = 0;
            while (true) {
                String str = in.readLine();

                out.println(str);
                if (str.equalsIgnoreCase("shutdown")) {
                    server.close();
                    in.close();
                    out.close();
                    System.err.println("Connesione chiusa!");
                }

                BufferedReader input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                String answer = input.readLine();
                if (str.equalsIgnoreCase("close")) {
                    count++;
                }
                if (count == 3) {
                    server.close();
                    System.exit(0);
                }
                System.out.println("  Server says: " + answer);
            }
        } catch (IOException ex) {
            System.err.println("Server è chiuso!");
        }
    }
}
