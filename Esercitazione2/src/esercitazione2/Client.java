package esercitazione2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author garbin eleonora
 */
public class Client {

   
    BufferedReader bufferedR;
    PrintWriter out;
    Socket con;
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.run();
    }

    public void run() throws IOException {

        con = new Socket("localhost", 9999);
        bufferedR = new BufferedReader(new InputStreamReader(con.getInputStream()));
        out = new PrintWriter(con.getOutputStream(), true);
        Scanner tast;
        tast = new Scanner(System.in);
        String s = "";
        while (true) {
            System.out.print("Message: ");
            s = tast.nextLine();
            if (s.equals("end")) {
                break;
            }
            out.println(s);
            s = bufferedR.readLine();
            System.out.println(s);

        }
        con.close();

    }

}
