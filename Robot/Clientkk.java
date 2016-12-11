package myknockknock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author mattia.zonelli
 */
public class Clientkk {

    Socket con;
    BufferedReader br;
    PrintWriter out;

    public static void main(String[] args) throws IOException {
        Clientkk client = new Clientkk();
        client.run();
    }

    public void run() throws IOException {

        con = new Socket("localhost", 9999);
        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        out = new PrintWriter(con.getOutputStream(), true);
        Scanner tast;
        tast = new Scanner(System.in);
        String s = "";

        while (true) {
            s = br.readLine();
            System.out.println(s);
            System.out.print("Messaggio: ");
            s = tast.nextLine();
            if (s.equalsIgnoreCase("addio")) {
                break;
            }
            out.println(s);

        }

        con.close();

    }

}
