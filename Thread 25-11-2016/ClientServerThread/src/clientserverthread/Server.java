
package clientserverthread;

import java.io.*;
import java.net.*;

/**
 *
 * @author Eleonora
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9999);
        Socket con;
        PrintWriter uscita;
        BufferedReader bReader;
        String message;
        while (true) {
            con = ss.accept();
            uscita = new PrintWriter(con.getOutputStream(), true);
            bReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (true) {
                message = bReader.readLine();
                System.out.println(message);
                uscita.println("read message");
                if (message == null) {
                    break;
                }

            }
        }
    }
}
